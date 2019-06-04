package xupt.se.ttms.view.Movie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.model.Type;
import xupt.se.ttms.service.MovieSrv;
import xupt.se.ttms.view.user.ConfirmBox;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ProcessMvUI {

    TextField play_name, play_ticket_price, play_status, path, play_length;
    VBox vb ;
    HBox hb, htypelist ;
    Map<Type, CheckBox> cbmap = new HashMap<>() ;
    List<Type> list = GetMovieType.movieType() ;
    Movie movie ;
    CheckBox[] cb ;
    public void modifyMv(Movie mv) {

        movie = mv ;
        Stage win = new Stage() ;
        win.setTitle("修改剧目信息") ;
        setTextFeild() ;
        setBox() ;

        for(Type t:list) {

            CheckBox cb = new CheckBox(t.getType()) ;
            cbmap.put(t, cb) ;
            htypelist.getChildren().add(cb) ;
        }

        HBox h = new HBox() ;

        cb= new CheckBox[3] ;
        CheckBox cb1 = new CheckBox("有票") ;
        CheckBox cb2 = new CheckBox("即将上映") ;
        CheckBox cb3 = new CheckBox("已经下架");
        cb[0] = cb1 ;
        cb[1] = cb2 ;
        cb[2] = cb3 ;

        h.getChildren().addAll(cb1,cb2,cb3) ;

        Button ok = new Button("确认");
        Button cancer = new Button("取消");
        ok.setOnMouseClicked(e->{
            int ret = 0 ;
            ret = getInfo(mv) ;
            if(ret == 1) {
                win.close();
            }
        });

        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        hb.getChildren().addAll(ok,cancer) ;

        vb.getChildren().addAll(htypelist, play_name,h, play_ticket_price, play_length, path, hb) ;
        Scene sne = new Scene(vb, 800,800) ;
        win.setScene(sne);
        win.show() ;
    }

    public int getInfo(Movie mv) {
        if (play_name.getText().equals("")
                || play_length.getText().equals("")
                || play_status.getText().equals("")
                || play_ticket_price.getText().equals("")
        ||path.getText().equals(""))
        {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入完整信息");
            return 0 ;
        }
        String type  = "";
        for(Map.Entry<Type, CheckBox>entry:cbmap.entrySet()) {
            Type t = entry.getKey() ;
            CheckBox c = entry.getValue() ;
            if(c.isSelected()) {
               type +=c.getText()+" ";
            }
        }

        mv.setPlay_type(type);
        mv.setPlay_ticket_price(Integer.parseInt(play_ticket_price.getText()));
        mv.setPlay_name(play_name.getText());
        mv.setPlay_Length(Integer.parseInt(play_length.getText()));
        mv.setPath(path.getText());

        if(cb[0].isSelected()) {
             mv.setPlay_status(1);
        }

        if(cb[1].isSelected()) {
            mv.setPlay_status(2);
        }
        if(cb[2].isSelected()) {
            mv.setPlay_status(0);
        }
        MovieSrv ms  =  new MovieSrv();
        int ret = ms.modify(mv) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("修改信息失败,请重试!");
            return  0 ;
        }
        return 1 ;
     }

    public void setBox() {
        htypelist = new HBox() ;
        vb = new VBox() ;
        hb = new HBox() ;
        vb.setAlignment(Pos.CENTER);
        hb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        hb.setSpacing(10);
        vb.setPadding(new Insets(20));
        htypelist.setSpacing(10);
        htypelist.setAlignment(Pos.CENTER);

    }

    public void setTextFeild() {

        play_name = new TextField() ;
        play_name.setPromptText("请输入要修改的影片名称");
        play_status =  new TextField() ;

        play_ticket_price = new TextField();
        play_ticket_price.setPromptText("请输入票价");
        path = new TextField();
        path.setPromptText("请输入宣传海报url");
        play_length = new TextField() ;
        play_length.setPromptText("请输入电影时长");
    }
}
