package xupt.se.ttms.idao;

import xupt.se.ttms.model.Seat;

import java.util.List;

public interface iSeatDao {
    public List<Seat> select(String condt) ;
    public int insert(Seat seat) ;
}
