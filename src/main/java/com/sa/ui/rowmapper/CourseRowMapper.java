package com.sa.ui.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sa.ui.model.Course;

public class CourseRowMapper implements RowMapper<Course>{
	
	public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		Course contents = new Course();
		contents.setCourseId(rs.getLong("courseId"));
		contents.setCourseName(rs.getString("courseName"));
		contents.setCourseDesc(rs.getString("courseDesc"));
		contents.setTutorId(rs.getLong("tutorId"));
		contents.setTopicId(rs.getLong("topicId"));
		return contents;
   }
}
