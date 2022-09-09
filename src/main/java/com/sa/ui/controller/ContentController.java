package com.sa.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sa.ui.model.Course;
import com.sa.ui.model.Student;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.UserCourse;
import com.sa.ui.service.CourseService;

@RestController
public class ContentController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseService contentService;
	
	/*@GetMapping("/ui/getContentListById")
	public List<Contents> getTopicListById (@RequestParam("CONTENTID") long contentId) {

		List<Contents> contentList = contentService.getAllContentsById(contentId);
		
		return contentList;
	}*/
	
	@PostMapping("/ui/getContentsByStudentId")
	public List<Course> getContentsByStudentId (@RequestBody Student student) {
		
		List <Course> contentList = contentService.getContentsByStudentId(student.getUserId());
		return contentList;
	}
	
	@PostMapping("/ui/getContentListByContentDesc")
	public List<Course> getTopicListByContentDescription (@RequestBody String desc) {

		List<Course> contentList = contentService.getContentsListByContentDesc(desc);
		
		return contentList;
	}
	
	@GetMapping("/ui/getAllContentList")
	public List<Course> getAllContentList () {

		List<Course> contentList = contentService.getContentsListByContentDesc(null);
		
		return contentList;
		
	}
	
	@PostMapping("/ui/addContentToStudent")
	public UserAccessReturnObject addContentToUser(@RequestBody UserCourse content) {
		
		Long courseId = content.getCourseId();
		Long userId = content.getUserId();
		
		UserAccessReturnObject returnObject = contentService.addContentToUser(userId, courseId);
		
		return returnObject;
		
	}
}
