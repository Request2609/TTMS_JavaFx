package xupt.se.ttms.view.user;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;

import javax.xml.soap.Text;


public class UserSceneUI extends Application {

    boolean close = false ;
    RadioButton admin;
    RadioButton manager;
    RadioButton sales;
    Stage windows;
    TextField tf;
    PasswordField pf;
    Employee user ;
    @Override
    public void start(Stage primaryStage) throws Exception {
    }

    public void getLogInScene(Stage pri) {
        windows = pri ;
        Scene  cne = getLogInScene() ;
        windows.setScene(cne);
        windows.show();
    }

    public Scene getLogInScene() {
        Scene sne = null;
        user = new Employee();
        VBox layout = new VBox();
        layout.setPadding(new Insets(50, 50, 50, 50));
        layout.setSpacing(30);
        layout.setAlignment(Pos.CENTER);

        HBox layout1 = new HBox();
        layout1.setPadding(new Insets(50, 50, 50, 50));
        layout1.setSpacing(20);
        layout1.setAlignment(Pos.CENTER);
        final ToggleGroup group = new ToggleGroup();
        setHBox(layout1, group, user);
        Label lb = new Label("优乐影院管理系统");

        HBox layout2 = new HBox();
        layout2.setPadding(new Insets(50, 50, 50, 50));
        layout2.setSpacing(40);
        layout2.setAlignment(Pos.CENTER);
        lb.setId("bold-label");
        tf = new TextField();
        tf.setPromptText("请输入用户名");
        tf.setMinWidth(90);
        tf.setMaxWidth(400);
        tf.setMinHeight(40);
        pf = new PasswordField();
        pf.setPromptText("请输入密码");
        pf.setMaxWidth(400);
        pf.setMinWidth(90);
        tf.setMinHeight(40);
        Button bt = new Button("确认登录");
        Button bt1 = new Button("找回密码") ;
        Button bt2 = new Button("退出");
        layout1.getChildren().addAll(bt, bt1) ;
        //注册事件
        bt1.setOnMouseClicked(e->{
            findPassword(user) ;
        });
        bt.setOnMouseClicked(e -> {
            verifyLoginUI(user);

        });
        bt2.setOnMouseClicked(e->{
            over(windows) ;
        });
        bt2.setMinWidth(110);
        //设置单个组件的样式
        layout2.getChildren().addAll(bt,bt1,bt2) ;

        layout.getChildren().addAll(lb, tf, pf,layout1, layout2);
        sne = new Scene(layout, 700, 550) ;
        sne.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        return sne ;
    }
    public void over(Stage s) {
        s.close();
    }

    public boolean getUserInfo(Employee uu, TextField name, TextField pass, TextField pNum, TextField access) {
        if(name.getText().equals("")||
            pass.getText().equals("")
                ||pNum.getText().equals("")
                ||access.getText().equals("")){
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入完整信息");
            return false ;
        }
        else{
            uu.setName(name.getText());
            uu.setPassword(pass.getText());
            uu.setTel(pNum.getText());
            uu.setAccess(Integer.parseInt(access.getText()));
            LoginedUser lg = new LoginedUser() ;
            if(lg.addUser(uu)) {
                return true ;
            }
        }
        return false ;
    }
    public void  addUser(Employee use) {
        Stage win = new Stage() ;
        win.setTitle("添加用户");
        VBox vb= new VBox() ;
        vb.setSpacing(20);
        vb.setPadding(new Insets(10,10,10,10));
        TextField name = new TextField() ;
        name.setPromptText("请输入用户名");
        PasswordField pass = new PasswordField() ;
        pass.setPromptText("请输入密码");
        TextField pNum = new TextField() ;
        pNum.setPromptText("请输入电话号码");
        TextField access = new TextField() ;
        access.setPromptText("0.管理员 1.经理 2.售票员");

        HBox  hb = new HBox() ;
        hb.setPadding(new Insets(10,10,10,10));
        hb.setSpacing(20);
        Button sure = new Button("确认");

        sure.setOnMouseClicked(e->{
            boolean res= getUserInfo(use, name, pass, pNum, access) ;
            if(res) {
               win.close() ;
            }
        });

        Button cancer = new Button("取消");
        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        hb.getChildren().addAll(sure, cancer) ;
        vb.getChildren().addAll(name, pass, pNum, access, hb) ;
        Scene sne = new Scene(vb, 600,600) ;
        SetSceneStyle.sceneStyle(sne);
        win.setScene(sne);
        win.show();
    }

    public void setHBox(HBox hbox, ToggleGroup group, Employee user) {

        admin = new RadioButton("管理员");
        admin.setToggleGroup(group);
        admin.setUserData("管理员");
        admin.setStyle(" -fx-font-size:15;-fx-font-weight:bold;");

        manager = new RadioButton("经理");
        manager.setToggleGroup(group);
        manager.setUserData("经理");
        manager.setStyle(" -fx-font-size:15 ;-fx-font-weight:bold;");

        sales = new RadioButton("售票员");
        sales.setToggleGroup(group);
        sales.setUserData("售票员");
        sales.setStyle(" -fx-font-size:15 ;-fx-font-weight:bold;");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    String access = new String(group.getSelectedToggle().getUserData().toString());
                    if(access.equals("管理员")) {
                        user.setAccess(0);
                    }
                    if(access.equals("售票员")) {
                        user.setAccess(2);
                    }
                    if(access.equals("经理")) {
                        user.setAccess(1);
                    }
                }
                else{
                    user.setAccess(2);
                }
            }
        });
        hbox.getChildren().addAll(admin, manager, sales);
    }
    public void findPassword(Employee user) {
        FindPassScene fp = new FindPassScene() ;
        fp.findPassWord(user);
    }

    public void verifyLoginUI(Employee user) {

        user.setName(tf.getText());
        user.setPassword(pf.getText());
        //将接收到的信息传给dao层
        LoginedUser login = new LoginedUser();
        boolean sucess = login.verifyUser(user);
        if (sucess) {
            UsersUI ui = new UsersUI();
            if(user.getAccess() == 0){
                ui.getUserAdminSne(user, windows) ;
            }
            else if(user.getAccess() == 2){
                ui.getSaleSne(user, windows) ;
            }
            else {
                ui.getStudioManagerSne(user, windows) ;
            }
        }

        else {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("登陆错误");
        }
    }

}

