package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iTypeDao;
import xupt.se.ttms.model.Type;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class TypeDao implements iTypeDao {


    public List<Type> select(String condt) {
        List<Type> list= null;
        list=new LinkedList<Type>();
        try {
            String sql = "select * from play_type";
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
                    Type type=new Type();
                    type.setId(rst.getInt("id"));
                    type.setType(rst.getString("type")) ;
                    list.add(type) ;
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
}
