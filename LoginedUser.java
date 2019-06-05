package xupt.se.ttms.service;
import java.util.List;
import xupt.se.ttms.dao.UserDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Employee;


public class LoginedUser extends Employee {

	public boolean addUser(Employee uu) {
		UserDao udao = new UserDao() ;
		int i = udao.insert(uu) ;
		if(i == 0) return false ;
		return true ;
	}

	public boolean verifyUser(Employee user){
		UserDao udao = new UserDao() ;
		Employee tmp = udao.verify(user) ;
		System.out.println(user.toString()) ;

		if(tmp == null) {
			return false ;
		}
		if(tmp.getName().equals(user.getName())&&
				tmp.getName().equals(user.getName())&&
				tmp.getAccess() ==user.getAccess()
				&&user.getPassword().equals(tmp.getPassword())) {
			return true ;
		}

		System.out.println("数据不正确");
		System.out.println(tmp.toString()) ;
		return false ;
	}

	public static List<Employee> getAllUser() {
	    List<Employee>list = null ;
	    UserDao dao = DAOFactory.creatUserDAO() ;
	    list = dao.selectAll("") ;
	    return list ;
    }

    public boolean modify(Employee user){

	    UserDao uu = DAOFactory.creatUserDAO() ;
	    System.out.println(uu.toString());

        int ret = uu.modify(user);

        if(ret == 0){
            return false ;
        }
        return true ;
    }

    public int delete(int ID){
	    DAOFactory handle = new DAOFactory() ;
	    UserDao dao = handle.creatUserDAO() ;
	    return dao.delete(ID) ;
	}

	public boolean findPass(Employee uu) {

		UserDao uDao = DAOFactory.creatUserDAO() ;
		Employee user ;
		user= uDao.verify(uu);

		System.out.println("当前用户的信息:"+user.toString()) ;
		System.out.println("之前用户的信息:"+uu.toString()) ;

		if(user == null) {
			return false ;
		}

		if(user.getName().equals(uu.getName())
				&&user.getTel().equals(uu.getTel())){
			user.setPassword(uu.getPassword());
			int ret = uDao.modify(user);
			if(ret == 0) {
				return false ;
			}
			return true ;
		}
		return false ;
	}
}
