package com.sa.ui.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.sa.ui.model.UserContent;

public class UserContentRowMapper implements RowMapper<UserContent>{

	@Override
	public UserContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserContent userContent = new UserContent();
		userContent.setUserContentId(userContent.getUserContentId());
		userContent.setContentId(userContent.getContentId());
		userContent.setUserId(userContent.getUserId());

		return userContent;
	}
}
