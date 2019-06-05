package xupt.se.ttms.view.user;
import javafx.application.Application;

import javafx.stage.Stage;

//未完成,修改演出计划状态
public class LoginUI extends Application {

    Stage windows;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        windows = primaryStage ;
        windows.setTitle("欢迎使用优乐影院管理系统");
        UserSceneUI usu = new UserSceneUI() ;
        usu.getLogInScene(windows);
    }
}