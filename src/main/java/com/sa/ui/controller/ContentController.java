package com.sa.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sa.ui.model.Contents;
import com.sa.ui.model.Student;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.UserContent;
import com.sa.ui.service.ContentService;

@RestController
public class ContentController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ContentService contentService;
	
	/*@GetMapping("/ui/getContentListById")
	public List<Contents> getTopicListById (@RequestParam("CONTENTID") long contentId) {

		List<Contents> contentList = contentService.getAllContentsById(contentId);
		
		return contentList;
	}*/
	
	@PostMapping("/ui/getContentsByStudentId")
	public List<Contents> getContentsByStudentId (@RequestBody Student student) {
		
		List <Contents> contentList = contentService.getContentsByStudentId(student.getUserId());
		return contentList;
	}
	
	@PostMapping("/ui/getContentListByContentDesc")
	public List<Contents> getTopicListByContentDescription (@RequestBody String desc) {

		List<Contents> contentList = contentService.getContentsListByContentDesc(desc);
		
		return contentList;
	}
	
	@GetMapping("/ui/getAllContentList")
	public List<Contents> getAllContentList () {

		List<Contents> contentList = contentService.getContentsListByContentDesc(null);
		
		return contentList;
		
	}
	
	@PostMapping("/ui/addContentToStudent")
	public UserAccessReturnObject addContentToUser(@RequestBody UserContent content) {
		
		Long contentId = content.getContentId();
		Long userId = content.getUserId();
		
		UserAccessReturnObject returnObject = contentService.addContentToUser(userId, contentId);
		
		return returnObject;
		
	}
}
