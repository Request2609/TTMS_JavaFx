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
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;

import java.util.List;


public class UserSceneUI extends Application {

    boolean close = false ;

    public RadioButton admin;
    public RadioButton manager;
    public RadioButton sales;
    public RadioButton finance_manager;
    Stage windows;
    TextField tf;
    PasswordField pf;
    Employee user ;
    int access ;
    public final ToggleGroup group = new ToggleGroup();
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

        setHBox(layout1, user);
        Label lb = new Label("优乐影院管理系统");
        lb.setId("welcome-text");

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
        sne = new Scene(layout, 800, 600) ;
        SetSceneStyle.sceneStyle(sne);
        return sne ;
    }
    public void over(Stage s) {
        s.close();
    }

    public boolean getUserInfo(Employee uu, TextField name, TextField pass, TextField pNum) {
        if(name.getText().equals("")||
            pass.getText().equals("")
                ||pNum.getText().equals("")
                ){
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入完整信息");
            return false ;
        }

        else{
            uu.setName(name.getText());
            uu.setPassword(pass.getText());
            uu.setTel(pNum.getText());
            uu.setAccess(access);
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
        final ComboBox<Object>com = new ComboBox<>() ;
        com.getItems().addAll(
                "管理员",
                "剧目经理",
                "财务经理",
                "售票员"
        );

        com.setValue("管理员");
        com.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(newValue.toString());
                String ll = newValue.toString() ;
                if(ll.equals("剧目经理")){
                    access = 1 ;
                }
                else if(ll.equals("管理员")){
                    access = 0 ;
                }
                else if(ll.equals("财务经理")){
                    access = 6 ;
                }
                else if(ll.equals("售票员")){
                    access = 2 ;
                }
                else {
                    com.setValue("销售员");
                    access = 2 ;
                }
            }
        }) ;

        HBox  hb = new HBox() ;
        hb.setPadding(new Insets(10,10,10,10));
        hb.setSpacing(20);
        Button sure = new Button("确认");

        sure.setOnMouseClicked(e->{
            boolean res= getUserInfo(use, name, pass, pNum) ;
            if(res) {
               win.close() ;
            }
        });

        Button cancer = new Button("取消");
        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        hb.getChildren().addAll(sure, cancer) ;
        vb.getChildren().addAll(name, pass, pNum, com, hb) ;
        Scene sne = new Scene(vb, 600,400) ;
        SetSceneStyle.sceneStyle(sne);
        win.setScene(sne);
        win.show();
    }

    public void setHBox(HBox hbox,  Employee user) {

        finance_manager = new RadioButton("财务经理");
        finance_manager.setStyle(" -fx-font-size:20;-fx-font-weight:bold;");
        finance_manager.setToggleGroup(group);
        finance_manager.setUserData("财务经理");

        admin = new RadioButton("管理员");
        admin.setToggleGroup(group);
        admin.setUserData("管理员");
        admin.setStyle(" -fx-font-size:20;-fx-font-weight:bold;");

        manager = new RadioButton("剧目经理");
        manager.setToggleGroup(group);
        manager.setUserData("剧目经理");
        manager.setStyle(" -fx-font-size:20 ;-fx-font-weight:bold;");

        sales = new RadioButton("售票员");
        sales.setToggleGroup(group);
        sales.setUserData("售票员");
        sales.setStyle(" -fx-font-size:20 ;-fx-font-weight:bold;");

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
                    if(access.equals("剧目经理")) {
                        user.setAccess(1);
                    }
                    if(access.equals("财务经理")){
                        user.setAccess(6);
                    }
                }
                else{
                    user.setAccess(2);
                }
            }
        });
        hbox.getChildren().addAll(admin, manager, sales, finance_manager);
    }

    public void findPassword(Employee user) {
        FindPassScene fp = new FindPassScene() ;
        fp.findPassWord(user);
    }

    public void verifyLoginUI(Employee user) {

        user.setName(tf.getText());
        user.setPassword(pf.getText());
//        user.setAccess();
        //将接收到的信息传给dao层
        LoginedUser login = new LoginedUser();

        boolean sucess = login.verifyUser(user);
        System.out.println("用户信息登录信息："+sucess+"   权限："+user.getAccess()) ;
        Button back = new Button("退出");
        back.setOnMouseClicked(e->{
            Scene s = getLogInScene() ;
            SetSceneStyle.sceneStyle(s);
            windows.setScene(s);
            windows.show() ;
        });

        if (sucess) {

            LoginedUser lu = new LoginedUser();
            List<Employee> list = lu.select("emp_name='"+user.getName()+"'") ;
            user = list.get(0) ;
            UsersUI ui = new UsersUI();
            if(user.getAccess() == 0){
                ui.getUserAdminSne(user,back, windows) ;
            }
            else if(user.getAccess() == 2){

                ui.getSaleSne(user,back , windows) ;
            }
            else if(user.getAccess() == 1){
                ui.getStudioManagerSne(user,back , windows) ;
            }
            //否则就是财务经理
            else {
                ui.getFinaceManagerSne(user, back, windows) ;
            }
        }
        else {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("登陆错误");
        }
    }

}

