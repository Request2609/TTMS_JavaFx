package xupt.se.ttms.idao;
import xupt.se.ttms.dao.*;

public class DAOFactory {
	public static iStudioDAO creatStudioDAO(){
		return new StudioDAO();
	}

	public static UserDao creatUserDAO(){
		return new UserDao();
	}

	public static MovieDao createMovieDao() {
		return new MovieDao() ;
	}

//	public static SeatDao createSeatDao() {
//		return new SeatDao() ;
//	}
}
