package xupt.se.ttms.model;

public class Movie {

    int play_id ;
    //种类的id
    int play_type_id ;


    int play_ticket_price ;
    String play_type ;
    //时长（分钟）
    int play_Length ;

    //影片的名字
    String play_name;
    //影片介绍
    String play_introduction ;
    //影片路径
    String path ;

    int play_status ;


    public String getPlay_type() {
        return play_type;
    }

    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public int getPlay_ticket_price() {
        return play_ticket_price;
    }

    public void setPlay_ticket_price(int play_ticket_price) {
        this.play_ticket_price = play_ticket_price;
    }

    public void setPlay_status(int play_status) {
        this.play_status = play_status;
    }


//    public void setType(String typ

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public int getPlay_type_id() {
        return play_type_id;
    }

    public void setPlay_type_id(int play_type_id) {
        this.play_type_id = play_type_id;
    }

    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public String getPlay_introduction() {
        return play_introduction;
    }

    public void setPlay_introduction(String play_introduction) {
        this.play_introduction = play_introduction;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPlay_status() {
        return play_status;
    }

    public int getPlay_Length() {
        return play_Length;
    }

    public void setPlay_Length(int play_Length) {
        this.play_Length = play_Length;
    }
}
