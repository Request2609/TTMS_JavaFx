package xupt.se.ttms.model;

import java.sql.Date;

public class Ticket {
	
	private int ticket_id;
	private int movie_id;
	private int seat_id;
	private String ticket_date;
	private int ticket_locked_time;
	private int ticket_sold;
	int ticket_price ;
	int sched_id ;

	public int getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(int ticket_price) {
		this.ticket_price = ticket_price;
	}

	public int getSched_id() {
		return sched_id;
	}

	public void setSched_id(int sched_id) {
		this.sched_id = sched_id;
	}

	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public int getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(int seat_id) {
		this.seat_id = seat_id;
	}
	public String getTicket_date() {
		return ticket_date;
	}
	public void  setTicket_date(String ticket_date) {
		this.ticket_date = ticket_date;
	}
	public int getTicket_locked_time() {
		return ticket_locked_time;
	}
	public void setTicket_locked_times(int ticket_locked_time) {
		this.ticket_locked_time = ticket_locked_time;
	}
	public int getTicket_sold() {
		return ticket_sold;
	}
	public void setTicket_sold(int ticket_sold) {
		this.ticket_sold = ticket_sold;
	}
	
}
