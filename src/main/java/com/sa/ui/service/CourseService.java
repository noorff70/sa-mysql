package com.sa.ui.service;

import java.util.List;

import com.sa.ui.model.Course;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.UserCourse;


public interface CourseService {

	public List<Course> getContentsByStudentId(long studentId);
	public List<Course> getContentsListByContentDesc(String desc);
	public UserAccessReturnObject addContentToUser(Long userId, Long contendId) ;

	// sa-mongo compatible (used by the Angular app when environment.dataApi = 'mysql')
	public List<Course> getCourseListByCourseDesc(String desc);
	public UserAccessReturnObject addCourseToUser(UserCourse userCourse);

}
