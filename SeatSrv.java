package xupt.se.ttms.service;

import xupt.se.ttms.dao.SeatDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Seat;

import java.util.List;

public class SeatSrv {

    public int insert(List<Seat> lseat) {
        SeatDao sd = DAOFactory.createSeatDao() ;
        for(Seat s:lseat) {
            int ret = sd.insert(s) ;
            if(ret  == 0) {
                return  0 ;
            }
        }
        return 1 ;
    }

}
