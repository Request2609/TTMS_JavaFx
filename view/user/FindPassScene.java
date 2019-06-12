package xupt.se.ttms.view.user;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;


class AllScene {

}

public class FindPassScene extends Application {
    Scene sne ;
    Stage window ;
    TextField name, pnum;
    PasswordField newPassword,passAgain ;
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void findPassWord(Employee user) {

        window = new Stage();
        window.setTitle("找回密码") ;

        VBox vb = new VBox() ;
        vb.setSpacing(40);
        vb.setPadding(new Insets(50,50,50,50));
        vb.setAlignment(Pos.CENTER);

        name = new TextField() ;
        name.setPromptText("请输入账号");
        name.setMinWidth(90);

        newPassword = new PasswordField() ;
        newPassword.setMinWidth(90); ;
        newPassword.setPromptText("请输入新密码");

        pnum = new TextField() ;
        pnum.setPromptText("请输入电话号码") ;
        pnum.setMinWidth(90);
        Button confirm = new Button("确认") ;
        Button cancer = new Button("取消") ;

        //验证用户输入
        confirm.setOnMouseClicked(e->{
            boolean ret = modifyPass(user,name, newPassword, pnum) ;
            if(ret == true) {
                window.close() ;
            }
        });

        cancer.setOnMouseClicked(e->{
            window.close() ;
        });
        HBox hb = new HBox() ;
        hb.setSpacing(20);
        hb.getChildren().addAll(confirm, cancer) ;
        vb.getChildren().addAll(name, newPassword, pnum, hb) ;
        sne = new Scene(vb, 600, 400) ;
        SetSceneStyle.sceneStyle(sne);
        window.setScene(sne);
        window.show();
    }

    //修改密码操作
    public boolean modifyPass(Employee user, TextField name, TextField pass, TextField pnum){
              if(name.getText().equals("")||pass.getText().equals("")
                      ||pnum.getText().equals(""))  {
                  ConfirmBox con = new ConfirmBox() ;
                  con.displaySuccess("请输入完整信息！");
                  return false;
              }

              user.setName(name.getText()) ;
              user.setTel(pnum.getText());
              user.setPassword(pass.getText());
              LoginedUser lu = new LoginedUser() ;
              boolean ret = lu.findPass(user) ;
              if(ret) {
                  return true ;
              }
              ConfirmBox con = new ConfirmBox() ;
              con.displaySuccess("请检查输入用户是否存在");
              return false ;
    }
}
