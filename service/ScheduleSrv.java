package xupt.se.ttms.service;

import xupt.se.ttms.dao.ScheduleDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Schedule;

import java.util.LinkedList;
import java.util.List;

public class ScheduleSrv {

    public List<Schedule> select(String condt) {
        List<Schedule> list ;
        ScheduleDao sd =  DAOFactory.createScheduleDao() ;
        list = sd.select(condt) ;
        return list ;
    }

    public int delete(int id) {
        ScheduleDao sd = DAOFactory.createScheduleDao() ;
        return sd.delete(id) ;
    }

    public int modify(Schedule s){
        ScheduleDao sd =  DAOFactory.createScheduleDao() ;
        return 1 ;
    }

    public int add(Schedule s) {
        ScheduleDao sd = DAOFactory.createScheduleDao() ;
        return sd.add(s) ;
    }

}
