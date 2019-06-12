package xupt.se.ttms.view.sellticket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.*;
import xupt.se.ttms.service.*;
import xupt.se.ttms.view.user.ConfirmBox;

import java.util.LinkedList;
import java.util.List;


public class SaleTicketScene {

    Stage window;
    Button backs ;
    Movie mvs ;
    Employee users ;
    //先获取演出计划,再根据演出计划获取座位信息
    public void saleTicket(Stage win, Button back, Movie mv, Employee  uu) {
           mvs = mv ;
           users = uu ;
           backs = back ;
           window = win ;
           Scene s = getMovieUI(mv) ;
           if(s == null) {
               ConfirmBox con = new ConfirmBox() ;
               con.displaySuccess("没有该剧目的演出计划～！");
               return ;
           }
           win.setScene(s);
           win.show() ;
    }

    public boolean isEmpty(List<Seat>ls) {
        for(Seat s:ls) {
            if(s.getSeat_status() == 0) {
                return false ;
            }
        }
        return true;
    }

    public Scene  getMovieUI(Movie mv) {

        VBox vb = new VBox() ;
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        HBox hb = new HBox() ;
        hb.setSpacing(10);
        hb.setPadding(new Insets(10));
        hb.setAlignment(Pos.CENTER);

        GridPane gp =new GridPane() ;
        gp.setVgap(20);
        gp.setHgap(20);
        gp.setAlignment(Pos.CENTER);

        List<Schedule> ls ;
        //获取演出计划列表
        ScheduleSrv ss = new ScheduleSrv();
        ls = ss.select("play_id="+mv.getPlay_id()+";") ;
        if(ls.size() == 0) {
            return null ;
        }
        //当存在该剧
        //查看每一条演出计划相关演出厅座位情况
        int flag = 0 ;
        int i = 0 ;
        for(Schedule sd: ls) {
            int studio_id = sd.getStudio_id() ;
            SeatSrv seatsrv = new SeatSrv() ;
            List<Seat>lseat = seatsrv.selectAll("studio_id="+studio_id+";") ;
            System.out.println("演出厅ID:"+studio_id+"    查找到的票的数量："+lseat.size()) ;
            if(!isEmpty(lseat))  {
                flag  =1 ;
                break ;
            }
            i++ ;
        }

        if(flag == 0) {
            return null;
        }

        Schedule s =ls.get(i) ;
        //获取座位信息
        List<Seat> lseat ;
        SeatSrv seats = new SeatSrv() ;
        lseat = seats.selectAll("sched_id="+s.getSche_id()) ;
        StudioSrv sv = new StudioSrv() ;

        Studio sd = sv.Fetch("studio_id="+s.getStudio_id()) ;
        Label title = new Label("目前安排场次总共"+ls.size()+"场　　"+"当前演出厅ID:"+s.getStudio_id()+"    即将上映剧目:"+mv.getPlay_name()+"    开始时间："+s.getSched_time()+"----"+s.getSched_time_end()) ;


        if(sd == null) {
            return null ;
        }

        for(Seat tmp: lseat) {
            TicketSrv ts = new TicketSrv() ;
            List<Ticket>ltk;
            ltk = ts.select("seat_tmp_id="+tmp.getTmp_seat_id()+" and sched_id="+tmp.getSched_id()+";") ;
            int ticketid = ltk.get(0).getTicket_id() ;
            System.out.println("票ID"+tmp.getSeat_id()+"   "+tmp.getSeat_row()+"排"+tmp.getSeat_column()+"列");
            if(tmp.getSeat_status()== 0) {
                ImageView iv = setImageView(0) ;
                gp.add(iv, tmp.getSeat_column(), tmp.getSeat_row()+1);
                //点击事件处理，意味着买票
                iv.setOnMouseClicked(e->{
                    ProcessSaleTicket(tmp, s, mv) ;
                });
            }

            if(tmp.getSeat_status()== 1) {
                ImageView iv = setImageView(1) ;
                gp.add(iv, tmp.getSeat_column(), tmp.getSeat_row()+1);
                iv.setOnMouseClicked(e->{
                    ConfirmBox con = new ConfirmBox() ;
                    con.displaySuccess(tmp.getSeat_id()+"号座位该座位已被占用!  票ID:"+ticketid);
                });
            }

            if(tmp.getSeat_status()== 2) {
                ImageView iv= setImageView(2);
                gp.add(iv, tmp.getSeat_column(), tmp.getSeat_row()+1);
                iv.setOnMouseClicked(e->{
                    ConfirmBox con = new ConfirmBox() ;
                    con.displaySuccess(tmp.getSeat_id()+"号座位不可用!   票ID:"+ticketid);
                });
            }
        }

        VBox lay = new VBox() ;
        lay.setAlignment(Pos.CENTER);
        lay.setSpacing(20);
        lay.setPadding(new Insets(20));
        lay.getChildren().add(gp) ;

        gp.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(title, hb, lay, backs) ;
        Scene scne = new Scene(vb, 1500,1000) ;
        SetSceneStyle.sceneStyle(scne);
        return scne ;
    }

    public ImageView setImageView(int flag){
        ImageView iv = new ImageView();
        if(flag == 0) {
            Images image1 = new Images();
            image1.setImage("https://www.hao336.cn/pictures/green.png");
            iv.setImage(image1.getImage());
            iv.setFitWidth(40);
            iv.setFitHeight(40);
        }
        if(flag == 1) {
            Images image2 = new Images();
            image2.setImage("https://www.hao336.cn/pictures/gray.png");
            iv.setImage(image2.getImage());
            iv.setFitWidth(40);
            iv.setFitHeight(40);
        }
        if(flag == 2) {
            Images image3 = new Images();
            image3.setImage("https://www.hao336.cn/pictures/red.png");
            iv.setImage(image3.getImage());
            iv.setFitWidth(40);
            iv.setFitHeight(40);
        }
        return iv ;
    }

    //处理卖票事件，
    //1.修改座位信息
    //2.修改票信息
    //3.修改用户信息
    //4.修改电影票房信息
    public void ProcessSaleTicket(Seat t, Schedule s, Movie mv) {

        SeatSrv seatsrv = new SeatSrv() ;
        t.setSeat_status(1);
        TicketSrv  ticketsrv= new TicketSrv() ;
        //根据座位中的演出计划id和seat_id确定唯一票
        List<Ticket>ls =ticketsrv.select("sched_id ="+t.getSched_id()+"  and "+"  seat_tmp_id="+t.getTmp_seat_id()+";") ;
        if(ls.size() == 0) {
            return  ;
        }
        LoginedUser log = new LoginedUser() ;
        int money = users.getSaleMoney();
        users.setSaleMoney(money+s.getSched_ticket_price());
        Ticket tk = ls.get(0) ;
        tk.setTicket_sold(1);
        int play_id = s.getPlay_id() ;
        MovieSrv moviesrv = new MovieSrv() ;
        Movie m = moviesrv.select("play_id="+play_id) ;
        int mMoney =m.getTicket_money() ;
        m.setTicket_money(mMoney+tk.getTicket_price());
        //想起来了，这里在生成票时输入的票价一定要和电影中的票价相统一
        moviesrv.modify(m) ;
        ticketsrv.modify(tk) ;
        seatsrv.modify(t);
        log.modify(users) ;

        window.setScene(getMovieUI(mvs));
        window.show();
        ConfirmBox con = new ConfirmBox() ;
        con.displaySuccess("售票成功,订单信息如下\n"+"票的ID号:"+tk.getTicket_id()+"\n"+"演出厅ID:"+t.getStudio_id()+"\n演出时间:"+tk.getTicket_date()+"\n电影名称:"+mvs.getPlay_name()+"\n时长:"+mvs.getPlay_Length()+
                "\n座位:"+t.getSeat_row()+"排"+t.getSeat_column()+"列\n"+"办理业务人员ID:"+users.getId()+"\n欢迎下次光临！\n");
    }
}
