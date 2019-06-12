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
    public Seat select(String condt) {
        SeatDao sd =DAOFactory.createSeatDao() ;
        List<Seat> s =sd.select(condt);
        if(s.size() == 0) {
            return null ;
        }
        else {
            return s.get(0) ;
        }
    }
    public List<Seat> selectAll(String condt) {
        SeatDao sd =DAOFactory.createSeatDao() ;
        List<Seat> s =sd.select(condt);
        return s ;
    }

    public int modify(Seat seat) {
        SeatDao sd = DAOFactory.createSeatDao() ;
        return sd.modify(seat) ;
    }

    public int modifyAll(String condt) {
        SeatDao sd = new SeatDao() ;
        return sd.modifyAll(condt) ;
    }

}
