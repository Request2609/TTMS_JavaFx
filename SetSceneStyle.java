package xupt.se.ttms.SceneStyle;

import javafx.scene.Scene;
import xupt.se.ttms.view.user.LoginUI;

public class SetSceneStyle {
    public static void sceneStyle(Scene s) {
        s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
    }
}
