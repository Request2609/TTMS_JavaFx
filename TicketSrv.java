package xupt.se.ttms.service;

import xupt.se.ttms.dao.TicketDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Ticket;

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
}
