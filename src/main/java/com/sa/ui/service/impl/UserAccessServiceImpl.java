package com.sa.ui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.CourseDAO;
import com.sa.ui.dao.UserAccessDAO;
import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.UserAccessService;

@Service("accessService")
public class UserAccessServiceImpl implements UserAccessService{

	@Autowired
	private UserAccessDAO accessDAO;

	@Autowired
	private CourseDAO courseDAO;

	@Override
	public UserAccessReturnObject registerUser(User user) {
		return accessDAO.registerUser(user);
	}

	@Override
	public UserAccessReturnObject loginUser(User user) {
		UserAccessReturnObject result = accessDAO.loginUser(user);

		// the Angular app reads the enrolled courses from student.course after login
		if (result.isLoginSuccess() && result.getStudent() != null) {
			result.getStudent().setCourse(
					courseDAO.getContentsByStudentId(result.getStudent().getUserId()));
		}
		return result;
	}

}
