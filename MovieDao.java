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
            String sql = "select play_type_id, play_id, play_name, play_introduction, play_ticket_price, play_status, path, play_length,play_type from play ";
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
                    play.setPlay_ticket_price(rst.getInt("play_ticket_price"));
                    play.setPlay_type_id(rst.getInt("play_type_id")) ;
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
    @Override
    public int delete(int id) {
        int rtn=0;
        try{
            String sql = "delete from  play";
            sql += " where play_id = " + id ;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
    @Override
    public int modify(Movie mv) {
        int rtn=0;
        try{
            String sql = "update play set play_name= '"+mv.getPlay_name()+"'"+
                    " , play_status ="+mv.getPlay_status()+", play_introduction= '"+mv.getPlay_introduction()+"',"
                    +"play_type='"+mv.getPlay_type()+"',"+"play_ticket_price ="+mv.getPlay_ticket_price()+", path='"+
                    mv.getPath()+"' "+"where play_id = "+mv.getPlay_id();
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
