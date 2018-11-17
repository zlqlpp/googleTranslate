package com.contraller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.Main_translator;
import com.bean.Result4Page;
import com.dao.BaseDao;
import com.service.GoogleTranslatorService;
import com.util.Language;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class TransController {

	@Autowired
	BaseDao bd;
	
	private GoogleTranslatorService gts = new GoogleTranslatorService();
    
	@RequestMapping("/main")
	public String main(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		model.addAttribute("err", "帐号错误，请联系VX:salinahk");
		return "index.jsp";
	}
	
	@RequestMapping("/detail")
	public String detail(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		List<Main_translator> list = bd.search("");
        return "index.jsp";
	}
	
	@RequestMapping("/trans")
	public String trans(ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String word = request.getParameter("word").trim();
    	if(null!=word&&!"".equals(word)){
    		
            
            List<Main_translator> list = bd.search(word);
            Result4Page r = new Result4Page();
            if(list.size()==0){
            	 String explain = gts.translate(word, Language.ENGLISH,Language.CHINESE );
            	 Main_translator mt = new Main_translator();
            	 mt.setWord(word);
            	 mt.setExplanation(explain);
            	 
            	 r.setGoogleExplain(mt);
            	 
            	 bd.save(mt);
            	 list = bd.search(word);
            	 bd.saveToFlow(list.get(0));
            }else{
            	
            	r.setLastSearchExplain(list.get(0));
            	bd.saveToFlow(list.get(0));
            }
            
            
            request.setAttribute("r", r);
            request.setAttribute("word", word);
    	}
    	return "index.jsp";
	}


}
