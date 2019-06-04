package xupt.se.ttms.idao;

import xupt.se.ttms.model.Schedule;

import java.util.List;

public interface iScheduleDao {
    public List<Schedule> select(String condt) ;
    public int delete(int id) ;
    public int modify(Schedule s) ;
    public int add(Schedule sd) ;
}
