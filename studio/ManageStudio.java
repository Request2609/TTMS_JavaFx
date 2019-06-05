package xupt.se.ttms.view.studio;

import javafx.scene.Scene;
import javafx.stage.Stage;

import xupt.se.ttms.model.Employee;
import xupt.se.ttms.view.Movie.GetMovieInfoSne;
import xupt.se.ttms.view.user.LoginUI;

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
