package xupt.se.ttms.view.user;
import javafx.application.Application;

import javafx.stage.Stage;
import xupt.se.ttms.service.DeleteSchedule;

//未完成,修改演出计划状态s
public class LoginUI extends Application {

    Stage windows;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        windows = primaryStage ;
        windows.setX(200) ;
        windows.setY(100) ;
        windows.setTitle("欢迎使用优乐影院管理系统");
        DeleteSchedule ds = new DeleteSchedule() ;
        ds.setTask();
        //执行删除演出计划任务
        UserSceneUI usu = new UserSceneUI();
        usu.getLogInScene(windows);
    }
}