package com.tis.myapp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.log4j.Log4j;

/* [1] MultipartFile을 이용하는 방법
 * [2] MultipartHttpServletRequest를 이용하는 방법
 *  => 이 경우는 동일한 파라미터명으로 여러 개의 파일을 업로드하는 경우에 유용함
 * 
 * */

@Controller("fileController")
@RequestMapping(value="/file")
@Log4j
public class FileController {
	
	@Resource(name="upDir")
	private String upDir;
	
	@RequestMapping(value="/fileUp",method=RequestMethod.GET)
	public String fileUp() {
		return "fileup/fileTest";
	}
	
	@RequestMapping(value="/fileUp",method=RequestMethod.POST)
	public String fileUp(@RequestParam("name") String name,@RequestParam("filename") MultipartFile filename,Model model) {
		log.info("name=="+name);
		log.info("filename=="+filename.getOriginalFilename());
		log.info("upDir=="+upDir);
		
		String fname = filename.getOriginalFilename();
		long fsize = filename.getSize();
		
		try {
			if(!filename.isEmpty()) {
				//byte[] b = file.getBytes();
				//FileCopyUtils.copy(b, new File(upDir,fname));
				filename.transferTo(new File(upDir,fname));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("file1", fname);
		model.addAttribute("file1Size", fsize);
		model.addAttribute("upDir", upDir);
		
		return "fileup/fileUpload";
	}
	
	@RequestMapping(value="/fileUp2",method=RequestMethod.GET)
	public String fileUp2() {
		return "fileup/fileTest2";
	}
	
	
	// [2] MultipartHttpServletRequest를 이용하는 방법
	@RequestMapping(value="/fileUp2",method=RequestMethod.POST)
	public String fileUp2(HttpServletRequest req,Model m) {
		
		String name = req.getParameter("name");
		
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		
		List<MultipartFile> arr = mr.getFiles("filename");
		if(arr!=null) {
			for(int i=0;i<arr.size();i++) {
				MultipartFile mf = arr.get(i);
				String fname = mf.getOriginalFilename();
				long fsize = mf.getSize();
				
				try {
					mf.transferTo(new File(upDir,fname));
					m.addAttribute("file"+(i+1), fname);
					m.addAttribute("file"+(i+1)+"Size", fsize);
					m.addAttribute("upDir", upDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "fileup/fileUpload";
	}
	
}
