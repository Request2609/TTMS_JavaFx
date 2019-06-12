package xupt.se.ttms.idao;

import xupt.se.ttms.model.Ticket;

import java.util.List;

public interface iTicketDao {
    public int add(Ticket tk) ;
    public List<Ticket> select(String condt);
    public int modify(Ticket t) ;
    public int delete(int ID) ;
    public int deleteAll(String condt) ;
}
