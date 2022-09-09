package com.sa.ui.dao;

import java.util.List;

import com.sa.ui.model.Course;
import com.sa.ui.model.UserAccessReturnObject;

public interface CourseDAO {

	//public List<Contents> getAllContentsById(long id);
	public List<Course> getContentsByStudentId(long studentId);
	public List<Course> getContentsListByContentDesc(String desc);
	public UserAccessReturnObject addContentToUser(Long userId, Long contendId) ;
}
