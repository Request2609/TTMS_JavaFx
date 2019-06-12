package xupt.se.ttms.view.sellticket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.*;
import xupt.se.ttms.service.*;
import xupt.se.ttms.view.Ticket.TicketProcessUI;
import xupt.se.ttms.view.user.ConfirmBox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessTicketEvUI{

    Stage window ;
    Employee users ;
    Button bk  ;
    TextField movieId ;
    TextField movieName ;
    Stage swin ;
    Button backs ;
    //获取演出票的信息
    public void manageTicket(Stage window, Button back, Employee emp) {
        swin = window ;
        backs = back ;
        users = emp ;
        Scene s= getTicketScene() ;
        if(s == null) {
            return  ;
        }
        swin.setScene(s);
    }

    public Scene getTicketScene() {

        Label title = new Label("当前剧目经理："+users.getName()+"  ID号:"+users.getId()+"    未售出票列表") ;
        HBox tit = new HBox() ;
        tit.setSpacing(20);
        tit.setPadding(new Insets(10));
        tit.setAlignment(Pos.CENTER);

        tit.getChildren().add(title) ;
        GridPane gp = new GridPane() ;
        gp.setVgap(20);
        gp.setHgap(60);
        gp.setAlignment(Pos.CENTER);

        gp.add(new Text("票ID"), 1,1) ;
        gp.add(new Text("演出厅ID") ,2, 1) ;
        gp.add(new Text("座位"), 3, 1) ;
        gp.add(new Text("票价"), 4,1) ;
        gp.add(new Text("票时间"),5, 1) ;
        gp.add(new Text("剧目名称"), 6,1) ;
        SeatSrv ss =  new SeatSrv() ;
        TicketSrv tsv = new TicketSrv() ;
        MovieSrv ms = new MovieSrv() ;
        ScheduleSrv ssrv = new ScheduleSrv() ;

        List<Ticket>tlist = tsv.select(" ticket_status ="+0) ;
        if(tlist.size() == 0) {
            return null ;
        }

        int row = 2 ;
        int col = 1 ;
        //变查询边打印吧！不然记录的信息太多了 q w q
        int count = 1 ;
        for(Ticket t:tlist) {
            if(t.getTicket_sold() != 0) {
                continue ;
            }
            Button delete = new Button("删除") ;
            count ++ ;
            //获取票相关联的信息，忒他妈的麻烦吧！
            ///////////////////////////////////////////////////////////////////
            Seat s = ss.select("sched_id ="+t.getSched_id()+"  and tmp_seat_id ="+t.getSeat_id()) ;
            List<Schedule>lsched =ssrv.select("sched_id="+t.getSched_id()) ;
            if(lsched.size() == 0) {
                return null ;
            }
            //获取演出计划信息
            Schedule sd =lsched.get(0) ;
            Movie mv = ms.select("play_id ="+sd.getPlay_id()) ;
            ////////////////////////////////////////////////////////////////////
            gp.add(new Text(""+t.getTicket_id()), col, row);
            gp.add(new Text(""+s.getStudio_id()), col+1, row) ;
            gp.add(new Text(""+s.getSeat_row()+"排"+s.getSeat_column()+"列"),col+2, row) ;
            gp.add(new Text(""+t.getTicket_price()), col+3, row) ;
            gp.add(new Text(""+t.getTicket_date()), col+4, row) ;
            gp.add(new Text(""+mv.getPlay_name()), col+5, row) ;
            gp.add(delete, col+6, row) ;
            //删除演出票，会将座位置为坏的，传入票id和座位id
            delete.setOnMouseClicked(e->{
                deleteTicket(t, s) ;
            });

            row ++ ;
            col = 1 ;
            if(count == 10) {
                break ;
            }
        }
        gp.setStyle("-fx-background-color:#EBF5E4; -fx-border-color: #B4D6A9;");
        VBox top = new VBox() ;
        top.setPadding(new Insets(10));
        top.setSpacing(10);
        top.getChildren().addAll(tit, gp) ;
        BorderPane bp = new BorderPane() ;
        bp.setTop(top);

        VBox but = new VBox() ;
        but.setSpacing(20);
        but.setAlignment(Pos.CENTER);
        but.setPadding(new Insets(20));
        Button find =new Button("查询票");
        find.setMinSize(140,30);
        backs.setMaxSize(140, 30);
        HBox hbs = new HBox() ;
        hbs.setSpacing(20);
        hbs.setPadding(new Insets(20));
        hbs.setAlignment(Pos.CENTER);
        hbs.getChildren().addAll(backs,find) ;
        but.getChildren().addAll(hbs);

        find.setOnMouseClicked(e->{
            findTicket() ;
        });

        bp.setTop(top);
        bp.setBottom(but);
        Scene s = new Scene(bp, 1500,1000) ;
        SetSceneStyle.sceneStyle(s);
        return s ;
    }

    public void findTicket() {
        Stage win  = new Stage() ;
        Label lb = new Label("请输入票的ID:");
        TextField tf = new TextField() ;
        tf.setMinHeight(30);
        tf.setMinWidth(90);
        HBox hb = new HBox() ;
        hb.setPadding(new Insets(20));
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(lb, tf) ;
        Button delete = new Button("删除") ;
        Button back = new Button("返回") ;
        delete.setOnMouseClicked(e->{
            int id =Integer.parseInt(tf.getText());
            TicketSrv ssrv = new TicketSrv() ;
            List<Ticket> ls = ssrv.select("ticket_id="+id) ;
            if(ls.size() == 0) {
                ConfirmBox con = new ConfirmBox() ;
                con.displaySuccess("演出票不存在！");
                win.close() ;
            }

            Ticket t = ls.get(0) ;
            SeatSrv  ss = new SeatSrv() ;
            Seat s = ss.select("sched_id="+t.getSched_id()+"  and tmp_seat_id="+t.getSeat_id()) ;
            s.setSeat_status(2);
            ss.modify(s) ;
            ssrv.delete(t.getTicket_id()) ;

            Scene sne = getTicketScene();
            swin.setScene(sne);
            swin.show() ;
            VBox vb = new VBox() ;
            vb.setAlignment(Pos.CENTER);
            vb.getChildren().addAll(new Text("删除成功"), back) ;
            Scene se = new Scene(vb, 600,400) ;
            SetSceneStyle.sceneStyle(se);
            win.setScene(se);
            win.show();
        });

        back.setOnMouseClicked(e->{
            win.close() ;
        });

        HBox hb1 = new HBox() ;
        hb1.setPadding(new Insets(20));
        hb1.setSpacing(20);
        hb1.setAlignment(Pos.CENTER);
        hb1.getChildren().addAll(delete, back) ;
        VBox vb = new VBox() ;
        vb.setPadding(new Insets(20));
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);
//        vb.getChildren().addAll(delete, back) ;
        vb.getChildren().addAll(hb, hb1) ;
        Scene s = new Scene(vb,600,400) ;
        win.setScene(s);
        SetSceneStyle.sceneStyle(s);
        win.show();
    }

    //剧目管理人员删除票的信息
    public void deleteTicket(Ticket tk, Seat st) {
        SeatSrv ssrv = new SeatSrv() ;
        TicketSrv tsrv = new TicketSrv() ;
        st.setSeat_status(2);
        //修改座位信息
        ssrv.modify(st) ;
        tsrv.delete(tk.getTicket_id()) ;
        Scene s= getTicketScene() ;
        swin.setScene(s);
        swin.show();
    }

    public void getTicketUI(Button back, Stage win, Employee user) {
            backs = back ;
            window = win ;
            users = user ;

            swin = new Stage() ;
            movieId = new TextField() ;
            movieId.setPromptText("请输入查询演出票的剧目ID");
            movieName = new TextField() ;
            movieName.setPromptText("请输入演出票相关的剧目名称");
            Button sure = new Button("确认");
            VBox vb = new VBox() ;
            vb.setPadding(new Insets(20));
            vb.setAlignment(Pos.CENTER);
            vb.setSpacing(20);

            HBox hb = new HBox() ;
            hb.setPadding(new Insets(20));
            hb.setAlignment(Pos.CENTER);
            hb.setSpacing(20);
            back.setText("返回");
            hb.getChildren().addAll(sure,  back) ;

            sure.setOnMouseClicked(e-> {
                getMovieInfo();
            });
            vb.getChildren().addAll(movieId, movieName, hb) ;
            Scene sne = new Scene(vb, 600,400) ;
            SetSceneStyle.sceneStyle(sne);
            swin.setScene(sne);
            swin.show();
    }

    //这里已经设置了外键但是还是验证一下通知用户别乱输入
    public void getMovieInfo() {

        if(movieId.getText().equals("")&&movieName.getText().equals("")) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入查询的剧目信息");
            return ;
        }
        ScheduleSrv ss = new ScheduleSrv();
        List<Schedule>ls ;
        int id = 0;
        int flag = 0 ;
        if(!movieId.getText().isEmpty()) {
            if(!isNumeric(movieId.getText())){
                ConfirmBox con = new ConfirmBox() ;
                con.displaySuccess("输入格式不对!~");
                return ;
            }
            flag = 1 ;
            //剧目的id
            id = Integer.parseInt(movieId.getText()) ;

            //获取到剧目的相关演出计划,根据演出计划查询票的id,显示票
            ls = ss.select("play_id="+id) ;
        }
        else {
            //获取剧目名称
            String name = movieName.getText() ;
            //根据剧目名称获取要查询的剧目id,根据剧目id获取演出计划列表
            ls = ss.select("play_id = (select play_id from play where play_name='"+name+"')");
        }
        //如何处理演出计划列表???先判空
        if(ls.size() == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("该剧目没有安排演出计划");
            return ;
        }
        else {
            String name  = new String() ;
            //判断用户输入的是id还是名称
            if(flag == 0){
                  name = movieName.getText() ;
            }
            //查询电影名称
            else {
                MovieSrv mv= new MovieSrv() ;
                Movie m = mv.select("play_id="+id+";") ;
                name = m.getPlay_name() ;
            }
            swin.close() ;
            Scene s = getTicketList(ls, name) ;
            if(s == null) {
                return ;
            }
            window.setScene(s);
        }
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    //获取票的场景,获取票的列表
    public Scene getTicketList(List<Schedule>ls, String name) {


        GridPane gp = new GridPane() ;
        gp.setVgap(10);
        gp.setHgap(100);
        gp.setAlignment(Pos.CENTER);

        BorderPane bdp = new BorderPane() ;
        bdp.setPadding(new Insets(20));
        //      考虑到多场演出计划,所以用链表进行处理
        //如何布局票的相应座位,演出票的时间应该和演出计划时间保持一致
        backs.setText("退出");
        HBox btHb = new HBox();
        btHb.setSpacing(20);
        btHb.setPadding(new Insets(20));
        btHb.setAlignment(Pos.CENTER);
        btHb.getChildren().add(backs) ;

        VBox vb = new VBox() ;
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);
        //获取演出厅ID
        int std_id = ls.get(0).getStudio_id() ;
        //获取演出计划id,根据演出计划查找
        int sched_id = ls.get(0).getSche_id() ;
        System.out.println("演出计划ID："+sched_id) ;
        //获取所有票的信息

        TicketSrv ts = new TicketSrv() ;
        //获取票的信息
        gp.add(new Text("票ID"), 1,1) ;
        gp.add(new Text("价格"), 2,1) ;
        gp.add(new Text("演出厅ID"), 3,1) ;
        gp.add(new Text("座位信息"), 4,1) ;
        System.out.println("演出计划ID："+sched_id) ;
        List<Ticket> tls = ts.select("sched_id="+sched_id+";") ;

        if(tls.size() == 0) {
            System.out.println("演出票的表为空！");
            return null;
        }
        Map<Ticket, Seat>ts_map=new HashMap<>();
        //获取票和相应座位的map
        getTSMap(ts_map, std_id,sched_id, tls) ;
        int row = 2 ;
        int col = 1 ;
        int count = 0 ;
        for(Ticket t: ts_map.keySet()) {

            Button sale = new Button("售票");
            //只显示可用票
            if(t.getTicket_sold() == 0){
//                System.out.println(t.getSeat_id()+ts_map.get(t).getSeat_row()+"排"+ts_map.get(t).getSeat_column()+"列");
                gp.add(new Text(""+t.getTicket_id()),col,row) ;
                gp.add(new Text(""+t.getTicket_price()),col+1,row) ;
                gp.add(new Text(""+std_id), col+2, row) ;
                gp.add(new Text(ts_map.get(t).getSeat_row()+"排"+ts_map.get(t).getSeat_column()+"列"), col+3, row) ;

                gp.add(sale, col+4, row) ;

                sale.setOnMouseClicked(e->{
                        saleRandomTicket(t, ts_map.get(t),ls, name) ;
                });
                row++ ;
                col = 1 ;
                count ++ ;
            }
            if(count == 10) {
                break ;
            }
        }
        vb.setAlignment(Pos.CENTER);
        Label t = new Label("演出厅ID："+std_id+"    剧目名称："+ name+"     开始时间："+tls.get(0).getTicket_date()) ;
        vb.getChildren().addAll(t);
        vb.getChildren().addAll(gp, btHb) ;
        bdp.setTop(vb);
        Scene s = new Scene(bdp, 1500, 1000) ;
        SetSceneStyle.sceneStyle(s);
        gp.setStyle("-fx-background-color: #999");
        return s ;
    }

    //将作座位状态和票的状态修改为1
    public void saleRandomTicket(Ticket tic, Seat s, List<Schedule>ls,String name) {

        tic.setTicket_sold(1);
        s.setSeat_status(1);
        TicketSrv ts = new TicketSrv() ;
        SeatSrv ss = new SeatSrv() ;
        ts.modify(tic) ;
        ss.modify(s) ;
        LoginedUser us = new LoginedUser() ;

        //将用户赚的钱存入到数据库中
        List<Employee>ulist  =us.select("emp_name='"+users.getName()+"'") ;
        Employee em = ulist.get(0) ;
        int cur = em.getSaleMoney() ;
        em.setSaleMoney(cur+tic.getTicket_price());
        us.modify(em) ;
        window.setScene(getTicketList(ls, name));
        ConfirmBox con = new ConfirmBox() ;
        con.displaySuccess("售票成功,订单信息如下\n"+"票的ID号:"+tic.getTicket_id()+"\n"+"演出厅ID:"+s.getStudio_id()+"\n演出时间:"+tic.getTicket_date()+"\n电影名称:"+name+
                "\n座位:"+s.getSeat_row()+"排"+s.getSeat_column()+"列\n"+"办理业务人员ID:"+users.getId()+"\n欢迎下次光临！\n");
    }

    public void backTicket(Employee user) {
        users = user ;
        window = new Stage() ;
        Label t = new Label("请输入票的ID号:") ;
        TextField id = new TextField() ;
        id.setPromptText("输入票的ID");
        id.setMinWidth(90);
        id.setMinHeight(20);
        Button sure = new Button("确认");
        Button cancer  = new Button("退出");

        sure.setOnMouseClicked(e->{

            int ret = 0 ;
            try {
                ret = getTicketId(id) ;
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            if(ret == 1) {
                window.close() ;
            }
        });

        cancer.setOnMouseClicked(e->{
            window.close() ;
        });

        HBox hb = new HBox() ;
        hb.setSpacing(20);
        hb.setPadding(new Insets(20));
        hb.getChildren().addAll(t, id) ;

        HBox hb1 = new HBox() ;
        hb1.setSpacing(20);
        hb1.setAlignment(Pos.CENTER);
        hb1.setPadding(new Insets(20));
        hb1.getChildren().addAll(sure, cancer) ;

        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        vb.setPadding(new Insets(20));
        vb.getChildren().addAll(hb,hb1) ;

        Scene s = new Scene(vb, 600,400) ;
        SetSceneStyle.sceneStyle(s);
        window.setScene(s);
        window.show() ;
    }

    public int getTicketId(TextField tid) throws ParseException {
        if(tid.getText().isEmpty()) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入票的信息");
            return 0 ;
        }
        //根据票的id获取票 的数据
        int id = Integer.parseInt(tid.getText()) ;
        TicketSrv ts = new TicketSrv() ;
        List<Ticket>tls =ts.select("ticket_id="+id);
        if(tls.size() == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("无效演出票");
            return 0 ;
        }
        Ticket tk = tls.get(0) ;
        //获取当前时间
        String curTime= TicketProcessUI.getCurTime() ;
        if(compareTime(tk.getTicket_date(), curTime)) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("对不起，票已经过期！");
            return 0 ;
        }
        //先找到作为相关信息
        SeatSrv seatsrv = new SeatSrv() ;
        Seat st = seatsrv.select("sched_id="+tk.getSched_id()+"   and  "+"tmp_seat_id="+tk.getSeat_id());
        if(st == null) {
            return 0 ;
        }
        //修改座位信息
        st.setSeat_status(0);
        seatsrv.modify(st) ;

        //修改票的信息
        tk.setTicket_sold(0);
        ts.modify(tk) ;

        //修改用户信息
        LoginedUser log = new LoginedUser() ;
        int money = log.getSaleMoney() ;
        users.setSaleMoney(money-tk.getTicket_price());
        log.modify(users) ;
        //修改电影票房相关信息
        return 1 ;
    }

    public boolean compareTime(String time1, String time2) throws ParseException {
        String format= "yyyy-MM-dd hh:mm:ss";
        DateFormat t = new SimpleDateFormat(format);
        Date timefir =t.parse(time1) ;
        Date timelast = t.parse(time2) ;
        if(timefir.getTime() < timelast.getTime()) {
            return true ;
        }
        return false ;
    }

    public void getTSMap(Map<Ticket, Seat>ts, int std_id,int sched_id , List<Ticket>tls) {

        //获取中座位和票票信息
        for(Ticket t: tls) {
            System.out.println("座位ID:"+t.getSeat_id()+"      票ID:"+std_id) ;
            Seat s = null ;
            SeatSrv ss = new SeatSrv() ;
            //根据座位id和演出厅id确定座位信息
            s = ss.select("sched_id="+sched_id+" and "+"tmp_seat_id="+t.getSeat_id()+" and studio_id="+std_id+";") ;
            ts.put(t, s) ;
        }
     }
}