package com.tis.common;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CommonUtil {

	public String addMsgLoc(Model model,String msg,String loc) {
		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);
		return "memo/message";
	}
	
	public String addMsgBack(Model model,String msg) {
		model.addAttribute("msg", msg);
		model.addAttribute("loc", "javascript:history.back()");
		return "memo/message";
	}
	
}