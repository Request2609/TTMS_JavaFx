package xupt.se.ttms.view.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;

public class ModifyUserInfo {

    TextField emp_name, emp_tel_num,emp_password, access;

    public Scene getModifyScene(Employee users, Employee user, Button back) {

        setText() ;
        UserSceneUI usu = new UserSceneUI() ;
        Label title =new Label("当前管理员:"+users.getName()+"  ID:"+users.getId()) ;
        HBox hb= new HBox() ;
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        //设置单选框
        usu.setHBox(hb,user);
        HBox bthb =new HBox() ;
        bthb.setAlignment(Pos.CENTER);
        bthb.setPadding(new Insets(20));
        bthb.setSpacing(20);
        Button sure= new Button("确认") ;

        sure.setOnMouseClicked(e->{
            modifyInfo(user) ;
        });

        bthb.getChildren().addAll(sure, back) ;
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setSpacing(20);
        vb.getChildren().addAll(title, emp_name, emp_tel_num, emp_password,hb, bthb) ;
        Scene s = new Scene(vb,800, 600) ;
        SetSceneStyle.sceneStyle(s);
        return s ;
    }

    public void modifyInfo(Employee user) {
        if(!emp_name.getText().isEmpty()) {
            user.setName(emp_name.getText());
        }
        if(!emp_tel_num.getText().isEmpty()){
            user.setTel(emp_tel_num.getText());
        }
        if(!emp_password.getText().isEmpty()){
            user.setPassword(emp_password.getText());
        }
        System.out.println("修改用户信息："+user.toString());
        LoginedUser log = new LoginedUser() ;
        boolean ret =log.modify(user) ;
        ConfirmBox con = new ConfirmBox() ;
        if(ret == false) {
            con.displaySuccess("修改失败！");
            return  ;
        }
        con = new ConfirmBox() ;
        con.displaySuccess("修改成功！");
    }

    //设置ｔｅｘｔ
    public void setText(){
        emp_name = new TextField() ;
        emp_tel_num = new TextField() ;
        emp_password = new TextField() ;
        access = new TextField() ;
        emp_name.setPromptText("请输入新的用户名");
        emp_tel_num.setPromptText("请输入新的电话号码");
        emp_password.setPromptText("请输入新的用户密码");
    }
}
