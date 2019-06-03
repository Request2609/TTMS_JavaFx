package xupt.se.ttms.view.user;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;

import javax.xml.soap.Text;

//修改用户信息
public class ModifyUserUI extends Application {

    static Stage window ;
    static boolean ok = false;
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void display(Employee uu) {
        window  = new Stage() ;
        window.setTitle("修改用户信息");
        BorderPane bd = new BorderPane() ;

        VBox vb= new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        vb.setPadding(new Insets(20,20,20,20));

        TextField idTf = new TextField() ;
        idTf.setPromptText("请输入用户ID(必须已经为存在的ID)");
        TextField name = new TextField();
        name.setPromptText("请输入用户名");
        TextField atf = new TextField() ;
        atf.setPromptText("请输入新用户权限");
        PasswordField pwdtf = new PasswordField() ;
        pwdtf.setPromptText("请输入新密码");
        TextField teltf = new TextField() ;
        teltf.setPromptText("请输入电话号码");

        HBox hb = new HBox() ;
        hb.setSpacing(20);
        hb.setPadding(new Insets(20,20,20,20));
        Button bt1 = new Button("确认") ;
        Button bt2 = new Button("取消") ;
        hb.getChildren().addAll(bt1,bt2) ;

        bt1.setOnMouseClicked(e->{
            boolean ret = verifyData(uu ,idTf, name, pwdtf, teltf,atf) ;
            if(ret) {
                window.close();
            }
        });

        bt2.setOnMouseClicked(e->{
            window.close();
        });

        vb.getChildren().addAll(idTf, atf,name, pwdtf ,teltf, hb) ;
        Scene cne = new Scene(vb, 400, 400) ;
        window.setScene(cne);
        window.show();

    }

    public boolean verifyData(Employee uu, TextField idTf,TextField name, TextField pwdtf ,
                              TextField teltf, TextField atf){
        uu.setName(name.getText()) ;
        uu.setId(Integer.parseInt(idTf.getText()));
        uu.setTel(teltf.getText());
        uu.setPassword(pwdtf.getText());
        System.out.println(uu.toString()) ;
        if(uu.getId() == 9||uu.getName().equals("")||
                uu.getPassword().equals("")||
                uu.getTel().equals("")||atf.getText().equals("")){
            ConfirmBox con = new ConfirmBox();
            con.displaySuccess("用户信息含有空项");
            return false;
        }
        else {
            switch (atf.getText()) {
                case "管理员":
                    uu.setAccess(0);
                    break ;
                case "售票员":
                    uu.setAccess(2);
                    break ;
                case "经理":
                    uu.setAccess(1);
                    break ;
                default:
                    uu.setAccess(2);
                    break ;
            }
            LoginedUser user = new LoginedUser() ;
            boolean ret = user.modify(uu) ;
            if(!ret) {
                ConfirmBox con = new ConfirmBox() ;
                con.displaySuccess("用户不存在！");
                return false ;
            }
            return true;
        }
    }
}
