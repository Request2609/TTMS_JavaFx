package xupt.se.ttms.service;

import xupt.se.ttms.dao.TicketDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Ticket;

import java.util.Date;
import java.util.List;

public class TicketSrv {
    public int add(List<Ticket> lt) {
        TicketDao td = DAOFactory.createTicketDao() ;
        for(Ticket tk : lt) {
            int ret  = td.add(tk) ;
            if(ret == 0){
                return 0 ;
            }
        }
        return 1 ;
    }

    public List<Ticket>select(String condt) {
        TicketDao td= DAOFactory.createTicketDao() ;
        return td.select(condt) ;
    }
    public int modify(Ticket t) {
        TicketDao td = DAOFactory.createTicketDao() ;
        return td.modify(t) ;
    }

    public int delete(int id) {
        TicketDao td =DAOFactory.createTicketDao() ;
        return td.delete(id) ;
    }

    //将票置为废票
    public int modifyTicket(String condt) {
        TicketDao td = DAOFactory.createTicketDao() ;
        return td.deleteAll(condt)  ;
    }
}
