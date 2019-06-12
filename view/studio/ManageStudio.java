package xupt.se.ttms.view.studio;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.view.Movie.GetMovieInfoSne;

//import javax.swing.text.html.ImageView;

public class ManageStudio {
    Employee user ;
    Stage window ;

    //呈现演出厅消费界面
    public ManageStudio(Employee uu, Button quit, Stage win) {
        user = uu;
        window = win;
        GetMovieInfoSne get = new GetMovieInfoSne() ;
        Scene sce = get.getMovieScene(win,quit, user) ;
        win.setScene(sce);
        SetSceneStyle.sceneStyle(sce);
        win.show();
    }


}
