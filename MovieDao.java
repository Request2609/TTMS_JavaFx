package xupt.se.ttms.dao;

import xupt.se.ttms.idao.iMovieDao;
import xupt.se.ttms.model.Movie;
import xupt.se.util.DBUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MovieDao implements iMovieDao {

    public List<Movie> select(String condt) {
        List<Movie> list  = new LinkedList<Movie>() ;
        try {
            String sql = "select play_id, play_name, play_introduction, play_ticket_price, play_status, path, play_length,play_type from play ";
            condt.trim();
            if(!condt.isEmpty())
                sql+= " where " + condt;
            DBUtil db = new DBUtil();
            if(!db.openConnection()){
                System.out.print("fail to connect database");
                return null;
            }
            System.out.println(sql) ;
            //填入查询语句
            ResultSet rst = db.execQuery(sql);
            //将数据库中的数据到处存到链表中
            if (rst!=null) {
                while(rst.next()){
                    //新创建演出厅对象
                    Movie play=new Movie();
                    play.setPlay_id(rst.getInt("play_id"));
                    play.setPath(rst.getString("path"));
                    play.setPlay_Length(rst.getInt("play_Length"));
                    play.setPlay_name(rst.getString("play_name"));
                    play.setPlay_introduction(rst.getString("play_introduction"));
                    play.setPlay_status(rst.getInt("play_status")) ;
                    play.setPlay_type(rst.getString("play_type"));
                    list.add(play) ;
                }
            }
            //关闭数据库对象
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
        }
        //返回链表
        return list;
    }

}
