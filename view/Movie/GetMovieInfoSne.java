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
import xupt.se.ttms.view.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.service.MovieSrv;
//import xupt.se.ttms.view.sellticket.ProcessTicketEvUI;
import xupt.se.ttms.view.Plan.ProcessSchedUI;
import xupt.se.ttms.view.sellticket.ProcessTicketEvUI;
import xupt.se.ttms.view.sellticket.SaleTicketScene;
import xupt.se.ttms.view.user.ConfirmBox;

import java.util.List;

public class GetMovieInfoSne {

    int width =1500;
    int height=1000 ;
    Button backs ;
    Stage win ;
    Employee users ;
    //退票
    //需要的信息
    //票的id号码,需要根据票的id获取票的信息，进而获取相应的演出计划id,进而获取时间，判断是否为过期的票
    //如果能退的话，修改座位和对应票的状态，修改用户售票额，修改电影的票房

    public void ProcessBackTEv(Stage win) {
        ProcessTicketEvUI pteu = new ProcessTicketEvUI() ;
        pteu.backTicket(users);
    }

    public Scene getMovieScene(Stage window ,Button quit,  Employee uu) {
        users = uu ;
        backs = quit ;
        win= window ;
        BorderPane bdp  = new BorderPane() ;
        GridPane gp = null;
        VBox vb = new VBox() ;
        gp = new GridPane() ;
        window.setTitle("售票管理");
        Label lb = new Label("当前售票员名称：" + uu.getName()+"  "+"ID:"+uu.getId());

        setMovieShow(gp);

        Button backt = new Button("退票") ;
        backt.setOnMouseClicked(e->{
            ProcessBackTEv(window) ;
        });

        backt.setAlignment(Pos.CENTER);
        backt.setMinSize(150, 50);
        Button plan = new Button("查询演出计划") ;
        //获取演出计划
        plan.setOnMouseClicked(e->{
                findSchedPlan() ;
        });

        plan.setMinSize(150,50);
        plan.setAlignment(Pos.CENTER);
        Button ticket = new Button("查询演出票") ;
        Button back =new Button("返回") ;
        ticket.setAlignment(Pos.CENTER);
        ticket.setMinSize(150,50);

        back.setOnMouseClicked(e->{
            Scene s = getMovieScene(window,backs, uu) ;
            SetSceneStyle.sceneStyle(s);
            window.setScene(s);
        });

        ticket.setOnMouseClicked(e->{
            ProcessTicketEvUI pte = new ProcessTicketEvUI() ;
            pte.getTicketUI(back, window, uu) ;
        });


        quit.setAlignment(Pos.CENTER);
        quit.setMinSize(150,50);

        HBox hb = new HBox() ;
        hb.getChildren().addAll(backt,plan, ticket,backs) ;
        hb.setSpacing(40);
        hb.setAlignment(Pos.CENTER);

        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(lb, gp) ;

        bdp.setTop(vb);
        bdp.setBottom(hb);
        bdp.setPadding(new Insets(20,20,20,20));

        Scene cne = new Scene(bdp, width, height);
        return cne ;
    }

    public void findSchedPlan() {
        ProcessSchedUI psu = new ProcessSchedUI() ;
        psu.querySched();
    }

    public void setMovieShow(GridPane gp){

        //获取所有影片信息
        MovieSrv ms = new MovieSrv() ;
        List<Movie> list = ms.fetchAllMovie() ;

        gp.setVgap(20);
        gp.setHgap(20);
        gp.setPadding(new Insets(20,20,20,20));
        int count = 1 ;
        int row = 1 ,col = 1 ;
        for(Movie mv:list) {
            count ++ ;
            if(col == 6) {
                col = 1 ;
                row+= 2 ;
            }
            Label label= new Label() ;
            if(mv.getPlay_status() == 2) {
                label = new Label("有票");
            }
            if(mv.getPlay_status() == 1) {
                label = new Label("即将上线");
            }
            if(mv.getPlay_status() == 0) {
                label = new Label("已经下架");
            }

            Button moreInfo = new Button("详情") ;
            moreInfo.setMaxSize(90, 90);
            Button sale = new Button("选座") ;
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
            Button back = new Button("退出");
            back.setOnMouseClicked(e->{
                Scene s = getMovieScene(win, backs, users) ;
                SetSceneStyle.sceneStyle(s);
                win.setScene(s);
                win.show() ;
            });

            sale.setOnMouseClicked(e->{
                SaleTicketScene sts = new SaleTicketScene() ;
                sts.saleTicket(win, back, mv, users) ;
            });
            gp.add(label,col, row);
            gp.add(imagev, col,row+1) ;
            gp.add(hb, col, row+2) ;
            col++ ;
            if(count == 10) {
                break ;
            }
        }
    }
}
