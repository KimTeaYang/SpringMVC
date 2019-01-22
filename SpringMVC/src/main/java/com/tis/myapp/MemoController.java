package com.tis.myapp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tis.persistence.MemoDAO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/memo")
@Log4j
public class MemoController {
	
	@Autowired
	private MemoDAO memoDAO;
	//@Autowired : MemoDao클래스 유형의 빈이 있으면 주입한다.

	//GET방식일 때 호출됨
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String memoForm() {
		//로그 레벨에 따라 메소드 호출 debug/info/warn/error
		log.info("memoForm()����");
		return "memo/input"; // /WEB-INF/views/memo/input.jsp
	}
	
	@RequestMapping(value="/input",method=RequestMethod.POST)
	public String memoInsert(Model model) {
		log.info("memoInsert()����");
		
		
		model.addAttribute("msg", "�޸��� �׽�Ʈ");
		model.addAttribute("loc", "memos");
		return "memo/message";
	}
	
	@RequestMapping("/memos")
	public String memoList(Model model) {
		log.info("memoList()����");
		
		int cnt = memoDAO.getTotalCount();
		log.info("cnt==="+cnt);
		
		model.addAttribute("totalCount", cnt);
		return "memo/list";
	}
	
	@RequestMapping("/edit")
	public String memoEdit() {
		return "memo/edit";
	}
	
	@RequestMapping("/ajaxUpload")
	public String memoAjaxUpload() {
		return "memo/ajaxUpload";
	}
	
	/*@RequestMapping("/message")
	public String memoMessage(Model model, String msg, String loc) {
		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);
		return "memo/message";
	}
	
	@RequestMapping("memo/testTabCount")
	public String memoTestTabCount() {
		return "memo/testTabCount";
	}*/
	
}