package com.sa.ui.service;

import com.sa.ui.model.RequestObject;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.WebCourse;

public interface WebCourseService {

	// instructor-led courses of a subject (was /mongo/getWebCourseList); never null
	public WebCourse getWebCourseList(String subjectName);

	// register the logged-in student for a course date (was /mongo/addStudentToScheduledCourse)
	public UserAccessReturnObject addStudentToScheduledCourse(RequestObject request);
}
