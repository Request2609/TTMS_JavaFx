package xupt.se.ttms.view.Movie;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import xupt.se.ttms.model.Type;
import xupt.se.ttms.service.TypeSrv;

import java.util.LinkedList;
import java.util.List;

public class GetMovieType {

    public static  List<Type> movieType() {

        List<Type> list ;
        TypeSrv ts = new TypeSrv() ;
        list = ts.selectAll("") ;
        System.out.println("总共有剧目的种类:"+list.size()) ;
        return list ;
    }
}
