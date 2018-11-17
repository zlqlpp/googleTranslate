package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bean.Main_translator;

import javax.annotation.Resource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BaseDaoImpl implements BaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
     
    	//jdbcTemplate.update("INSERT INTO test2 (n1, n2) VALUES (0, 'xx')");
    	//jdbcTemplate.update("update test2 set n2='bb's where n1='0'");
        //jdbcTemplate.queryForList("select * from user where user_name='"+userName+"' and passwd='"+passWd+"'") ;
      
    @Override
    @Transactional
    public List<Main_translator> search(String word) {
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
			List<Map<String, Object>> listRet = jdbcTemplate.queryForList(sql) ;
			Main_translator mt = null;
			Map<String, Object> mapRet= null;
			for(int i=0;i<listRet.size();i++) {
				mapRet = listRet.get(i);
				mt= new Main_translator();
				mt.setId_translatorid(mapRet.get("id_translatorid").toString());
				mt.setWord(mapRet.get("word").toString());
				mt.setExplanation(mapRet.get("Explanation").toString());
				mt.setCount(mapRet.get("count")==null?"":mapRet.get("count").toString());
				mt.setSearchdate(mapRet.get("searchdate")==null?"":mapRet.get("searchdate").toString());
				mt.setNewsearchdate(mapRet.get("newsearchdate")==null?"":mapRet.get("newsearchdate").toString());
				list.add(mt);
			}
		return list;
	}
    @Override
    @Transactional
	public void save(Main_translator mt) {
    	String sql = "INSERT INTO main_translator ( `word`, `Explanation`, `count`, `searchdate`) VALUES ( '"+mt.getWord()+"', '"+mt.getExplanation()+"', NULL, '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"')";
    	jdbcTemplate.update("INSERT INTO test2 (n1, n2) VALUES (0, 'xx')");
	}
    
    @Override
    @Transactional
	public void saveToFlow(Main_translator mt) {
    	jdbcTemplate.update("INSERT INTO  main_flowling ( word, date, pid_translatorid) VALUES ( '"+mt.getWord()+"','"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"','"+mt.getId_translatorid()+"')");
		   
	}
}
