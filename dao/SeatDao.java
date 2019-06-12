package xupt.se.ttms.dao;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSeatDao;
import xupt.se.ttms.model.Seat;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class SeatDao implements iSeatDao {

    public int modify(Seat s) {
        int ret = 0 ;
        try{
            String sql = "update seat set seat_status= "+s.getSeat_status()
                    +", seat_column ="+s.getSeat_column()+", seat_row= "+s.getSeat_row()+","
                    +"tmp_seat_id="+s.getTmp_seat_id()+","+"sched_id="+s.getSched_id()+"  where seat_id = "+s.getSeat_id();
            DBUtil db = new DBUtil();
            db.openConnection();
            System.out.println(sql) ;
            ret=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret ;
    }

    public int modifyAll(String condt) {
        int ret = 0 ;
        try{
            String sql = "update seat seat_status where " ;
            sql+= "  "+condt ;
            DBUtil db = new DBUtil();
            db.openConnection();
            System.out.println(sql) ;
            ret=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret ;
    }

    public List<Seat> select(String condt) {
        List<Seat>ls = new LinkedList<>() ;
        try {
            String sql = "select * from seat";
            condt.trim();
            if(!condt.isEmpty())
                sql+= " where " + condt;
            DBUtil db = new DBUtil();
            if(!db.openConnection()){
                System.out.print("fail to connect database");
                return null;
            }
            System.out.println("查询座位信息："+sql) ;
            //填入查询语句
            ResultSet rst = db.execQuery(sql);
            //将数据库中的数据到处存到链表中
            if (rst!=null) {
                while(rst.next()){
                    //新创建演出厅对象
                    Seat t = new Seat() ;
                    t.setSched_id(rst.getInt("sched_id") ) ;
                    t.setTmp_seat_id(rst.getInt("tmp_seat_id"));
                    t.setSeat_column(rst.getInt("seat_column"));
                    t.setSeat_row(rst.getInt("seat_row"));
                    t.setSeat_status(rst.getInt("seat_status"));
                    t.setSeat_id(rst.getInt("seat_id"));
                    t.setSched_id(rst.getInt("sched_id")) ;
                    t.setStudio_id(rst.getInt("studio_id")) ;
                    ls.add(t) ;
                }
            }
            //关闭数据库对象
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{

        }
        return ls ;
    }

    public int insert(Seat seat) {

        try {
            DBUtil db = new DBUtil();
            String sql = "insert seat(studio_id, sched_id,seat_row, seat_column,  tmp_seat_id, seat_status)values ("
                    + seat.getStudio_id()
                    +" , "+seat.getSched_id()+","
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
