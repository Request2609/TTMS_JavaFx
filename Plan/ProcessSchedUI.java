package xupt.se.ttms.view.Plan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.omg.CORBA.INTERNAL;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.view.user.ConfirmBox;

import java.sql.Date;

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
        s.setSched_time(Date.valueOf(show_date.getText()));
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
        //验证剧目是否存在?
        sh.setPlay_id(Integer.parseInt(play_id.getText()));
        //根据剧目ID,修改剧目的票价,修改剧目的状态
        sh.setSched_ticket_price(Integer.parseInt(ticket_price.getText())) ;
        sh.setSched_time(Date.valueOf(show_date.getText()));
        ScheduleSrv sched = new ScheduleSrv() ;
        int ret =sched.add(sh) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("添加数据失败");
            return 0;
        }
        return 1 ;
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
}
