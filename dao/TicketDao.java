package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iTicketDao;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class TicketDao implements iTicketDao {

    public List<Ticket> select(String condt) {
        List<Ticket>ls = new LinkedList<>() ;
        try {
            String sql = "select * from ticket";
            condt.trim();
            if(!condt.isEmpty())
                sql+= " where " + condt;
            DBUtil db = new DBUtil();
            if(!db.openConnection()){
                System.out.print("fail to connect database");
                return null;
            }

            //填入查询语句
            ResultSet rst = db.execQuery(sql);
            //将数据库中的数据到处存到链表中
            if (rst!=null) {
                while(rst.next()){
                    //新创建演出厅对象
                    Ticket t = new Ticket() ;
                    t.setSched_id(rst.getInt("sched_id"));
                    t.setSeat_id(rst.getInt("seat_tmp_id"));
                    t.setTicket_price(rst.getInt("ticket_price"));
                    t.setTicket_sold(rst.getInt("ticket_status"));
                    t.setTicket_id(rst.getInt("ticket_id"));
                    t.setTicket_date(rst.getString("ticket_date"));
                    t.setSeat_id(rst.getInt("seat_tmp_id"));
                    t.setVersion(rst.getInt("version"));
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

    @Override
    public int add(Ticket tk) {
        try {
            //添加的时候设置ｖｅｒｓｉｏｎ字段为１
            System.out.println(tk.toString());
            DBUtil db = new DBUtil();
            String sql = "insert ticket (seat_tmp_id, sched_id," +
                    "ticket_price,"+"ticket_status," +
                    "ticket_locked_time," +
                    "ticket_date,"+"version)values("
                    +tk.getSeat_id()
                    + ", "
                    + tk.getSched_id()
                    + ", " + tk.getTicket_price()
                    + "," + tk.getTicket_sold()
                    +","+tk.getTicket_locked_time()+",'"+tk.getTicket_date()+"',"+"1)";
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

    //修改票相关信息
    public int modify(Ticket t) {
        //修改相关票的时候先将票从数据库中拿出来，在通过ｖｅｒｓｉｏｎ来枷锁并进行修改
        TicketSrv rs = new TicketSrv() ;
        Ticket tk = rs.select("ticket_id ="+t.getTicket_id()).get(0) ;
        //找不到票的话就修改失败
        if(tk == null) {
            return 0 ;
        }
        //获取票的版本号
        int version = tk.getVersion();
        int ret = 0;
        try{
            //要是修改过了，就不会修改成功
            String sql = "update ticket set ticket_price="+t.getTicket_price()+", ticket_status= "+t.getTicket_sold()+","
                    +"ticket_locked_time="+t.getTicket_locked_time()+","+"ticket_date='"+t.getTicket_date()+"', seat_tmp_id="+
                    t.getSeat_id()+",version="+(version+1)+"  where (ticket_id = "+t.getTicket_id() +" and version="+version+")";
            System.out.println(sql) ;
            DBUtil db = new DBUtil();
            db.openConnection();
            ret=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret ;
    }

    public int delete(int ID) {
        int rtn=0;
        try{
            String sql = "delete from  ticket ";
            sql += " where ticket_id = " + ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    //将所有票置为废票
    public int deleteAll(String condt) {
        int rtn=0;
        int ret = 0;
        try{
            String sql = "update ticket set ticket_status = 2 where "+condt ;
            System.out.println(sql) ;
            DBUtil db = new DBUtil();
            db.openConnection();
            ret=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret ;
    }

}
