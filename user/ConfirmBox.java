package xupt.se.ttms.view.user;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.service.LoginedUser;


public class ConfirmBox extends Application {

    boolean result ;
    Stage window ;

    public boolean display(String title) {

        window = new Stage() ;
        VBox layout = new VBox() ;

        layout.setSpacing(50);
        layout.setAlignment(Pos.CENTER);
        window.setTitle(title);

        Button yes_bt1 = new Button("重新登录") ;
        Button no_bt1 = new Button("找回密码") ;

        yes_bt1.setOnAction(e->{
            window.close() ;
        });

        no_bt1.setOnAction(e->{
            getScene() ;
        });
        layout.getChildren().add(yes_bt1) ;
        layout.getChildren().add(no_bt1) ;
        Scene sne = new Scene(layout,500,500) ;
        window.setScene(sne);
        window.show();
        return false ;
    }

    public void findPassScene(Stage window) {

    }

    public void getScene() {

        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(15);
        vb.setPadding(new Insets(30,30,30,30));
        HBox hb = new HBox() ;
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(15);
        hb.setPadding(new Insets(20,20,20,20));

        TextField name = new TextField() ;
        name.setPromptText("请输入用户名");
        name.setMinWidth(90);

        TextField tel_num= new TextField();
        tel_num.setPromptText("请输入电话号码");
        tel_num.setMinWidth(90);

        PasswordField pass = new PasswordField();
        pass.setPromptText("请输入新密码");
        pass.setMinWidth(90);

        Button  ok= new Button("确认");
        Button  cancer= new Button("取消");

        hb.getChildren().addAll(ok,cancer);
        vb.getChildren().addAll(name,tel_num, pass, hb) ;
        //验证输入的信息
        ok.setOnMouseClicked(e->{
            Employee user = new Employee() ;
            VerifyInfoUI.verifyInfo(user) ;
        });

        cancer.setOnMouseClicked(e->{
            window.close();
        });
        Scene sne1 = new Scene(vb, 400,500);
        window.setScene(sne1);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void displaySuccess(String title) {
        window = new Stage() ;

        VBox layout = new VBox() ;
        layout.setSpacing(50);
        layout.setAlignment(Pos.CENTER);
        window.setTitle(title);
        Label lb = new Label(title) ;
        lb.setStyle("-fx-font-size: 40");
        Button yes_bt1 = new Button("确定") ;

        yes_bt1.setOnAction(e->{
            window.close() ;
        });
        layout.getChildren().addAll(lb, yes_bt1) ;
        Scene sne = new Scene(layout,500,500) ;
        window.setScene(sne);
        window.show();
    }

    public void getInfoOfMovie(Movie mv) {

        Images im = new Images() ;
        im.setImage(mv.getPath());
        ImageView imagev = new ImageView() ;
        imagev.setImage(im.getImage());
        imagev.setFitHeight(100);
        imagev.setFitWidth(100);

        window = new Stage() ;
        Label play_name = new Label("影片名称："+mv.getPlay_name()) ;
        Label play_type = new Label("影片类型："+mv.getPlay_type()) ;
        Label play_length = new Label("影片时长："+mv.getPlay_name()) ;
        Label play_status = new Label();
        switch(mv.getPlay_status()) {
            case 0 :
                play_status.setText("即将上映");
                break ;
            case 1 :
                play_status.setText("正在上映");
                break;
            case 2:
                play_status.setText("已经下架");
                break ;
        }

        Label play_introduct = new Label("影片介绍："+mv.getPlay_introduction()) ;
        VBox vb= new VBox() ;
        vb.setPadding(new Insets(20,20,20,20));
        vb.setSpacing(20);
        vb.getChildren().addAll(imagev, play_name, play_type,play_length,play_status,play_introduct) ;
        Scene sce = new Scene(vb, 400,400) ;
        window.setScene(sce);
        window.show() ;
    }
}
