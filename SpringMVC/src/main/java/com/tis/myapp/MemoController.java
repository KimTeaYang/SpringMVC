package com.tis.myapp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tis.common.CommonUtil;
import com.tis.domain.MemoVO;
import com.tis.service.MemoService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/memo")
@Log4j
public class MemoController {
	
	@Autowired
	private MemoService mService;
	@Autowired
	private CommonUtil util;

	//GET방식일 때 호출됨
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String memoForm() {
		//로그 레벨에 따라 메소드 호출 debug/info/warn/error
		log.info("memoForm()����");
		return "memo/input"; // /WEB-INF/views/memo/input.jsp
	}
	
	//첨부파일 없는 메모 삽입
	@RequestMapping(value="/input_old",method=RequestMethod.POST)
	public String memoInsert_old(@ModelAttribute("memo") MemoVO memo,Model model) {
		log.info("memoInsert()===memo: "+memo);
		
		int n = this.mService.createMemo(memo);
		String msg = (n>0)?"메모 등록 성공":"등록 실패";
		String loc = (n>0)?"memos":"javascript:history.back()";
		
		return util.addMsgLoc(model, msg, loc);
	}
	
	/* 파일 업로드 처리를 위해
	 * [1] pom.xml에 commons-fileupload와 commons-io를 등록한다.
	 * [2] WEB-INF/spring/appServlet/servlet-context.xml에
	 * 		multipartResolver 빈을 등록
	 * 		[주의] 빈의 id는 반드시 multipartResolver로 주자.
	 * ------------------------------------------------------
	 *  <beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
			<beans:property name="maxUploadSize" value="-1" />
			<beans:property name="defaultEncoding" value="UTF-8"/>
		</beans:bean>
	 * ------------------------------------------------------
	 * 
	 * */
	//파일첨부 기능 있는 삽입
	@RequestMapping(value="/input",method=RequestMethod.POST)
	public String memoInsert(@ModelAttribute("memo") MemoVO memo,HttpServletRequest req,Model model) {
		log.info("memo=="+memo);
		log.info("filename=="+memo.getFilename());
		
		String upDir = req.getServletContext().getRealPath("/images");
		log.info("upDir=="+upDir);
		
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest) req;
		MultipartFile mf = mr.getFile("mfile");
		
		if(!mf.isEmpty()) {
			String filename = mf.getOriginalFilename();
			
			memo.setFilename(filename);
			
			try {
				mf.transferTo(new File(upDir,filename));
				log.info(filename+"파일 업로드 처리 성공");
			} catch (IllegalStateException | IOException e) {
				log.error("파일 업로드 오류: "+e.getMessage());
			}
		}
		
		log.info("filename=="+memo.getFilename());
		
		int n = this.mService.createMemo(memo);
		String msg = (n>0)?"메모 등록 성공":"등록 실패";
		String loc = (n>0)?"memos":"javascript:history.back()";
		
		return util.addMsgLoc(model, msg, loc);
	}
	
	@RequestMapping("/memos")
	public String memoList(Model model,@RequestParam(defaultValue="1") int cpage) {
		log.info("memoList()����");
		
		int totalCount = this.mService.getTotalCount();
		log.info("totalCount==="+totalCount);
		
		if(cpage<0) cpage=1;
		log.info("cpage=="+cpage);
		
		int pageSize = 6;
		int pageCount = ((totalCount-1)/pageSize)+1;
		int end = cpage*pageSize;
		int start = end-(pageSize-1);
		
		if(cpage>pageCount) cpage=pageCount;
		
		Map<String,Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		List<MemoVO> listMemo = this.mService.listMemo(map);
		
		model.addAttribute("listMemo", listMemo);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("cpage", cpage);
		
		return "memo/list";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String memoEdit(@RequestParam(defaultValue="0") int idx,Model model) {
		log.info("수정 글번호idx==="+idx);
		MemoVO memoVO = this.mService.getMemo(idx);
		if(memoVO==null) {
			return "redirect:memos";
		}
		model.addAttribute("memo", memoVO);
		return "memo/edit";
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String memoEdit(@ModelAttribute("memo") MemoVO memoVO,Model model) {
		log.info("memoVO=="+memoVO+"/idx="+memoVO.getIdx()+"/name="+memoVO.getName()+"/msg"+memoVO.getMsg());
		
		int n = this.mService.updateMemo(memoVO);
		
		String msg = (n>0)?"수정 성공":"수정 실패";
		String loc = (n>0)?"memos":"javascript:history.back()";
		
		return util.addMsgLoc(model, msg, loc);
	}
	
	@RequestMapping("/delete")
	public String memoDelete(@RequestParam(defaultValue="0") int idx,Model model) {
		log.info("글삭제 idx==="+idx);
		
		if(idx==0) { // 유효성 체크
			return util.addMsgLoc(model, "잘못 들어온 경로입니다.", "memos");
		}
		
		int n = this.mService.deleteMemo(idx);
		String msg = (n>0)?"삭제 성공":"삭제 실패";
		
		return util.addMsgLoc(model, msg, "memos");
	}
	
	@RequestMapping("/ajaxUpload")
	public String memoAjaxUpload() {
		return "memo/ajaxUpload";
	}
	
}