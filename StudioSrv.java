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
	
	public List<Studio> Fetch(String condt){
		return stuDAO.select(condt);		
	}
	
	public List<Studio> FetchAll(){
		List<Studio>ls = new LinkedList<>();
		ls = stuDAO.select("") ;
		return ls ;
	}
}
