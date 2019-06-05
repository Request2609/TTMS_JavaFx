package xupt.se.ttms.dao;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSeatDao;
import xupt.se.ttms.model.Seat;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class SeatDao implements iSeatDao {
    public List<Seat> select(String condt) {
        List<Seat>ls = new LinkedList<>() ;
        return ls ;
    }

    public int insert(Seat seat) {
        int ret = 0 ;
        try {
            DBUtil db = new DBUtil();
            String sql = "insert seat(studio_id, seat_row, seat_column,  tmp_seat_id, seat_status)values ("
                    + seat.getStudio_id()
                    +" , "
                    + seat.getSeat_row()
                    + "," +seat.getSeat_column()
                    + "," + seat.getTmp_seat_id()+","+seat.getSeat_status()+")";
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst!=null && rst.first()) {
                seat.setSeat_id(rst.getInt(1));
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
