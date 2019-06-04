package xupt.se.ttms.dao;
import xupt.se.ttms.idao.iScheduleDao;
import xupt.se.ttms.model.Schedule;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class ScheduleDao implements iScheduleDao {
    //将查询的结果一链表的形式返回给业务逻辑层
    @Override
    public List<Schedule> select(String condt) {
        //演出厅链表
        List<Schedule> list = null;
        list=new LinkedList<Schedule>();
        try {
            String sql = "select sched_id, studio_id, play_id, sched_time, sched_ticket_price from schedule ";
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
                    Schedule sched=new Schedule();
                    sched.setSche_id(rst.getInt("sched_id"));
                    sched.setStudio_id(rst.getInt("studio_id"));
                    sched.setPlay_id(rst.getInt("play_id"));
                    sched.setSched_time(rst.getDate("sched_time"));
                    sched.setSched_ticket_price(rst.getInt("sched_ticket_price"));
                    //加入到链表
                    list.add(sched);
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
        //返回链表
        return list;
    }

    public int delete(int ID) {
        int rtn=0;
        try{
            String sql = "delete from  schedule ";
            sql += " where sched_id = " + ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    public int modify(Schedule s) {
        //更新演出厅信息
            int rtn=0;
            try {
                String sql = "update schedule set " + " studio_id ='"
                        + s.getStudio_id()+ "', " + " play_id= "
                        + s.getPlay_id() + ", " + " sched_time= "
                        + s.getSched_time() + ", " + " sched_ticket_price = '"
                        + s.getSched_ticket_price() + "' ";

                sql += " where studio_id = " + s.getSche_id();
                DBUtil db = new DBUtil();
                db.openConnection();
                rtn =db.execCommand(sql);
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rtn;
    }

    public int add(Schedule sd) {
        int ret = 0 ;
        try {
            DBUtil db = new DBUtil();
            String sql = "insert schedule (studio_id, play_id, sched_time, sched_ticket_price)values ('"
                    + sd.getStudio_id()
                    + "', "
                    + sd.getPlay_id()
                    + ", " + sd.getSched_time()
                    + ",'" + sd.getSched_ticket_price()
                    + "' )";
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst!=null && rst.first()) {
                sd.setSche_id(rst.getInt(1));
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
