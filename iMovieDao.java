package xupt.se.ttms.idao;

import xupt.se.ttms.model.Movie;

import java.util.List;

public interface iMovieDao {
    public List<Movie> select(String condt) ;
}
