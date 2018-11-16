package com.contraller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Main_translator;
import com.bean.Result4Page;
import com.dao.TransDao;
import com.service.GoogleTranslatorService;
import com.util.Language;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class StaffServlet
 */
public class TransServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GoogleTranslatorService gts = new GoogleTranslatorService();
    private TransDao staffDao = new TransDao();
    
    public TransServlet() {
        
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        doPost(request, response);
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	String word = request.getParameter("word").trim();
        	if(null!=word&&!"".equals(word)){
        		
                
                List<Main_translator> list = staffDao.search(word);
                Result4Page r = new Result4Page();
                if(list.size()==0){
                	 String explain = gts.translate(word, Language.ENGLISH,Language.CHINESE );
                	 Main_translator mt = new Main_translator();
                	 mt.setWord(word);
                	 mt.setExplanation(explain);
                	 
                	 r.setGoogleExplain(mt);
                	 
                	 staffDao.save(mt);
                	 list = staffDao.search(word);
                	 staffDao.saveToFlow(list.get(0));
                }else{
                	
                	r.setLastSearchExplain(list.get(0));
                	staffDao.saveToFlow(list.get(0));
                }
                
                
                request.setAttribute("r", r);
                request.setAttribute("word", word);
        	}
        	request.setCharacterEncoding("UTF-8");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
    }

}
