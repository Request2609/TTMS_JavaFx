package xupt.se.ttms.view.Movie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.service.MovieSrv;

import java.util.LinkedList;
import java.util.List;

public class MovieManageUI {

    Stage win ;
    Employee userm ;
    Button backm  ;
    //剧目管理
    public Scene getMovieSne(Employee user, Button back, Stage userWindow){
        win = userWindow ;
        userm = user ;
        backm = back ;

        BorderPane bord = new BorderPane();
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setSpacing(20);

        Label title = new Label("剧目管理+"+"  当前经理:"+user.getName()+"  ID:"+user.getId()) ;
        title.setStyle("-fx-background-color: #987; -fx-font-size: 15");
        GridPane g = getMovieList() ;
        vb.getChildren().addAll(title, g) ;
        bord.setTop(vb) ;
        HBox hb = new HBox() ;
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(20);
        hb.setPadding(new Insets(20)) ;
        Button add = new Button("添加剧目") ;
        Button fresh = new Button("刷新页面") ;
        Button query = new Button("查询剧目") ;
        hb.getChildren().addAll(back, add,fresh,query);
        bord.setBottom(hb);
        Scene s = new Scene(bord, 1200,1000) ;
        return s ;
    }

    //获取电影列表
    public GridPane getMovieList() {
        GridPane g = new GridPane() ;
        g.setVgap(10);
        g.setHgap(80);
        List<Movie> list = new LinkedList<>() ;
        MovieSrv ms = new MovieSrv() ;
        list =ms.fetchAllMovie() ;
        int col = 1 ;
        int row  = 1 ;
        for(Movie m: list) {
            VBox vb = new VBox() ;
            setVBox(m, vb) ;
            g.add(vb,col,row) ;
            col++ ;
            if(col==5) {
                row++ ;
                col = 1 ;
            }
        }
        g.setStyle("-fx-background-color: #EBF5E4;") ;
        return g ;
    }

    public void setVBox(Movie m, VBox vb) {
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);
        Images im = new Images() ;
        im.setImage(m.getPath());
        HBox hb = new HBox() ;
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        Button delete = new Button("删除") ;
        Button modify = new Button("修改") ;
        hb.getChildren().addAll(delete, modify) ;
        //删除
        delete.setOnMouseClicked(e->{
            deleteMv(m) ;
        });

        modify.setOnMouseClicked(e->{
            modifyMv(m) ;
        });

        ImageView iv = new ImageView() ;
        iv.setImage(im.getImage());
        vb.getChildren().addAll(iv, new Text("电影名称:"+m.getPlay_name()),
                new Text("电影类型:"+m.getPlay_type()),
                new Text("影片时长:"+m.getPlay_Length()), new Text("票价:"+m.getPlay_ticket_price()));
        if(m.getPlay_status() == 0) {
               vb.getChildren().add(new Text("剧目已经下架")) ;
        }

        if(m.getPlay_status() == 1) {
            vb.getChildren().add(new Text("剧目正在上映")) ;
        }

        if(m.getPlay_status() == 2) {
            vb.getChildren().add(new Text("剧目即将上映")) ;
        }

        vb.getChildren().add(hb) ;
    }
    //删除电影
    public void deleteMv(Movie mv) {
        MovieSrv ms = new MovieSrv() ;
        ms.delete(mv.getPlay_id()) ;
        Scene s = getMovieSne(userm, backm, win) ;
        SetSceneStyle.sceneStyle(s);
        win.setScene(s);
    }
    //修改电影信息
    public void modifyMv(Movie mv) {
        ProcessMvUI pm = new ProcessMvUI() ;
        pm.modifyMv(mv);
    }
}
