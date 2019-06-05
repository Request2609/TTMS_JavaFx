package xupt.se.ttms.view.user;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.LoginedUser;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.Movie.MovieManageUI;
import xupt.se.ttms.view.Plan.PlanArangeUI;
import xupt.se.ttms.view.studio.ManageStudio;
import xupt.se.ttms.view.studio.ProcessStudioUI;

import java.util.List;

public class UsersUI extends Application {

    Employee users ;
    Scene sne ;
    List<Employee>list ;
    GridPane grid ;
    Scene changeSnes ;
    Stage userWindow ;
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void setGrid(){
        grid.setHgap(90);
        grid.setVgap(10);
        grid.add(new Text("用户ID"), 0,1) ;
        grid.add(new Text("权限"), 1,1) ;
        grid.add(new Text("用户名"), 2,1) ;
        grid.add(new Text("电话号码"), 3,1) ;
        int i = 2, j= 0 ;
        list = LoginedUser.getAllUser() ;
        for(Employee uu: list) {

            Button bt = new Button("删除") ;
            Button modiButton = new Button("修改") ;
            grid.add(new Text(uu.getId()+""),j,i ) ;
            if(uu.getAccess() == 0)
                grid.add(new Text("管理员"), j+1,i) ;
            else if(uu.getAccess() == 1)
                grid.add(new Text("经理"), j+1,i) ;
            else if(uu.getAccess() == 2)
                grid.add(new Text("售票员"), j+1,i) ;
            else {
                grid.add(new Text("售票员"), j+1,i) ;
            }
            grid.add(new Text(uu.getName()), j+2,i) ;
            grid.add(new Text(uu.getTel()), j+3, i) ;
            grid.add(bt, j+4, i) ;
            grid.add(modiButton, j+5, i) ;
            i++ ;
            bt.setOnAction(e->{
                delUser(uu) ;
            });
            modiButton.setOnAction(e->{
                modifyUserInfo(uu) ;
            });
        }
    }

    //销售员操作
    public void getSaleSne(Employee user, Stage win) {
        ManageStudio  ms = new ManageStudio(user, win) ;
        return ;
    }

    //经理操作
    public void getManagerSne(Employee user, Stage win) {

    }

    public void getStudioManagerSne(Employee user, Stage win) {
        userWindow = win ;
        Scene s = getStudioSne(user) ;
        s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        win.setScene(s);
    }

    public Scene getStudioSne(Employee user) {
        users = user ;
        BorderPane bord = new BorderPane();
        bord.setPadding(new Insets(20,20,20,20));
        updateScene(user, bord) ;
        Scene sne = new Scene(bord, 1200,1000) ;
        return sne ;
    }

    public void updateScene(Employee user, BorderPane bord) {
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);

        Label lb = new Label("演出厅管理"+" "+"当前在线经理："+user.getName()+" "+"经理ID:"+user.getId());
        lb.setStyle("-fx-background-color: #987;-fx-font-size:30");

        GridPane gp = new GridPane() ;
        gp.setStyle("-fx-background-color:#EBF5E4; -fx-border-color: #B4D6A9;");
        gp.setHgap(60) ;
        gp.setVgap(10) ;
        gp.setPadding(new Insets(20,20,20,20));

        StudioSrv ss = new StudioSrv() ;
        List<Studio>list = ss.FetchAll() ;

        gp.add(new Text("演出厅ID"), 1,1) ;
        gp.add(new Text("名称"), 2,1);
        gp.add(new Text("排数"),3,1);
        gp.add(new Text("列数"),4,1);
        gp.add(new Text("介绍"),5,1);
        gp.add(new Text("状态"), 6,1) ;
        int cols = 1;
        int rows = 2 ;
        for(Studio s : list) {
            Button delete = new Button("删除");
            Button modify = new Button("修改");
            delete.setOnMouseClicked(e->{
                deleteStudio(s);
            });

            modify.setOnMouseClicked(e->{
                modifyStudio(s) ;
            });
            delete.setMinSize(100,30);
            modify.setMinSize(100,30);

            gp.add(new Text(s.getID()+""), cols, rows) ;
            gp.add(new Text(s.getName()+""), cols+1, rows);
            gp.add(new Text(s.getRowCount()+""), cols+2,rows) ;
            gp.add(new Text(s.getColCount()+""), cols+3,rows) ;
            gp.add(new Text(s.getIntroduction()+""), cols+4,rows) ;
            if(s.getStatus() == 0) {
                gp.add(new Text("闲置"), cols+5, rows);
            }
            if(s.getStatus() == 1) {
                gp.add(new Text("已安排"), cols+5, rows) ;
            }

            gp.add(delete, cols+6, rows) ;
            gp.add(modify, cols+7, rows) ;
            rows++ ;
        }

        vb.getChildren().addAll(lb, gp) ;

        Button add = new Button("添加演出厅") ;
        add.setOnMouseClicked(e->{
            addStudio() ;
        });
        add.setMinSize(150,40);
        Button quit = new Button("退出") ;
        quit.setOnMouseClicked(e->{
            userWindow.close() ;
        });

        quit.setMinSize(150,40);
        Button managemv = new Button("剧目管理") ;
        Button ticket = new Button("演出票管理") ;

        managemv.setMinSize(150,40);
        MovieManageUI mm = new MovieManageUI() ;

        managemv.setMinSize(150,40);
        Button b= new Button("返回上一页") ;
        Scene mvSnes = mm.getMovieSne(user,b, userWindow) ;
        managemv.setOnAction(e->{
            SetSceneStyle.sceneStyle(mvSnes) ;
            userWindow.setScene(mvSnes);
        });

        b.setOnAction(e->{
            Scene s = getStudioSne(users) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
        });

        Button b1 = new Button("返回上一页") ;
        Button plan = new Button("演出计划管理");
        plan.setMinSize(150,40);
        PlanArangeUI pl = new PlanArangeUI() ;
        Scene sne1= pl.getPlanSne(users, b1, userWindow);
        b1.setOnAction(e->{
            Scene s = getStudioSne(users) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
        });

        plan.setOnAction(e-> {
            SetSceneStyle.sceneStyle(sne1);
            userWindow.setScene(sne1);
        });

        Button refresh =  new Button("刷新页面") ;
        refresh.setOnMouseClicked(e->{
            Scene s = getStudioSne(users) ;
            userWindow.setScene(s);
            SetSceneStyle.sceneStyle(s);
            userWindow.show() ;
        });


        HBox hb = new HBox() ;
        hb.setPadding(new Insets(20,20,20,20)) ;
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(add, quit, managemv, refresh, plan, ticket);

        bord.setTop(vb);
        bord.setBottom(hb);
    }

    public void addStudio() {
        ProcessStudioUI ps = new ProcessStudioUI() ;
        ps.addStudioUI();
//        Scene s = updateScene(users) ;
//        SetSceneStyle.sceneStyle(s);
//        userWindow.setScene(s);
    }

    public void modifyStudio(Studio s) {
        ProcessStudioUI mod = new ProcessStudioUI() ;
        mod.modifyStudioUI(s);
    }

    public void deleteStudio(Studio s) {
        StudioSrv studio = new StudioSrv() ;
        studio.delete(s.getID()) ;
        Scene ss = getStudioSne(users) ;
        userWindow.setScene(ss);
        SetSceneStyle.sceneStyle(ss);
        userWindow.show() ;
    }

    //管理员操作
    public void getUserAdminSne(Employee user, Stage stage){
        users = user ;
        userWindow = stage ;
        //放置button的地方
        userWindow.setScene(updateScene(user));
        SetSceneStyle.sceneStyle(sne);
        userWindow.setScene(sne);
    }

    public void delUser(Employee uu) {
        LoginedUser user = new LoginedUser() ;
        int ret = user.delete(uu.getId()) ;
        if(ret != 0) {
            Scene s = updateScene(uu) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
            userWindow.show() ;
        }
        else {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("删除失败！请重试一下！");
        }
    }

    //修改用户信息
    public void modifyUserInfo(Employee user){

        Scene s = updateScene(user) ;
        SetSceneStyle.sceneStyle(sne);
        userWindow.setScene(s);
        userWindow.show() ;
    }

    public Scene updateScene(Employee user) {
        //放置button的地方
        BorderPane bord = new BorderPane() ;

        HBox hb = new HBox() ;
        VBox vb = new VBox() ;

        vb.setSpacing(50);
        vb.setPadding(new Insets(20,20,20,20)) ;
        vb.setAlignment(Pos.CENTER);
        Label lb = new Label("用户管理     管理员名称："+user.getName()+"   管理员ID:"+user.getId());
        lb.setStyle("-fx-font-size:50");
        lb.setStyle("-fx-background-color: #987");
        HBox hbLb = new HBox() ;
        hbLb.setAlignment(Pos.CENTER);
        hbLb.getChildren().add(lb) ;

        grid = new GridPane() ;
        setGrid();
        vb.getChildren().addAll(hbLb, grid);
        bord.setTop(vb);

        grid.setId("gridpane") ;
        grid.setAlignment(Pos.CENTER);

        Button addButton = new Button("添加用户") ;
        Button update = new Button("刷新页面") ;
        Button quit = new Button("退  出") ;
        quit.setOnMouseClicked(e->{
            userWindow.close() ;
        });
        setButton(addButton ,quit, update) ;
        hb.setSpacing(50);
        hb.setPadding(new Insets(20,20,100,100));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(addButton,quit, update) ;
        bord.setBottom(hb);
        sne = new Scene(bord, 1200, 1000) ;
        return sne ;
    }

    void setButton(Button addButton ,Button quit, Button update) {
        addButton.setMinWidth(150);
        quit.setMinWidth(150);
        update.setMinWidth(150);
        addButton.setOnMouseClicked(e->{
            UserSceneUI sce = new UserSceneUI();
            Employee uu = new Employee() ;
            sce.addUser(uu);
        });

        update.setOnAction(e->{
            Scene s = updateScene(users) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
            userWindow.show() ;
        });
    }
}
