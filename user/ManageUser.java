//package xupt.se.ttms.view.user;
//
//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ProgressIndicator;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//import xupt.se.ttms.model.Employee;
//
//public class ManageUser extends Application {
//
//    Stage window = null;
//    Employee user ;
//    public static void main(String args[]) {
//         launch(args);
//    }
//
//    public ManageUser(Employee user) {
//        this.user = user ;
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Group group = new Group() ;
//        window = primaryStage ;
//        window.setTitle("管理员");
//        HBox layout = new HBox();
//        layout.setAlignment(Pos.CENTER);
//        Button bt1 = new Button("登陆成功！");
//        layout.getChildren().add(bt1) ;
//
//        Scene sne = new Scene(group) ;
//        window.setScene(sne);
//        Group g = new Group() ;
//        ProgressIndicator p1 = new ProgressIndicator();
//        g.getChildren().add(p1) ;
//        sne.setRoot(g);
//        window.show() ;
//    }
//}
