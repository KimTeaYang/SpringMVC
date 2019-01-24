package com.tis.myapp;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.log4j.Log4j;

/* [1] MultipartFile을 이용하는 방법
 * [2] MultipartHttpServletRequest를 이용하는 방법
 *  => 이 경우는 동일한 파라미터명으로 여러 개의 파일을 업로드하는 경우에 유용함
 * 
 * */

@Controller
@Log4j
public class FileController {
	@javax.annotation.Resource(name="upDir")
	//@Value("C:\\myjava\\Upload")
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
	
	//ResponseEntity타입 : 데이터와 함께 헤더 상태 메시지를 전달하고자 할 때 사용
	//HTTP헤더를 다뤄야 할 경우 ResponseEntity를 통해 헤더정보나 데이터를 전달 할 수 있다.
	@RequestMapping(value="/fileDown",produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(HttpServletRequest req,@RequestHeader("User-Agent") String userAgent,
			@RequestParam("fname") String fname) {
		log.info("fname="+fname+", userAgent=="+userAgent);
		
		String up_dir = req.getServletContext().getRealPath("/images");
		String filePath = up_dir+File.separator+fname;
		
		log.info("filePath="+filePath);
		
		Resource resource = new FileSystemResource(filePath);
		
		if(!resource.exists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

		String downloadName = null;

		try {
			if (checkIE) {
				//IE인 경우
				downloadName = URLEncoder.encode(fname, "UTF8").replaceAll("\\+", " ");
			} else {
				//그 외
				downloadName = new String(fname.getBytes("UTF-8"), "ISO-8859-1");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename="+downloadName);
		
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
}
