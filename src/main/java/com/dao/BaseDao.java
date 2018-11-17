package com.dao;


import java.util.List;
import java.util.Map;

import com.bean.Main_translator;

public interface BaseDao {

	List<Main_translator> search(String word);

	void save(Main_translator mt);

	void saveToFlow(Main_translator mt);

	 


}
