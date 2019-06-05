package xupt.se.ttms.view.studio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.user.ConfirmBox;


public class ProcessStudioUI {

    TextField studio_intro ;
    TextField name ;
    TextField col_count ;
    TextField row_count ;
    int flag  ;

    public void addStudioUI() {
        Stage window = new Stage() ;
        flag  = 1 ;
        VBox vb = new VBox() ;
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        HBox hb = new HBox() ;
        hb.setSpacing(20);
        hb.setPadding(new Insets(20));

        Studio st = new Studio();
        st.setColCount(-1);
        st.setRowCount(-1) ;
        name = new TextField() ;
        name.setPromptText("请输入演出厅名称");
        row_count = new TextField() ;
        row_count.setPromptText("请输入演出厅行数");
        col_count = new TextField() ;
        col_count.setPromptText("请输入演出厅列数");
        studio_intro = new TextField() ;
        studio_intro.setPromptText("请输入演出厅介绍");
        Button ok = new Button("确认") ;
        ok.setOnMouseClicked(e->{
            boolean ret = getStudioInfo(st) ;
            if(ret) {
                window.close() ;
            }
        });
        Button cancer = new Button("取消") ;
        cancer.setOnMouseClicked(e->{
            window.close() ;
        });

        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(ok, cancer) ;
        vb.getChildren().addAll(name,col_count,row_count,studio_intro, hb) ;
        Scene sne = new Scene(vb, 600,600);
        SetSceneStyle.sceneStyle(sne);
        window.setScene(sne);
        window.show() ;
    }

    public boolean getStudioInfo(Studio st) {

            st.setName(name.getText());
            st.setColCount(Integer.parseInt(col_count.getText())) ;
            st.setRowCount(Integer.parseInt(row_count.getText())) ;
            st.setIntroduction(studio_intro.getText()) ;
            if(st.getColCount() == -1||st.getRowCount() == -1||
                    st.getName().equals("")||st.getIntroduction().equals(""))
            {
                ConfirmBox con = new ConfirmBox() ;
                con.displaySuccess("请输入完整信息!");
                return false ;
            }
            StudioSrv studio = new StudioSrv() ;
            if(flag == 1){
                int ret = studio.add(st);
                if(ret == 0) {
                    return false ;
                }
            }
            if(flag == 2){
                int ret =studio.modify(st) ;
                if(ret == 0) {
                    return false ;
                }
            }
            return true ;

    }

    public void modifyStudioUI(Studio s) {
        flag = 2 ;
        Stage window = new Stage() ;
        window.setTitle("修改演出厅信息");
        VBox vb = new VBox();
        vb.setPadding(new Insets(20));
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);

        name = new TextField() ;
        name.setPromptText("请输入新的名称");
        row_count = new TextField() ;
        row_count.setPromptText("请输入新的行数");
        col_count = new TextField() ;
        col_count.setPromptText("请输入新的列数");
        studio_intro= new TextField() ;
        studio_intro.setPromptText("请输入新介绍");

        Button ok = new Button("确认");
        Button cancer = new Button("取消");

        ok.setOnMouseClicked(e->{
            boolean ret = getStudioInfo(s) ;
            if(ret) {
                window.close() ;
            }
        });

        cancer.setOnMouseClicked(e->{
            window.close() ;
        });
        HBox hb = new HBox() ;
        hb.setSpacing(20);
        hb.setPadding(new Insets(20));
        hb.getChildren().addAll(ok, cancer) ;
        hb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(name, col_count,row_count,studio_intro,hb) ;
        Scene cne = new Scene(vb,400,400) ;
        window.setScene(cne);
        window.show();
    }

    public void deleteStudioUI(){

    }
}
