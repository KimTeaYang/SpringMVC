package com.tis.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {
	
	@RequestMapping(path="/test")
	public String mytest(Model model) {
		model.addAttribute("msg","좋은 한주 되세요~~");
		//req.setAttribute("msg","좋은 한주 되세요~~"); 와 동일
		//스프링에서는 Model 또는 ModelMap객체에 주로 저장한다.
		
		return "sample"; // 뷰이름을 문자열로 반환한다.
		/* servlet-context.xml에 설정된 InternalResourceViewResolver빈이
		 * 뷰이름 앞에 접두어(WEB-INF/views/)를 붙이고 접미어(.jsp)를 붙여 뷰페이지를 찾아가도록 한다. 
		 * /WEB-INF/views/sample.jsp
		 * */
	}
	
}