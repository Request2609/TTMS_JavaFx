package xupt.se.ttms.view.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;

import java.util.List;
import java.util.Set;

public class QueryUserScene {

    TextField name  = new TextField();
    TextField id = new TextField() ;
    HBox namehb , idhb, hbt;
    VBox vb ;
    Button ok;
    Button back ;
    Stage win ;
    Employee users ;
    public Scene getQueryUserSne(Stage window , Button bt, Employee user){
        users  = user ;
        win = window ;
        back = bt ;
        ok = new Button("确认");
        setHBox() ;
        setVBox();

        Label lb = new Label("请输入要查询的用户名：") ;
        Label lb2 = new Label("请输入想查的用户ID");
        name = new TextField() ;
        id = new TextField() ;
        namehb.getChildren().addAll(lb,name);
        idhb.getChildren().addAll(lb2, id) ;
        hbt.getChildren().addAll(ok, bt) ;
        ok.setOnMouseClicked(e->{
            getUserInfo() ;
        });

        vb.getChildren().addAll(namehb, idhb, hbt);
        Scene s = new Scene(vb, 800,600) ;
        SetSceneStyle.sceneStyle(s);
        return s ;
    }

    //获取用户id或者姓名查询用户信息
    public void getUserInfo() {

        LoginedUser lu = new LoginedUser() ;
        Employee emp = new Employee() ;
        List<Employee> empList = null;
        if(!name.getText().isEmpty()) {
            empList = lu.select("emp_name='"+name.getText()+"'") ;
        }
        if(!id.getText().isEmpty()){
            empList = lu.select("emp_id ="+Integer.parseInt(id.getText())) ;
        }
        if(empList.size() == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("用户不存在！");
            return ;
        }
        Employee user = empList.get(0) ;
        System.out.println("要修改的用户信息："+user.toString()) ;
        Scene s =getInfo(user) ;
        SetSceneStyle.sceneStyle(s);
        win.setScene(s);
        win.show() ;
    }

    public Scene getInfo(Employee user) {
        VBox vb = new VBox() ;
        vb.setPadding(new Insets(20));
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);
        Label title = new Label("要查询的用户信息");
        Label id = new Label("ID:"+user.getId()) ;
        Label name = new Label("用户名:"+user.getName()) ;
        Label tel = new Label("电话:"+user.getTel()) ;
        Label lb = new Label("用户密码:"+user.getPassword()) ;
        String acc ;
        if(user.getAccess() == 0) {
            acc = "管理员";
        }
        else if(user.getAccess() == 1) {
            acc = "剧目经理";
        }
        else if(user.getAccess() == 2) {
            acc = "售票员";
        }
        else {
            acc = "财务经理";
        }
//        System.out.println(users.toString()) ;
        Button modi = new Button("修改");
        Button del = new Button("删除");
        HBox hb = new HBox() ;
        modi.setOnMouseClicked(e->{
            modify(user) ;
        });
        del.setOnMouseClicked(e->{
            delete(user) ;
        });

        hb.setPadding(new Insets(20));
        hb.setSpacing(20);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(modi, del, back) ;

        vb.getChildren().addAll(title,id, name, tel, lb, new Text(acc), hb);
        Scene s = new Scene(vb, 800, 600);
        return s ;
    }
    public void setVBox(){
        vb = new VBox() ;
        vb.setSpacing(20);
        vb.setPadding(new Insets(20));
        vb.setAlignment(Pos.CENTER);
    }

    //调用之前的修改信息即可
    public void modify(Employee user) {
        UsersUI uu = new UsersUI() ;
        uu.modifyUserInfo(win, user, users,back) ;
    }
    //删除用户，然后回复原来的用户界面
    public void delete(Employee user) {
        LoginedUser lu = new LoginedUser() ;
        lu.delete(user.getId()) ;
        UsersUI uu =  new UsersUI() ;

        Scene s = uu.updateScene(users, back, win);
        SetSceneStyle.sceneStyle(s);
        win.setScene(s);
        win.show() ;
    }

    public void setHBox() {
        namehb = new HBox() ;
        idhb= new HBox() ;
        namehb.setSpacing(20);
        namehb.setPadding(new Insets(20));
        namehb.setAlignment(Pos.CENTER);
        idhb.setPadding(new Insets(20));
        idhb.setSpacing(20);
        idhb.setAlignment(Pos.CENTER);
        hbt = new HBox() ;
        hbt.setSpacing(20);
        hbt.setPadding(new Insets(20));
        hbt.setAlignment(Pos.CENTER);
    }
}
