package com.sa.ui.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.sa.ui.model.UserCourse;

public class UserContentRowMapper implements RowMapper<UserCourse>{

	@Override
	public UserCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserCourse userContent = new UserCourse();
		userContent.setUserCourseId(userContent.getUserCourseId());
		userContent.setCourseId(userContent.getCourseId());
		userContent.setUserId(userContent.getUserId());

		return userContent;
	}
}
