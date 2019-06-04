package xupt.se.ttms.idao;

import xupt.se.ttms.model.Movie;

import java.util.List;

public interface iMovieDao {
    public List<Movie> select(String condt) ;
    public int delete(int id) ;
    public int modify(Movie mv) ;
}
