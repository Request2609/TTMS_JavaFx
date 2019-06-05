package xupt.se.ttms.view.Movie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.SceneStyle.SetSceneStyle;
import xupt.se.ttms.model.Images;
import xupt.se.ttms.model.Movie;
import xupt.se.ttms.model.Type;
import xupt.se.ttms.service.MovieSrv;
import xupt.se.ttms.view.user.ConfirmBox;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class ProcessMvUI {

    TextField intro ;
    TextField play_name, play_ticket_price, play_status, path, play_length;
    VBox vb ;
    HBox hb, htypelist ;
    Map<Type, CheckBox> cbmap = new HashMap<>() ;
    List<Type> list = GetMovieType.movieType() ;
    Movie movie ;
    CheckBox[] cb_status ;
    Stage win ;
    Button ok, cancer ;
    public void modifyMv(Movie mv) {

        movie = mv ;
        Stage win = new Stage() ;
        win.setTitle("修改剧目信息") ;
        setTextFeild() ;
        setBox() ;
        setButton() ;
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
        setCbMap() ;

        HBox h = new HBox() ;
        cb_status= new CheckBox[3] ;
        CheckBox cb1 = new CheckBox("有票") ;
        CheckBox cb2 = new CheckBox("即将上映") ;
        CheckBox cb3 = new CheckBox("已经下架");
        cb_status[0] = cb1 ;
        cb_status[1] = cb2 ;
        cb_status[2] = cb3 ;

        h.getChildren().addAll(cb1,cb2,cb3) ;


        hb.getChildren().addAll(ok,cancer) ;

        vb.getChildren().addAll(htypelist, play_name,h, play_ticket_price, play_length, path, hb) ;
        Scene sne = new Scene(vb, 800,800) ;
        win.setScene(sne);
        win.show() ;
    }
    public void setCbMap() {
        for(Type t:list) {
            CheckBox cb = new CheckBox(t.getType()) ;
            cbmap.put(t, cb) ;
            htypelist.getChildren().add(cb) ;
        }
    }

    public void setButton(){
        Button ok = new Button("确认");
        Button cancer = new Button("取消");
    }
    public boolean isEmpty() {
        if (play_name.getText().equals("")
                || play_length.getText().equals("")
                || play_ticket_price.getText().equals("")
                ||path.getText().equals(""))
        {
            return true;
        }
        return false  ;
    }

    public String getMovieType() {
        String type = new String();
        for(Map.Entry<Type, CheckBox>entry:cbmap.entrySet()) {
            Type t = entry.getKey() ;
            CheckBox c = entry.getValue() ;
            if(c.isSelected()) {
                type +=c.getText()+" ";
            }
        }
        return type ;
    }
    public void setMovie(Movie mv) {
        String type  = getMovieType() ;
        mv.setPlay_type(type);
        mv.setPlay_ticket_price(Integer.parseInt(play_ticket_price.getText()));
        mv.setPlay_name(play_name.getText());
        mv.setPlay_Length(Integer.parseInt(play_length.getText()));
        mv.setPath(path.getText());
    }

    public int getInfo(Movie mv) {
        if(isEmpty()) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("请输入完整信息");
            return 0 ;
        }
        setMovie(mv);
        if(cb_status[0].isSelected()) {
             mv.setPlay_status(1);
        }

        if(cb_status[1].isSelected()) {
            mv.setPlay_status(2);
        }
        if(cb_status[2].isSelected()) {
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
    public void query(){
        win = new Stage() ;
        Scene s = queryMovie() ;
        win.setScene(s);
        win.show();
    }

    public Scene queryMovie() {
        win.setTitle("查询影片信息");
        VBox vb = new VBox() ;
        vb.setSpacing(20);
        vb.setPadding(new Insets(10));

        HBox hb = new HBox() ;
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(20);
        hb.setPadding(new Insets(10));

        TextField ID = new TextField() ;
        ID.setPromptText("请输入影片ID");
        TextField name = new TextField() ;
        name.setPromptText("影片名称");
        Button ok= new Button("确认") ;
        Button cancer = new Button("取消") ;
        hb.getChildren().addAll(ok, cancer) ;

        ok.setOnMouseClicked(e->{
               getMovie(name, ID) ;
        });
        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        vb.getChildren().addAll(name, ID, hb) ;
        Scene s = new Scene(vb, 400,400) ;
        SetSceneStyle.sceneStyle(s);
        return s ;
    }

    public int getMovie(TextField name, TextField ID) {


        if(name.getText().equals("")&&ID.getText().equals("")) {
            ConfirmBox con  = new ConfirmBox() ;
            con.displaySuccess("请输入至少一项信息");
            return 0 ;
        }
        Movie m = null;
        if(!name.getText().isEmpty()){
            MovieSrv md = new MovieSrv() ;
            m = md.select("play_name='"+name.getText()+"'") ;
        }
        else{
            int id = Integer.parseInt(ID.getText()) ;
            MovieSrv md = new MovieSrv() ;
            m= md.select(" play_id = "+id) ;
        }
        if(m == null) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("影片不存在!");
            return 0 ;
        }
        //展示影片
        Scene s= showMovie(m) ;
        SetSceneStyle.sceneStyle(s);
        win.setScene(s);
        return  1;
    }

    public Scene showMovie(Movie m) {
        Button ok = new Button("确认") ;
        VBox vb = new VBox() ;
        vb.setPadding(new Insets(20));
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        ok.setOnMouseClicked(e->{
            win.close() ;
        });

        Images im  = new Images() ;
        im.setImage(m.getPath());
        ImageView iv = new ImageView() ;
        iv.setImage(im.getImage());
        vb.setStyle("-fx-background-color: #987;");
        vb.getChildren().addAll(iv,
                new Text("票价:"+m.getPlay_ticket_price()),
                new Text("名称"+m.getPlay_name()),
                new Text("电影时长"+m.getPlay_Length()),
                new Text("电影类型:"+m.getPlay_type()),
                new Text("电影介绍:"+m.getPlay_introduction()),ok
        ) ;

        Scene s = new Scene(vb,600,600) ;
        return s ;
    }

    public void addMovie() {
        win = new Stage() ;
        Button ok = new Button("确认");
        Button cancer = new Button("取消");
        setBox() ;
        //设置htypelist和cbmap
        setCbMap();
        setTextFeild();
        intro = new TextField() ;
        intro.setPromptText("请输入影片介绍");

        ok.setOnMouseClicked(e->{
            int ret = 0 ;
            ret = addInfo() ;
            if(ret == 1) {
                win.close() ;
            }
        });
        cancer.setOnMouseClicked(e->{
            win.close() ;
        });
        hb.getChildren().addAll(ok, cancer) ;
        htypelist.setStyle("-fx-background-color: #987;");
        vb.getChildren().addAll(htypelist, play_name, play_ticket_price, play_length, path,intro, hb) ;
        Scene s = new Scene(vb,800,800) ;
        win.setScene(s);
        SetSceneStyle.sceneStyle(s);
        win.show() ;
    }

    public int addInfo() {
        Movie mv = new Movie() ;

        if(isEmpty()) {
            ConfirmBox con = new ConfirmBox();
            con.displaySuccess("请输入完整信息");
            return 0 ;
        }
        setMovie(mv);
        mv.setPlay_introduction(intro.getText());
        mv.setPlay_status(2);
        MovieSrv ms = new MovieSrv() ;
        int ret =ms.add(mv) ;
        if(ret == 0) {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("添加失败!");
            return 0 ;
        }
        return 1 ;
    }
}
