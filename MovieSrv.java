package xupt.se.ttms.service;

import xupt.se.ttms.dao.MovieDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Movie;

import java.util.List;

public class MovieSrv {

    public List<Movie> fetchAllMovie() {
        MovieDao movie = DAOFactory.createMovieDao() ;
        //获取所有影片信息
        List<Movie>list = movie.select("") ;
        return list ;
    }

}
