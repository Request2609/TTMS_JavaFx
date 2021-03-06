package xupt.se.ttms.view.Ticket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.*;
import xupt.se.ttms.service.*;
import xupt.se.ttms.view.user.ConfirmBox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class TicketProcessUI {


    TextField sched_id ;
    TextField ticket_price ;
    TextField ticket_locked_time ;

    public void addTicket() {
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(10);
        vb.setPadding(new Insets(20));
        Stage win = new Stage() ;
        win.setTitle("生成演出票");
        setText() ;
        Button ok = new Button("确认") ;
        Button cancer = new Button("取消") ;
        ok.setOnMouseClicked(e->{
              int ret = 0 ;
              ret = getTicketInfo() ;
              if(ret == 1) {
                  win.close() ;
              }
        });

        cancer.setOnMouseClicked(e->{
            win.close();
        });

        HBox hb = new HBox() ;
        hb.getChildren().addAll(ok, cancer) ;
        hb.setPadding(new Insets(20));
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(sched_id, ticket_price, hb) ;
        Scene sne = new Scene(vb, 600,400) ;
        SetSceneStyle.sceneStyle(sne);
        win.setScene(sne);
        win.show();
    }

    public int getTicketInfo() {

        if(sched_id.getText().equals("") ||ticket_price.getText().equals("")) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("信息不完整");
            return 0 ;
        }

        Ticket tk = new Ticket() ;
        tk.setSched_id(Integer.parseInt(sched_id.getText()));
        tk.setTicket_price(Integer.parseInt(ticket_price.getText())) ;

        //获取对应ID的演出计划
        ScheduleSrv ss =  new ScheduleSrv() ;
        List<Schedule> list = ss.select("sched_id="+tk.getSched_id()) ;
        if(list.size() == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("无法找到演出计划了");
            return 0 ;
        }

        //获取演出计划，修改演出计划的状态
        Schedule s = list.get(0) ;
        //如果演出票已经生成，则将不能再生成演出票
//        int flag = 0 ;
        if(s.getStatus() == 1) {
            ConfirmBox con = new ConfirmBox();
            con.displaySuccess("生成演出票失败！演出计划已经实施！");
            return 0;
        }
        s.setStatus(1);
        ss.modify(s) ;

        String time = s.getSched_time() ;
        //获取演出厅信息
        StudioSrv sv = new StudioSrv() ;
        Studio sd = sv.Fetch(s.getStudio_id()+"") ;
        if(sd == null) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("演出厅不存在!");
            return 0 ;
        }

        sd.setStatus(1);
        sv.modify(sd) ;

        MovieSrv mv = new MovieSrv() ;
        //获取剧目信息
        Movie mm = mv.select("play_id="+s.getPlay_id()+";") ;
        if(mm == null) {
            ConfirmBox con = new ConfirmBox();
            con.displaySuccess("剧目不存在");
            return 0;
        }

        mm.setPlay_status(2);
        //生成票
        int row = sd.getRowCount() ;
        int col = sd.getColCount() ;
        int row_up  =  1;
        int col_up = 1 ;

        int ticketNum = row*col ;

        //座位链表和票的链表
        List<Ticket>lticket = new LinkedList<>() ;
        List <Seat>lseat = new LinkedList<>() ;
        //这里生成票的
        for(int i = 1 ; i<=ticketNum; i++) {
            //先生成座位再根据座位ID信息产生票
            Seat seat = new Seat();
            seat.setSched_id(s.getSche_id());
            seat.setStudio_id(s.getStudio_id());
            seat.setSeat_row(row_up);
            seat.setSeat_column(col_up);
            //当不到一行总列数时列递加
            //否则将列数重新置为1,行数加1
            seat.setSeat_status(0);
            seat.setTmp_seat_id(i);
            lseat.add(seat) ;

            ///////////////////////////////////将座位先存到数据库中
            //tmp_seat_id 和sched_id决定票的唯一性
            //设置票的信息
            //票记录的是座位的tmp_seat_id!!!不是真正的ID,因为这里设置外键感觉有点麻烦，
            // 所以算了，设置个临时ID
            Ticket tmp = new Ticket() ;
            tmp.setSched_id(s.getSche_id());
            tmp.setSeat_id(i);
            //0为未售出 1为售出
            tmp.setTicket_sold(0);
            tmp.setTicket_locked_times(10);
            //设置票的时间和计划保持一致
            tmp.setTicket_date(time) ;
            tmp.setTicket_price(s.getSched_ticket_price());
            //演出计划里面有演出厅
            lticket.add(tmp) ;
            col_up ++ ;
            if(col_up > col) {
                col_up = 1 ;
                row_up++ ;
            }
        }

        //将作为插入数据库中
        SeatSrv seatsrv = new SeatSrv() ;
        int res = seatsrv.insert(lseat) ;
        if( res == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("存储座位信息失败");
            return  0 ;
        }
        //将票插入数据库中
        TicketSrv ts = new TicketSrv() ;
        res = ts.add(lticket) ;
        if(res ==0 ){
            ConfirmBox con =new ConfirmBox() ;
            con.displaySuccess("存储票信息失败!");
            return 0 ;
        }

        System.out.println("行数："+row+"        "+"列数："+col) ;
        //修改剧目中的票价信息
        mm.setTicket_money(tk.getTicket_price());
        mv.modify(mm) ;
        return 1 ;
    }

    public synchronized  void modify(Studio sd, int sched_id) {
        StudioSrv sv = new StudioSrv() ;
        ScheduleSrv ss = new ScheduleSrv() ;
        sv.modify(sd);
        //删除相应演出计划
        ss.delete(sched_id);
    }

    public static String getCurTime() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = dateFormat.format(calendar.getTime());
        return s ;
    }
    public static long getTime() {
        return 0 ;
    }
    public void setText() {
        sched_id = new TextField() ;
        sched_id.setPromptText("请输入演出计划ID");
        ticket_price = new TextField() ;
        ticket_price.setPromptText("请设置票的价格");
    }
}
