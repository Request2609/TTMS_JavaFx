package xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;
import xupt.se.ttms.idao.iStudioDAO;
import xupt.se.ttms.model.Studio;
import xupt.se.util.DBUtil;


public class StudioDAO implements iStudioDAO {
	@Override
	//插入演出厅信息
	public int insert(Studio stu) {
		try {
			String sql = "insert into studio(studio_name, studio_row_count, studio_col_count, studio_introduction )"
					+ " values('"
					+ stu.getName()
					+ "', "
					+ stu.getRowCount()
					+ ", " + stu.getColCount() 
					+ ", '" + stu.getIntroduction()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sql);
			if (rst!=null && rst.first()) {
				stu.setID(rst.getInt(1));
			}
			db.close(rst);
			db.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	//更新演出厅信息
	@Override
	public int update(Studio stu) {
		int rtn=0;
		try {
			String sql = "update studio set " + " studio_name ='"
					+ stu.getName() + "', " + " studio_row_count = "
					+ stu.getRowCount() + ", " + " studio_col_count = "
					+ stu.getColCount() + ", " + " studio_introduction = '"
					+ stu.getIntroduction() + "' ";

			sql += " where studio_id = " + stu.getID();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn =db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	//删除演出厅信息
	@Override
	public int delete(int ID) {
		int rtn=0;		
		try{
			String sql = "delete from  studio ";
			sql += " where studio_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn=db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;		
	}

	//将查询的结果一链表的形式返回给业务逻辑层
	@Override
	public List<Studio> select(String condt) {
		//演出厅链表
		List<Studio> stuList = null;
		stuList=new LinkedList<Studio>();
		try {
			String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction from studio ";
			condt.trim();
			if(!condt.isEmpty())
				sql+= " where " + condt;
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}

			//填入查询语句
			ResultSet rst = db.execQuery(sql);
			//将数据库中的数据到处存到链表中
			if (rst!=null) {
				while(rst.next()){
					//新创建演出厅对象
					Studio stu=new Studio();
					stu.setID(rst.getInt("studio_id"));
					stu.setName(rst.getString("studio_name"));
					stu.setRowCount(rst.getInt("studio_row_count"));
					stu.setColCount(rst.getInt("studio_col_count"));
					stu.setIntroduction(rst.getString("studio_introduction"));
					//加入到链表
					stuList.add(stu);
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
		return stuList;
	}
}
