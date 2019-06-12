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
    public Movie select(String condt) {
        MovieDao movie = DAOFactory.createMovieDao() ;
        //获取所有影片信息
        List<Movie>list = movie.select(condt) ;
        Movie mo  = null ;
        for(Movie m:list){
            mo = m ;
        }
        return mo ;
    }
    public List<Movie> selectAll(String condt) {
        MovieDao movie = DAOFactory.createMovieDao() ;
        //获取所有影片信息
        List<Movie>list = movie.select(condt) ;
        return list ;
    }

    public int delete(int id) {
        MovieDao md = new MovieDao() ;
        return md.delete(id) ;
    }

    public int modify(Movie mv) {
        MovieDao md = new MovieDao() ;
        return md.modify(mv) ;
    }
    public int add(Movie mv) {
        MovieDao md =DAOFactory.createMovieDao();
        return md.insert(mv) ;
    }
}
