package xupt.se.ttms.view.Plan;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.view.user.LoginUI;
import xupt.se.ttms.view.user.UserSceneUI;
import xupt.se.ttms.view.user.UsersUI;

public class PlanArangeUI {
    Scene sne ;
    int width,height ;
    Label lb  ;
    public void getPlanSne(Stage window, Employee user) {
        HBox hb = new HBox() ;

        lb=new Label("当前经理:"+user.getName()+"  ID:"+user.getId()) ;
        lb.setStyle("-fx-background-color: #987;");

        Button last = new Button("返回");

        last.setOnMouseClicked(e->{
            UsersUI uu =  new UsersUI() ;
            Scene cne = uu.getStudioSne(user) ;
            window.setScene(cne);
            cne.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
            window.show();
        });

        hb.getChildren().addAll(last) ;
        Scene sne = new Scene(hb, 1200,1000) ;
        window.setScene(sne);
        window.show();
//        Scene sne = new Scene() ;
    }
}
