package com.sa.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.CourseDAO;
import com.sa.ui.dao.LessonDAO;
import com.sa.ui.dao.UserAccessDAO;
import com.sa.ui.model.Course;
import com.sa.ui.model.Lesson;
import com.sa.ui.model.Student;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.UserCourse;
import com.sa.ui.service.CourseService;



@Service("contentService")
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private UserAccessDAO userAccessDAO;

	@Autowired
	private LessonDAO lessonDAO;

	@Override
	public Lesson getLessonByContentId(long courseId) {
		return lessonDAO.getLessonByCourseId(courseId);
	}

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

	@Override
	public List<Course> getCourseListByCourseDesc(String desc) {
		return courseDAO.getCourseListByCourseDesc(desc);
	}

	/*
	 * Enrol, sa-mongo style: the reply always carries student.course (the user's courses
	 * after the attempt), because the Angular lesson page reads it before checking success.
	 */
	@Override
	public UserAccessReturnObject addCourseToUser(UserCourse userCourse) {

		Long userId = userCourse.getUserId();
		if (userId == null) {
			userId = userAccessDAO.findUserIdByUserName(userCourse.getUserName());
		}

		UserAccessReturnObject result;
		Student student = new Student();

		if (userId == null || userCourse.getCourseId() == null) {
			result = new UserAccessReturnObject();
			result.setAddContentToUserSuccess(false);
			result.setMsgReturned(userId == null ? "No User found" : "Course not found");
			student.setCourse(new ArrayList<Course>());
		} else {
			result = courseDAO.addContentToUser(userId, userCourse.getCourseId());
			student.setUserId(userId);
			student.setUserName(userCourse.getUserName());
			student.setCourse(courseDAO.getContentsByStudentId(userId));
		}

		result.setStudent(student);
		return result;
	}

}
