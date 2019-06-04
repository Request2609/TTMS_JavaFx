package xupt.se.ttms.view.Plan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.MovieSrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.user.ConfirmBox;
import xupt.se.ttms.view.user.LoginUI;
import xupt.se.ttms.view.user.UserSceneUI;
import xupt.se.ttms.view.user.UsersUI;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanArangeUI {
    Scene sne ;
    int width,height ;
    Label lb  ;
    Stage win ;
    Button bk ;
    Employee users;
    public GridPane setGrid() {

        GridPane g = new GridPane();
        g.setPadding(new Insets(20));
        g.setHgap(60);
        g.setVgap(10);

        List<Schedule>list = null ;
        ScheduleSrv sc=  new ScheduleSrv() ;
        list = sc.select("") ;
        Map<Schedule , Studio> studio_map = new HashMap<>() ;
        Map<Schedule , Movie> play_map = new HashMap<>();
        getAllInfo(studio_map, play_map,list) ;
        g.add(new Text("演出计划ID"), 1,1);
        g.add(new Text("演出厅名称"), 2,1);
        g.add(new Text("电影名称"), 3,1);
        g.add(new Text("演出安排时间"), 4,1);
        g.add(new Text("票价"),5,1) ;
        int row = 2 ;
        int col = 1 ;

        for(Schedule s: list) {

            Button delete = new Button("取消 ") ;
            Button modify= new Button("修改") ;
            delete.setMinSize(80,30);
            modify.setMinSize(80,30);

            g.add(new Text(s.getSche_id()+""), col, row);
            if(play_map.get(s)!= null)
            g.add(new Text(studio_map.get(s).getName()+""), col+1, row);
            if(play_map.get(s)!= null)
            g.add(new Text(play_map.get(s).getPlay_name()+""), col+2, row);
            else {
                g.add(new Text(""), col+2, row);
            }
            g.add(new Text(s.getSched_time()+""), col+3, row);
            g.add(new Text(s.getSched_ticket_price()+""), col+4, row);
            g.add(delete,  col+5, row);
            g.add(modify,  col+6, row) ;
            row++ ;
            delete.setOnMouseClicked(e->{
                deleteSchedule(s) ;
            });
            modify.setOnMouseClicked(e->{
                modifySchedule(s) ;
            });
        }
        g.setStyle("-fx-background-color: #EBF5E4;");
        return g ;
    }

    public void deleteSchedule(Schedule s) {
        ScheduleSrv sched = new ScheduleSrv() ;
        int ret = sched.delete(s.getSche_id()) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("删除失败!");
        }
        Scene ss = getPlanSne(users, bk, win) ;
        ss.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        win.setScene(ss);
        win.show() ;
    }

    public void modifySchedule(Schedule s) {
            ProcessSchedUI ps = new ProcessSchedUI() ;
            ps.modifySchedule(s);
    }

    public void getAllInfo(Map<Schedule , Studio> studio_map,Map<Schedule , Movie> play_map ,List<Schedule>list) {
        //获取所有演出计划信息
        for(Schedule s: list) {
            MovieSrv m = new MovieSrv() ;
            Movie mm = m.select(s.getPlay_id()) ;
            if(mm != null) {
                System.out.println(mm.getPlay_name()) ;
            }
            play_map.put(s,mm) ;
            StudioSrv  ss = new StudioSrv() ;
            Studio st = ss.Fetch(""+s.getStudio_id()) ;
            studio_map.put(s,st) ;
            if(st != null) {
                System.out.println(st.getName()) ;
            }
        }
    }

    public Scene getPlanSne(Employee user,  Button back, Stage userWindow) {
        bk = back ;
        users = user ;
        win = userWindow ;
        HBox h = new HBox();
        VBox v=new VBox();

        BorderPane bord = new BorderPane() ;

        h.setAlignment(Pos.CENTER);
        lb=new Label("当前经理:"+user.getName()+"  ID:"+user.getId()+"   当前演出计划表") ;
        lb.setStyle("-fx-background-color: #987;");
        h.setAlignment(Pos.CENTER);
        h.getChildren().add(lb) ;

        GridPane g = setGrid() ;
        v.getChildren().addAll(lb, g) ;
        bord.setTop(v) ;

        HBox hb = new HBox() ;
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(20);
        hb.setPadding(new Insets(20));

        Button add = new Button("添加演出计划");
        Button makeTic = new Button("生成演出票") ;
        Button query = new Button("查询演出计划") ;
        Button fresh = new Button("刷新页面") ;
        add.setOnMouseClicked(e->{
            addSchedule() ;
        });
        makeTic.setOnMouseClicked(e->{
            makeTickets() ;
        });
        query.setOnMouseClicked(e->{
            querySchedule() ;
        });

        hb.getChildren().addAll(back, add, makeTic, query,fresh) ;

        fresh.setOnAction(e->{
            Scene s = getPlanSne(user,back,userWindow) ;
            s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
            userWindow.setScene(s) ;
            userWindow.show();
        });

        bord.setBottom(hb);
        bord.setPadding(new Insets(20));
        Scene s = new Scene(bord,1200,1000) ;
        return s ;
    }

    public void addSchedule(){
        ProcessSchedUI ps = new ProcessSchedUI() ;
        ps.addSchedule();
    }

    public void makeTickets() {

    }

    public void querySchedule() {

    }
}
