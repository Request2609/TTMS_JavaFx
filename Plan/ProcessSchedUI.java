package xupt.se.ttms.view.Plan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.omg.CORBA.INTERNAL;
import xupt.se.ttms.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.MovieSrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.user.ConfirmBox;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProcessSchedUI {

    TextField studio_id ;
    TextField play_id ;
    TextField show_date ;
    TextField ticket_price ;
    public int getScheduleInfo(Schedule s) {

        if(studio_id.getText().equals("")||play_id.getText().equals("")
                ||ticket_price.getText().equals("")||show_date.getText().equals("")) {
            ConfirmBox  con = new ConfirmBox() ;
            con.displaySuccess("数据不完整~!");
            return 0 ;
        }
        s.setStudio_id(Integer.parseInt(studio_id.getText()));
        s.setPlay_id(Integer.parseInt(play_id.getText()));
        s.setSched_ticket_price(Integer.parseInt(ticket_price.getText()));
        s.setSched_time(show_date.getText());
        ScheduleSrv  ss = new ScheduleSrv() ;
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
            ret = getScheduleInfo(s) ;
            if(ret == 1) {
                win.close();
            }
        });

        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        vb.getChildren().addAll(studio_id,play_id,show_date,ticket_price, hb) ;
        Scene ss = new Scene(vb, 400,400) ;
        win.setScene(ss);
        win.show() ;
    }

    public void addSchedule() {
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
            ret = addInfo() ;
            if(ret == 1) {
                win.close() ;
            }
        });
    }

    public int addInfo() {
        Schedule sh = new Schedule() ;
        if(studio_id.getText().equals("") ||play_id.getText().equals("")||
        show_date.getText().equals("")||ticket_price.getText().equals("")) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入完整数据");
            return 0 ;
        }
        //验证演出厅存在?
        sh.setStudio_id(Integer.parseInt(studio_id.getText()));
        StudioSrv sd  = new StudioSrv() ;
        Studio s = sd.Fetch(""+sh.getStudio_id()) ;
        if(s == null) {
            ConfirmBox con =  new ConfirmBox() ;
            con.displaySuccess("演出厅不存在");
            return 0 ;
        }
        //验证剧目是否存在?
        sh.setPlay_id(Integer.parseInt(play_id.getText()));
        MovieSrv ms  = new MovieSrv() ;
        Movie m = ms.select("play_id = "+sh.getPlay_id()) ;
        if(m == null) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("该剧目不存在");
            return 0 ;
        }
        //根据剧目ID,修改剧目的票价,修改剧目的状态
        sh.setSched_ticket_price(Integer.parseInt(ticket_price.getText())) ;
        m.setPlay_ticket_price(sh.getSched_ticket_price());
        String inputDate = show_date.getText() ;
        String curTime = getCurTime() ;
        long in = getTime(inputDate) ;
        long cur = getTime(curTime) ;
        if(in < cur) {
            m.setPlay_status(0);
        }
        else if(in == cur) {
            m.setPlay_status(1);
        }
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

        sh.setSched_time(show_date.getText());
        ScheduleSrv sched = new ScheduleSrv() ;
        ret =sched.add(sh) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("添加数据失败");
            return 0;
        }
        return 1 ;
     }
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
        show_date.setPromptText("请输入演出时间year-month-day");
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
        Scene sn = new Scene(vb, 400,400) ;
        SetSceneStyle.sceneStyle(sn);
        win.setScene(sn);
        win.show() ;
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
        grid.add(new Text("票价"), 5,1) ;


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
        vb.getChildren().addAll(hb, grid, new Button("确认")) ;
        grid.setStyle("-fx-background-color: #987;") ;
        Button ok =new Button("确认");
        ok.setOnMouseClicked(e->{
            win.close() ;
        });

        Scene s =new Scene(vb, 600,600) ;
        SetSceneStyle.sceneStyle(s);
        return s ;
    }

}
