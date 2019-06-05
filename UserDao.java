
package xupt.se.ttms.dao;


import xupt.se.ttms.idao.iUserDao;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Studio;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class UserDao implements iUserDao {

    @Override
    public int insert(Employee user) {
        try {
            System.out.println(user.toString());
            DBUtil db = new DBUtil();
            String sql = "insert employee (emp_name, password, emp_tel_num, access)values ('"
                    + user.getName()
                    + "', "
                    + user.getPassword()
                    + ", " + user.getTel()
                    + ",'" + user.getAccess()
                    + "' )";
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst!=null && rst.first()) {
                user.setId(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override

    public Employee verify(Employee user) {
        Employee uu = null;
        try {
            String sql = "select * from employee where emp_name ="+"'"+user.getName()+"'";
            DBUtil db = new DBUtil();
            db.openConnection();
            //获取结果集
            ResultSet rst = db.execQuery(sql);
            if(rst == null) {
                db.close(rst);
                db.close();
                return null;
            }
            else{
                while(rst.next()){
                rst.toString() ;
                //获取数据库中的数据并返回
                uu = new Employee() ;
                uu.setName(rst.getString("emp_name"));
                uu.setPassword(rst.getString("password"));
                uu.setTel(rst.getString("emp_tel_num"));
                uu.setId(rst.getInt("emp_id")) ;
                uu.setAccess(rst.getInt("access"));
                uu.setSaleMoney(rst.getInt("emp_sale_money"));

                db.close(rst);
                db.close();
                return uu ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uu;
    }
    @Override
    public int delete(int id) {
        int rtn=0;
        try{
            String sql = "delete from  employee ";
            sql += " where emp_id = " + id ;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    //
    @Override
    public int modify(Employee user) {
        int rtn=0;
        try{
            String sql = "update employee set emp_name= '"+user.getName()+"'"+
                    " , emp_tel_num='"+user.getName()+"'"+", password= '"+user.getPassword()+"'"
                    +"where emp_id ="+user.getId();
            System.out.println(sql) ;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }


    @Override
    public List<Employee> selectAll(String condt) {
        List<Employee> stuList = null;
        stuList=new LinkedList<Employee>();
        try {
            String sql = "select * from employee";
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
                    Employee user=new Employee();
                    user.setId(rst.getInt("emp_id"));
                    user.setName(rst.getString("emp_name"));
                    user.setTel(rst.getString("emp_tel_num"));
                    user.setSaleMoney(rst.getInt("emp_sale_money"));
                    user.setAccess(rst.getInt("access"));
                    //加入到链表
                    stuList.add(user);
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
        return stuList;
    }
}
