package xupt.se.ttms.idao;

import xupt.se.ttms.model.Movie;
import xupt.se.ttms.model.Type;

import java.util.List;

public interface iTypeDao {
    public List<Type> select(String condt) ;
}
