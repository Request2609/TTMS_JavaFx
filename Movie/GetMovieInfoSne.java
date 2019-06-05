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
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.service.MovieSrv;
//import xupt.se.ttms.view.sellticket.ProcessTicketEvUI;
import xupt.se.ttms.view.user.ConfirmBox;
import xupt.se.ttms.view.user.UserSceneUI;

import java.util.List;

public class GetMovieInfoSne {

    int width =1000;
    int height=1000 ;

    //退票
    public void ProcessBackTEv(Stage win) {

    }

    public Scene getMovieScene(Stage window , Employee uu) {
        BorderPane bdp  = new BorderPane() ;
        GridPane gp = null;
        VBox vb = new VBox() ;
        gp = new GridPane() ;
        window.setTitle("售票管理");
        Label lb = new Label("当前售票员名称：" + uu.getName()+"  "+"ID:"+uu.getId());
        lb.setStyle("-fx-background-color: #986; -fx-font-size: 20");
        setMovieShow(gp);

        Button backt = new Button("退票") ;
        backt.setOnMouseClicked(e->{
            ProcessBackTEv(window) ;
        });

        backt.setAlignment(Pos.CENTER);
        backt.setMinSize(150, 50);
        Button plan = new Button("查询演出计划") ;
        plan.setMinSize(150,50);
        plan.setAlignment(Pos.CENTER);
        Button ticket = new Button("查询演出票") ;
        ticket.setAlignment(Pos.CENTER);
        ticket.setMinSize(150,50);
        Button quit = new Button("退出") ;
        quit.setAlignment(Pos.CENTER);
        quit.setMinSize(150,50);

        quit.setOnMouseClicked(e->{
            window.close() ;
        });

        HBox hb = new HBox() ;
        hb.getChildren().addAll(backt,plan, ticket,quit) ;
        hb.setSpacing(40);
        hb.setAlignment(Pos.CENTER);

        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(lb, gp) ;

        bdp.setTop(vb);
//        bdp.setCenter(gp);
        bdp.setBottom(hb);
        bdp.setPadding(new Insets(20,20,20,20));

        Scene cne = new Scene(bdp, width, height);
        return cne ;
    }

    public void setMovieShow(GridPane gp){


        //获取所有影片信息
        MovieSrv ms = new MovieSrv() ;
        List<Movie> list = ms.fetchAllMovie() ;

        gp.setVgap(20);
        gp.setHgap(20);
        gp.setPadding(new Insets(20,20,20,20));

        int count = 1 ;
        int i = 1 ,j = 1 ;
        for(Movie mv:list) {

            if(i%5 == 0) {
                j+= 2 ;
            }
            Button moreInfo = new Button("详情") ;
            moreInfo.setMaxSize(90, 90);
            Button sale = new Button("售票") ;
            sale.setMaxSize(90,90);
            HBox hb = new HBox() ;
            hb.setSpacing(10);
            hb.getChildren().addAll(moreInfo, sale) ;

            Images im = new Images() ;
            im.setImage(mv.getPath());
            ImageView imagev = new ImageView() ;
            imagev.setImage(im.getImage());
            imagev.setFitWidth(150);
            imagev.setFitHeight(200);

            imagev.setOnMouseClicked(e->{
                ConfirmBox con = new ConfirmBox() ;
                con.getInfoOfMovie(mv) ;
            });

            //弹出一窗口，显示影片的详细信息即可
            moreInfo.setOnMouseClicked(e->{
                ConfirmBox con = new ConfirmBox() ;
                con.getInfoOfMovie(mv) ;
            });
            //弹出售票窗口
            sale.setOnMouseClicked(e->{

            });
            gp.add(imagev, i,j) ;
            gp.add(hb, i, j+1) ;
            i++ ;
        }
    }
}
