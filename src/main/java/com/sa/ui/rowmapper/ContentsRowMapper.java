package com.sa.ui.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sa.ui.model.Contents;

public class ContentsRowMapper implements RowMapper<Contents>{
	
	public Contents mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contents contents = new Contents();
		contents.setContentId(rs.getLong("contentId"));
		contents.setContentName(rs.getString("contentName"));
		contents.setContentDesc(rs.getString("contentDesc"));
		contents.setTutorId(rs.getLong("tutorId"));
		contents.setTopicId(rs.getLong("topicId"));
		return contents;
   }
}
