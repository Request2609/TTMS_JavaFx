package xupt.se.ttms.view.user;

import javafx.application.Application;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.service.LoginedUser;

public class VerifyInfoUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void verifyInfo(Employee user){
        LoginedUser log = new LoginedUser();
        log.findPass(user) ;
    }

    public static void findPass(){

    }
}

