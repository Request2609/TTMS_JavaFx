package xupt.se.ttms.view.Plan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.model.*;
import xupt.se.ttms.service.*;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.view.sellticket.ProcessTicketEvUI;
import xupt.se.ttms.view.user.ConfirmBox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProcessSchedUI {

    TextField studio_id ;
    TextField play_id ;
    TextField show_date ;
    TextField ticket_price ;
    public int getScheduleInfo(Schedule s) throws ParseException {

        //如果演出计划已经被安排，票已经生成，则不能修改演出计划
        if(s.getStatus() == 1) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("演出票已经生成，不能修改！");
            return 0 ;
        }

        if(studio_id.getText().equals("")||play_id.getText().equals("")
                ||ticket_price.getText().equals("")||show_date.getText().equals("")) {
            ConfirmBox  con = new ConfirmBox() ;
            con.displaySuccess("数据不完整~!");
            return 0 ;
        }
        s.setStudio_id(Integer.parseInt(studio_id.getText()));
        s.setPlay_id(Integer.parseInt(play_id.getText()));
        s.setSched_ticket_price(Integer.parseInt(ticket_price.getText()));
        //将当前演出计划和系统时间进行比较
        ProcessTicketEvUI pteu = new ProcessTicketEvUI() ;
        if(!pteu.compareTime(getCurTime(),show_date.getText())){
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("设置时间是过去时间！");
            return 0;
        }

        ScheduleSrv ss = new ScheduleSrv() ;
        ss.select("");
        s.setSched_time(show_date.getText());
        int ret = ss.modify(s) ;
        System.out.println(s.toString()) ;
        return ret ;
    }

    public void modifySchedule(Schedule s) {
        Stage win =  new Stage() ;
        win.setTitle("修改演出计划");
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        vb.setPadding(new Insets(20));

        studio_id = new TextField() ;
        play_id = new TextField() ;
        show_date= new TextField() ;
        ticket_price = new TextField() ;
        studio_id.setPromptText("请输入新的演出厅ID");
        play_id.setPromptText("请输入新的剧目ID");
        show_date.setPromptText("请输入新的上映日期");
        ticket_price.setPromptText("请重新设置票价");
        Button ok = new Button("确认") ;
        Button cancer = new Button("取消");
        HBox hb = new HBox() ;
        hb.setPadding(new Insets(20));
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(ok,cancer) ;
        ok.setOnMouseClicked(e->{
            int ret = 0 ;
            try {
                ret = getScheduleInfo(s) ;
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            if(ret == 1) {
                win.close();
            }
        });

        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        vb.getChildren().addAll(studio_id,play_id,show_date,ticket_price, hb) ;
        Scene ss = new Scene(vb, 800,600) ;
        win.setScene(ss);
        win.show() ;
    }

    public void addSchedule() {
        VBox vb = new VBox() ;
        vb.setSpacing(20);
        vb.setPadding(new Insets(20));
        vb.setAlignment(Pos.CENTER);
        HBox hb = new HBox() ;
        hb.setSpacing(20);
        hb.setPadding(new Insets(20));
        hb.setAlignment(Pos.CENTER);
        Stage win = new Stage() ;
        win.setTitle("添加演出计划");
        setAddField();
        Button ok = new Button("确认") ;
        Button cancer = new Button("取消") ;
        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        ok.setOnMouseClicked(e->{
            int ret =  0 ;
            try {
                ret  = addInfo() ;
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            if(ret == 1) {
                win.close() ;
            }
        });

        hb.getChildren().addAll(ok,cancer) ;
        vb.getChildren().addAll(studio_id, play_id, show_date, ticket_price, hb) ;
        Scene s = new Scene(vb, 800,600) ;
        SetSceneStyle.sceneStyle(s);
        win.setScene(s);
        win.show() ;
    }

    public static String getStringTime(Date time) {
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
        System.out.println(sDateFormat.format(time) ) ;
        return sDateFormat.format(time) ;
    }

    //删除演出计划,删除相关演出票，相关演出厅的状态置为０，即闲置状态
    public void deletePlan(Schedule s) {
        ScheduleSrv ssrv = new ScheduleSrv() ;
        ssrv.delete(s.getSche_id()) ;
        //修改相关演出厅状态
        StudioSrv sdrv=  new StudioSrv() ;
        Studio sd = sdrv.Fetch("studio_id =" +s.getStudio_id()) ;
        //将相关演出厅只为闲置
        sd.setStatus(0);
        sdrv.modify(sd) ;
        //删除演出票
        TicketSrv ts = new TicketSrv() ;
        List<Ticket>lstk ;
        Ticket tk = new Ticket() ;
        lstk = ts.select("sched_id ="+s.getSche_id()) ;
        List<Seat>lseat;
        SeatSrv sseat = new SeatSrv() ;
        lseat = sseat.selectAll("sched_id="+s.getSche_id()) ;
        if(lstk.size() != 0) {
            ts.modifyTicket("sched_id="+s.getSche_id()) ;
        }
        if(lseat.size() != 0) {
            sseat.modifyAll("sched_id="+s.getSche_id()) ;
        }
    }

    public int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    //添加演出计划，查看时间冲突
    public int addInfo() throws ParseException {
        Schedule sh = new Schedule() ;
        if(studio_id.getText().equals("") ||play_id.getText().equals("")||
        show_date.getText().equals("")||ticket_price.getText().equals("")) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入完整数据");
            return 0 ;
        }
        //判断时间是否有重复
        ////////////////////////////////////////////////////////////////////////////////////////////
        //演出厅存在的话是否被占用,要是被占用,计算时间
        //验证剧目是否存在?
        sh.setPlay_id(Integer.parseInt(play_id.getText()));
        MovieSrv ms  = new MovieSrv() ;
        Movie m = ms.select("play_id = "+sh.getPlay_id()) ;
        if(m == null) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("该剧目不存在");
            return 0 ;
        }

        sh.setSched_time(show_date.getText());
        //将剧目市场转换成毫秒,加上１０分钟休息时间
        int play_length = m.getPlay_Length()*60000+10*60000;
        //将剧目的开始时间转换成毫秒
        long start_time = getTime(show_date.getText()) ;
        long end_time = getTime(show_date.getText()) +play_length ;
        Date start_date = new Date(start_time) ;
        Date end_times = new Date(end_time) ;
        //设置开始时间

        sh.setSched_time(getStringTime(start_date));
        sh.setSched_time_end(getStringTime(end_times));

        ScheduleSrv ssrv = new ScheduleSrv() ;
        //这里要找演出计划中和剧目id重复的演出计划
        List<Schedule>lschd = ssrv.select("play_id = "+m.getPlay_id()+";") ;
        if(lschd.size() != 0) {
            int len = lschd.size() ;
            for(int i = 0;  i<len ; i++) {
                Schedule s = lschd.get(0) ;
                //解决演出计划冲突
                if((compareDate(s.getSched_time(), sh.getSched_time()) < 0&&compareDate(s.getSched_time_end(), sh.getSched_time()) > 0)
                ||(compareDate(s.getSched_time_end(), sh.getSched_time_end()) > 0&&compareDate(s.getSched_time(),sh.getSched_time_end()) < 0)) {
                    ConfirmBox con = new ConfirmBox() ;
                    con.displaySuccess("演出计划和已经存在的演出计划有冲突！");
                    return  0;
                }
            }
        }

        //验证演出厅存在?
        sh.setStudio_id(Integer.parseInt(studio_id.getText()));
        StudioSrv sd  = new StudioSrv();
        Studio s = sd.Fetch(""+sh.getStudio_id()) ;
        if(s == null) {
            ConfirmBox con =  new ConfirmBox() ;
            con.displaySuccess("演出厅不存在");
            return 0 ;
        }

        //生成演出票的时候一定要设置演出厅状态
        //找到演出厅的话，在演出计划表中找相关演出厅的演出计划，判断是否时间冲突
           if(sh.getStatus() == 1) {
               int flag = 0 ;
            List<Schedule> lsched = ssrv.select("studio_id=" + sh.getStudio_id());
            //演出厅已经被占用
            //说明相关演出计划已经被删除，可以安排
            if (lsched.size() == 0) {
                flag = 1 ;
            }
            //演出厅存在相关演出计划,直接不能安排
            else {
                flag =0 ;
            }
            if(flag == 0) {
                ConfirmBox con = new ConfirmBox() ;
                con.displaySuccess("安排演出计划失败！");
                return 0 ;
            }
        }

        //修改演出厅状态
        s.setStatus(1);
        sd.modify(s) ;
        //根据剧目
        //ID,修改剧目的票价,修改剧目的状态
        sh.setSched_ticket_price(Integer.parseInt(ticket_price.getText())) ;
        m.setPlay_ticket_price(sh.getSched_ticket_price());
        String inputDate = show_date.getText() ;

        String curTime = getCurTime() ;
        //获取设置的演出计划时间
        long in = getTime(inputDate) ;
        //获取当前时间
        long cur = getTime(curTime) ;

        if(in < cur) {
            m.setPlay_status(0);
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("设置时间滞后,请重新设置!");
            return 0 ;
        }
        //时间正确
        else {
            m.setPlay_status(2);
        }
        MovieSrv mm = new MovieSrv() ;
        int ret = mm.modify(m) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("修改影片信息失败!");
            return 0 ;
        }

        sh.setSched_time(inputDate);
        ScheduleSrv sched = new ScheduleSrv() ;
        ret =sched.add(sh) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("添加数据失败");
            return 0;
        }
        return 1 ;
     }

     //演出计划,安排的演出的时间不能
    //将传入的日期转换成微秒
    public long getTime(String date) {
        long dateToSecond = 0  ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的日期格式，根据实际调整""里面内容
        try {
            dateToSecond = sdf.parse(date).getTime();//sdf.parse()实现日期转换为Date格式，然后getTime()转换为毫秒数值
            System.out.print(dateToSecond);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return dateToSecond ;
    }
    //获取当前的时间
    public String getCurTime() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = dateFormat.format(calendar.getTime());
        return s ;
    }

    public void setAddField() {
        studio_id =  new TextField() ;
        studio_id.setPromptText("请输入演出厅ID");
        play_id =  new TextField();
        play_id.setPromptText("请输入剧目ID");
        show_date = new TextField();
        show_date.setPromptText("请输入演出时间 year-month-day hh:mm:ss");
        ticket_price = new TextField();
        ticket_price.setPromptText("请输入票价");
    }

    public void querySched() {
        Stage win = new Stage() ;
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setSpacing(10);

        win.setTitle("查询演出计划");
        TextField play_id= new TextField() ;
        //按照剧目ID找演出计划
        play_id.setPromptText("请输入剧目ID");
        TextField name=  new TextField() ;
        name.setPromptText("请输入剧目名称");
        Button ok = new Button("确认");
        Button cancer = new Button("取消");
        cancer.setOnMouseClicked(e->{
            win.close();
        });
        ok.setOnMouseClicked(e->{
            int ret = 0 ;
            ret = query(name, play_id, win) ;
        });
        HBox hb = new HBox() ;
        hb.setPadding(new Insets(20));
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(ok,cancer) ;

        vb.getChildren().addAll(name, play_id, hb) ;
        Scene sn = new Scene(vb, 600,400) ;
        SetSceneStyle.sceneStyle(sn);
        win.setScene(sn);
        win.show() ;
    }
    public long getMillTime(String time) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //加上时间
        //必须捕获异常
        try {
            Date date=sDateFormat.parse(time);
            return date.getTime() ;
        } catch(ParseException px) {
            px.printStackTrace();
        }
        return -1 ;
    }
    public Date getTime(long time) {
        Date date = new Date(time) ;
        return date ;
    }
    public int query(TextField name, TextField play_id, Stage win) {
        if(name.getText().equals("")&&play_id.getText().equals("")) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入信息!");
            return 0 ;
        }

        ScheduleSrv ss = new ScheduleSrv() ;
        List<Schedule> ls = null ;
        Movie mv = null;
        MovieSrv movieSrv = null;
        int flag  = 0 ;
        if(!name.getText().isEmpty()) {
            movieSrv = new MovieSrv() ;
            mv = movieSrv.select("play_name='"+name.getText()+"'") ;
            ls= ss.select("play_id="+mv.getPlay_id()) ;
            if(ls.size() != 0){
                flag =1 ;
            }
        }
       else {
           ss = new ScheduleSrv() ;
           movieSrv  = new MovieSrv() ;
           System.out.println(play_id.getText());
            int id = Integer.parseInt(play_id.getText()) ;
            ls = ss.select("play_id="+id) ;
            mv = movieSrv.select("play_id="+id) ;
            if(ls !=  null && mv != null) {
                flag =1 ;
            }
        }
       if(flag == 1 )
       {
           Scene s = getScheduleScene(ls,mv, win) ;
           win.setScene(s);
       }

       return 1 ;
    }

    public Scene getScheduleScene(List<Schedule>list, Movie mv, Stage win) {
        VBox vb=new VBox() ;
        vb.setSpacing(10);
        vb.setPadding(new Insets(10));
        HBox hb = new HBox() ;
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().add(new Text("演出计划")) ;
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        grid.add(new Text("电影名称"), 1,1) ;
        grid.add(new Text("演出厅ID"), 2,1) ;
        grid.add(new Text("演出时间"), 3,1) ;
        grid.add(new Text("票价"), 4,1) ;


        int col = 1 ;
        int row  = 2 ;
        for(Schedule s : list) {
            grid.add(new Text(mv.getPlay_name()), col,row) ;
            grid.add(new Text(s.getSche_id()+""), col+1,row) ;
            grid.add(new Text(s.getSched_time()+""), col+2,row) ;
            grid.add(new Text(s.getSched_ticket_price()+""), col+3,row) ;
            row++ ;
        }

        vb.setAlignment(Pos.CENTER);
//        grid.setStyle("-fx-background-color: #987;") ;
        Button ok =new Button("确认");
        ok.setOnMouseClicked(e->{
            win.close() ;
        });

        vb.getChildren().addAll(hb, grid, ok) ;
        Scene s =new Scene(vb, 600,400) ;
        SetSceneStyle.sceneStyle(s);
        return s ;
    }

}
