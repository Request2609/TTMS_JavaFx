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
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.service.MovieSrv;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.LoginedUser;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.Movie.MovieManageUI;
import xupt.se.ttms.view.Plan.PlanArangeUI;
import xupt.se.ttms.view.sellticket.ProcessTicketEvUI;
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
    Button backs ;
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
                grid.add(new Text("剧目经理"), j+1,i) ;
            else if(uu.getAccess() == 2)
                grid.add(new Text("售票员"), j+1,i) ;
            else if(uu.getAccess() == 6){
                grid.add(new Text("财务经理"), j+1,i) ;
            }
            else{
                grid.add(new Text("财务经理"), j+1,i) ;
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
                modifyUserInfo(userWindow, uu, users, backs) ;
            });
        }
    }

    //销售员操作
    public void getSaleSne(Employee user,Button quit, Stage win) {
        ManageStudio  ms = new ManageStudio(user,quit, win) ;
        return ;
    }

    public void getStudioManagerSne(Employee user,Button quit, Stage win) {
        backs = quit ;
        userWindow = win ;
        users = user ;
        Scene s = getStudioSne(user) ;
        SetSceneStyle.sceneStyle(s);
        win.setScene(s);
    }

    public Scene getStudioSne(Employee user) {
        users = user ;
        BorderPane bord = new BorderPane();
        bord.setPadding(new Insets(20,20,20,20));
        updateScene(user, bord) ;
        Scene sne = new Scene(bord, 1500,1000) ;
        return sne ;
    }

    public void updateScene(Employee user, BorderPane bord) {
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);

        Label lb = new Label("演出厅管理"+" "+"当前在线经理："+user.getName()+" "+"经理ID:"+user.getId());
//        lb.setStyle("-fx-background-color: #987;-fx-font-size:30");

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

        backs.setMinSize(150,40);
        Button managemv = new Button("剧目管理") ;
        Button ticket = new Button("管理演出票") ;



        //演出票管理界面

        managemv.setMinSize(150,40);
        MovieManageUI mm = new MovieManageUI() ;

        managemv.setMinSize(150,40);
        Button b= new Button("返回上一页") ;
        Scene mvSnes = mm.getMovieSne(user,b, userWindow) ;
        managemv.setOnAction(e->{
            SetSceneStyle.sceneStyle(mvSnes) ;
            userWindow.setScene(mvSnes);
        });

        ticket.setOnMouseClicked(e->{
            ManageTicket(b) ;
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
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
            userWindow.show() ;
        });


        HBox hb = new HBox() ;
        hb.setPadding(new Insets(20,20,20,20)) ;
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(add, backs, managemv, refresh, plan, ticket);

        bord.setTop(vb);
        bord.setBottom(hb);
    }

    public void ManageTicket(Button back) {
        ProcessTicketEvUI pteu = new ProcessTicketEvUI() ;
        pteu.manageTicket(userWindow ,back, users) ;
    }

    public void addStudio() {
        ProcessStudioUI ps = new ProcessStudioUI() ;
        ps.addStudioUI();
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
    public void getUserAdminSne(Employee user,Button back, Stage stage){
        users = user ;
        userWindow = stage ;
        //放置button的地方
        backs = back ;
        userWindow.setScene(updateScene(user, backs, userWindow));
        SetSceneStyle.sceneStyle(sne);
        userWindow.setScene(sne);
    }

    public void delUser(Employee uu) {
        LoginedUser user = new LoginedUser() ;
        int ret = user.delete(uu.getId()) ;
        if(ret != 0) {
            Scene s = updateScene(uu, backs, userWindow) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
            userWindow.show() ;
        }
        else {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("删除失败！请重试一下！");
        }
    }

    public Button getBackButton() {

        Button back = new Button("返回") ;
        back.setOnMouseClicked(e->{
            Scene s = updateScene(users, backs, userWindow) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
        });
        return back ;
    }

    //修改用户信息
    public void modifyUserInfo(Stage userWindow,  Employee user, Employee users, Button back){
        this.userWindow = userWindow ;
        this.users = users ;
        backs = back ;
        ModifyUserInfo mui = new ModifyUserInfo() ;
        Button bt  = getBackButton() ;
        Scene sne = mui.getModifyScene(users, user, bt);
        SetSceneStyle.sceneStyle(sne);
        userWindow.setScene(sne);
    }

    public Scene updateScene(Employee user, Button backs, Stage win) {
        userWindow = win ;
        this.backs = backs;
        users = user ;
        //放置button的地方
        BorderPane bord = new BorderPane() ;

        HBox hb = new HBox() ;
        VBox vb = new VBox() ;

        vb.setSpacing(50);
        vb.setPadding(new Insets(20,20,20,20)) ;
        vb.setAlignment(Pos.CENTER);
        Label lb = new Label("用户管理     管理员名称："+user.getName()+"   管理员ID:"+user.getId());
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
        Button query = new Button("查询用户") ;
        Button update = new Button("刷新页面") ;

        query.setOnMouseClicked(e->{
             queryUsers() ;
        });

        backs.setMinWidth(150);
        setButton(addButton , update) ;
        hb.setSpacing(50);
        hb.setPadding(new Insets(20,20,100,100));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(addButton,backs, update, query) ;
        bord.setBottom(hb);
        sne = new Scene(bord, 1500, 1000) ;
        return sne ;
    }
    public void queryUsers() {

        Button bt = getBackButton() ;
        QueryUserScene qus = new QueryUserScene();
        Scene s = qus.getQueryUserSne(userWindow, bt, users) ;
        SetSceneStyle.sceneStyle(s);
        userWindow.setScene(s);
        userWindow.show();
    }

    void setButton(Button addButton , Button update) {

        addButton.setMinWidth(150);
        update.setMinWidth(150);
        addButton.setOnMouseClicked(e->{
            UserSceneUI sce = new UserSceneUI();
            Employee uu = new Employee() ;
            sce.addUser(uu);
        });

        update.setOnAction(e->{
            Scene s = updateScene(users, backs, userWindow) ;
            SetSceneStyle.sceneStyle(s);
            userWindow.setScene(s);
            userWindow.show() ;
        });
    }

    public void getFinaceManagerSne(Employee user, Button back, Stage window){
        userWindow = window ;
        backs = back ;
        users = user ;
        Scene s= getSaleMoney();
        SetSceneStyle.sceneStyle(s);
        window.setScene(s);
        window.show();
    }

    public Scene getSaleMoney() {
        Label title = new Label("当前财务经理："+users.getName()+"    ID:"+users.getId()) ;
        Label title1 = new Label("售票销售额前十");
        Label title2 = new Label("票房前十") ;

        VBox vb1 = new VBox() ;
        vb1.setPadding(new Insets(20));
        vb1.setSpacing(20);
        vb1.setAlignment(Pos.CENTER);
        vb1.getChildren().add(title1) ;

        VBox vb2 = new VBox() ;
        vb2.setPadding(new Insets(20));
        vb2.setSpacing(20);
        vb2.setAlignment(Pos.CENTER);
        vb2.getChildren().add(title2) ;

        List<Employee>ls = getAllEmpList() ;
        List<Movie>lm = getAllMovieList() ;
        GridPane gp = new GridPane() ;
        gp.setHgap(90);
        gp.setVgap(20);
        gp.setAlignment(Pos.CENTER);

        GridPane gemp = new GridPane() ;
        gemp.setHgap(90);
        gemp.setVgap(20);
        gemp.setAlignment(Pos.CENTER);

        GridPane gmvp= new GridPane() ;
        gmvp.setHgap(90);
        gmvp.setVgap(20);
        gmvp.setAlignment(Pos.CENTER);
        int row_start = 1 ;
        int col_start = 1 ;

        gemp.add(new Text("销售员ID"),col_start,row_start ) ;
        gemp.add(new Text("名称"), col_start+1, row_start) ;
        gemp.add(new Text("销售额"), col_start+2, row_start) ;
        int row = row_start+1 ;
        int col = 1;
        //只显示销售额为前十名销售员的信息
        int count = 1 ;
        for(Employee emp : ls) {
            count ++ ;
            gemp.add(new Text(emp.getId()+""),col ,row) ;
            gemp.add(new Text(emp.getName()), col+1, row) ;
            gemp.add(new Text(emp.getSaleMoney()+""), col+2, row) ;
            row++ ;
            if(count == 10) {
                count = 1 ;
                break ;
            }
        }
        vb1.getChildren().add(gemp) ;
        row_start = 1 ;
        col_start = 1 ;
        gmvp.add(new Text("电影ID"), col_start, row_start);
        gmvp.add(new Text("名称"), col_start+1, row_start) ;
        gmvp.add(new Text("前十票房"), col_start+2, row_start) ;
        row = row_start+1 ;
        col = col_start;
        for(Movie m : lm) {
            count ++ ;
            gmvp.add(new Text(m.getPlay_id()+""), col, row) ;
            gmvp.add(new Text(m.getPlay_name()), col+1, row) ;
            gmvp.add(new Text(m.getTicket_money()+""), col+2, row) ;
            row++ ;
            if(count==10) {
                break ;
            }
        }

        vb2.getChildren().add(gmvp) ;
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(30);
        vb.setPadding(new Insets(20));
        title.setStyle("-fx-font-size: 30");
//        gp.setStyle("-fx-background-color:#EBF5E4; -fx-border-color: #B4D6A9;");
        gmvp.setStyle("-fx-background-color:#EBF5E4; -fx-border-color: #B4D6A9;");
        gemp.setStyle("-fx-background-color:#EBF5E4; -fx-border-color: #B4D6A9;");
        vb.getChildren().addAll(title, title2,gmvp, title1,gemp, backs) ;

        Scene s = new Scene(vb, 1500, 1000) ;
        return s ;
    }

    //获取所有售票员售票信息列表
    List<Employee>getAllEmpList() {
        LoginedUser log = new LoginedUser() ;
        List<Employee>ls = log.select("access="+1+"   order by emp_sale_money;") ;
        return ls ;
    }

    //获取所有剧目信息
    List<Movie> getAllMovieList() {
        MovieSrv ms = new MovieSrv() ;
        //找出上映的电影和并升序排列顺序
        List<Movie>lm = ms.selectAll(" play_status=2 order by ticket_money; ");
        return lm ;
    }
}
