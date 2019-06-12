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
	public static ScheduleDao createScheduleDao(){return new ScheduleDao() ;}
	public static TypeDao createTypeDao() {return new TypeDao();} ;
	public static SeatDao createSeatDao() { return new SeatDao() ;}
	public static  TicketDao createTicketDao(){return new TicketDao() ;}

}
