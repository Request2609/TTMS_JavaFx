package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iTicketDao;
import xupt.se.ttms.model.Ticket;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;

public class TicketDao implements iTicketDao {

    @Override
    public int add(Ticket tk) {
        try {
            System.out.println(tk.toString());
            DBUtil db = new DBUtil();
            String sql = "insert ticket (seat_tmp_id, sched_id," +
                    "ticket_price,"+"ticket_status," +
                    "ticket_locked_time," +
                    "ticket_date)values ("
                    +tk.getSeat_id()
                    + ", "
                    + tk.getSched_id()
                    + ", " + tk.getTicket_price()
                    + "," + tk.getTicket_sold()
                    +","+tk.getTicket_locked_time()+",'"+tk.getTicket_date()+"');";
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst!=null && rst.first()) {
                tk.setTicket_id(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
