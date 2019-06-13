package xupt.se.ttms.view.SceneStyle;

import javafx.scene.Scene;
import sun.rmi.runtime.Log;
import xupt.se.ttms.view.user.LoginUI;
import xupt.se.ttms.view.user.UserSceneUI;

public class SetSceneStyle {
    public static void sceneStyle(Scene s) {
        s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        s.getStylesheets().add(UserSceneUI.class.getResource("Style.css").toExternalForm());
    }
}
