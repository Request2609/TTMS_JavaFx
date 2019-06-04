package xupt.se.ttms.model;

import java.sql.Date;

public class Schedule {
    int sche_id ;
    int studio_id ;
    int play_id ;
    Date sched_time;
    int sched_ticket_price ;

    public int getSche_id() {
        return sche_id;
    }

    public void setSche_id(int sche_id) {
        this.sche_id = sche_id;
    }

    public int getStudio_id() {
        return studio_id;
    }

    public void setStudio_id(int studio_id) {
        this.studio_id = studio_id;
    }

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public Date getSched_time() {
        return sched_time;
    }

    public void setSched_time(Date sched_time) {
        this.sched_time = sched_time;
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "sche_id=" + sche_id +
                ", studio_id=" + studio_id +
                ", play_id=" + play_id +
                ", sched_time=" + sched_time +
                ", sched_ticket_price=" + sched_ticket_price +
                '}';
    }

    public int getSched_ticket_price() {
        return sched_ticket_price;
    }

    public void setSched_ticket_price(int sched_ticket_price) {
        this.sched_ticket_price = sched_ticket_price;
    }
}
