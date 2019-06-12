package xupt.se.ttms.service;

import java.util.LinkedList;
import java.util.List;

import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iStudioDAO;
import xupt.se.ttms.model.Studio;

public class StudioSrv {
	private iStudioDAO stuDAO=DAOFactory.creatStudioDAO();
	
	public int add(Studio stu){

		return stuDAO.insert(stu); 		
	}
	
	public int modify(Studio stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	
	public Studio Fetch(String condt){

		List<Studio>list  = stuDAO.select(condt);
		Studio s  = new Studio() ;
		for(Studio ss: list) {
			s = ss ;
		}
		return s ;
	}
	
	public List<Studio> FetchAll(){
		List<Studio>ls ;
		ls = stuDAO.select("") ;
		return ls ;
	}
}
