package com.sa.ui.service;

import java.util.List;

import com.sa.ui.model.Course;
import com.sa.ui.model.UserAccessReturnObject;


public interface CourseService {

	//public List<Contents> getAllContentsById(long id);
	public List<Course> getContentsByStudentId(long studentId);
	public List<Course> getContentsListByContentDesc(String desc);
	public UserAccessReturnObject addContentToUser(Long userId, Long contendId) ;
	//public Boolean insertStudentContent(String userName, long contentId);

}
