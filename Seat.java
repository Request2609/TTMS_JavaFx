package xupt.se.ttms.model;

//座位信息
public class Seat {

    int seat_id ;
    int studio_id ;
    int seat_row ;
    int seat_column ;
    //0 没人  1已经订购 2.坏座位
    int seat_status ;
    int tmp_seat_id ;

    public int getTmp_seat_id() {
        return tmp_seat_id;
    }

    public void setTmp_seat_id(int tmp_seat_id) {
        this.tmp_seat_id = tmp_seat_id;
    }


    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(int studio_id) {
        this.studio_id = studio_id;
    }

    public int getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(int seat_row) {
        this.seat_row = seat_row;
    }

    public int getSeat_column() {
        return seat_column;
    }

    public void setSeat_column(int seat_column) {
        this.seat_column = seat_column;
    }

    public int getSeat_status() {
        return seat_status;
    }

    public void setSeat_status(int seat_status) {
        this.seat_status = seat_status;
    }

}
