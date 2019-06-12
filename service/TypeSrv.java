package xupt.se.ttms.service;

import xupt.se.ttms.dao.TypeDao;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.model.Type;

import java.util.List;

public class TypeSrv {
    public List<Type>selectAll(String condt) {

        TypeDao type = new TypeDao() ;
        return type.select(condt) ;
    }
}
