package xupt.se.ttms.view.studio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import sun.plugin2.message.GetAppletMessage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.service.MovieSrv;
import xupt.se.ttms.view.user.ConfirmBox;
import xupt.se.ttms.view.user.LoginUI;

import java.util.List;

//import javax.swing.text.html.ImageView;

public class ManageStudio {
    Employee user ;
    Stage window ;

    //呈现演出厅消费界面
    public ManageStudio(Employee uu, Stage win) {
        user = uu;
        window = win;
        GetMovieInfoSne get = new GetMovieInfoSne() ;
        Scene sce = get.getMovieScene(win, user) ;
        win.setScene(sce);
        sce.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        win.show();
    }


}
