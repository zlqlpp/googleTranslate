package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bean.Main_translator;

/**
 * Dao
 * @author  
 *
 */
public class TransDao {
	/**
	 * 杩炴帴鏁版嵁搴�
	 * @return
	 */
	private Connection conn = null;
	
	public void getConn() {
		try {
		if(this.conn == null||conn.isClosed()){
			
				Class.forName("com.mysql.jdbc.Driver");//鎸囧畾杩炴帴绫诲瀷  
				conn = DriverManager.getConnection("jdbc:mysql://188.166.189.88/googleTranslator", "root", "qrkcgya520");//鑾峰彇杩炴帴
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 鍒楄〃
	 * @return
	 */
	public List<Main_translator> search(String word) {
		try {
			if(conn == null||conn.isClosed())
			{ 
				getConn(); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		List<Main_translator> list = new ArrayList<Main_translator>();
		String sql = "select t.id_translatorid,                                                             "
				+"t.word,                                                                               "
				+"t.Explanation,                                                                        "
				+"count(*) count,searchdate,                                                                             "
				+"max(f.date) newsearchdate                                                                          "
				+"from main_translator t LEFT JOIN main_flowling f  on t.id_translatorid=f.pid_translatorid  where 1=1  "
				+"                                                        ";
		if(null!=word&&!"".equals(word)){
			sql+= "and  t.word='"+word+"'";
		}
		sql+= " group by t.word,t.Explanation  order by t.word";
		try {
			//conn.setAutoCommit(false);
			ResultSet rs = conn.prepareStatement(sql ).executeQuery();
			Main_translator mt = null;
			while (rs.next()) {
				mt= new Main_translator();
				mt.setId_translatorid(new Integer(rs.getInt("id_translatorid")).toString());
				mt.setWord(rs.getString("word"));
				mt.setExplanation(rs.getString("Explanation"));
				mt.setCount(rs.getString("count")==null?"":rs.getString("count"));
				mt.setSearchdate(rs.getString("searchdate")==null?"":rs.getString("searchdate"));
				mt.setNewsearchdate(rs.getString("newsearchdate")==null?"":rs.getString("newsearchdate"));
				list.add(mt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} /*finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
		return list;
	}
	/**
	 * 鏂板
	 * @param name
	 */
	public void save(Main_translator mt) {
		try {
			if(conn == null||conn.isClosed())
			{ 
				getConn(); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO main_translator ( `word`, `Explanation`, `count`, `searchdate`) VALUES ( ?, ?, NULL, ?)");
			pstmt.setString(1, mt.getWord());
			pstmt.setString(2, mt.getExplanation());
			pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			pstmt.executeUpdate();
			//conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} /*finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	}
	public void saveToFlow(Main_translator mt) {
		try {
			if(conn == null||conn.isClosed())
			{ 
				getConn(); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO  main_flowling ( word, date, pid_translatorid) VALUES ( ?, ?, ?)");
			pstmt.setString(1, mt.getWord());
			pstmt.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			pstmt.setString(3, mt.getId_translatorid());
			pstmt.executeUpdate();
			//conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} /*finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	}


}
