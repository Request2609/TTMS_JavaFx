package xupt.se.ttms.idao;

import xupt.se.ttms.model.Employee;

import java.util.List;

public interface iUserDao {
    //注册新用户用的
    public int insert(Employee Employee) ;
    //验证登录用的
    public Employee verify(Employee Employee) ;
    //删除用户
    public int delete(int id) ;
    //修改用户信息
    public int modify(Employee Employee);
    //查询用户信息
    public List<Employee> selectAll(String info) ;

}
