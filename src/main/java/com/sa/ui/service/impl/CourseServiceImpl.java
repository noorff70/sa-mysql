package com.sa.ui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.CourseDAO;
import com.sa.ui.model.Course;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.CourseService;



@Service("contentService")
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseDAO courseDAO;
	
	/*@Override
	public List<Contents> getAllContentsById(long id) {

		return contentDAO.getAllContentsById(id);
	}*/
	
	@Override
	public List<Course> getContentsByStudentId(long studentId){
		
		return courseDAO.getContentsByStudentId(studentId);
		
	}
	
	public List<Course> getContentsListByContentDesc(String desc) {
		return courseDAO.getContentsListByContentDesc(desc);
	}

	@Override
	public UserAccessReturnObject addContentToUser(Long userId, Long contentId) {
		
		UserAccessReturnObject userAccessReturnObject =  
				courseDAO.addContentToUser(userId, Long.valueOf(contentId));
		return userAccessReturnObject;
	}

}

