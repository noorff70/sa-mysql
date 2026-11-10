package com.sa.ui.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sa.ui.model.ScheduleCourse;

public class ScheduleCourseMapper implements RowMapper<ScheduleCourse>{

	@Override
	public ScheduleCourse mapRow(ResultSet rs, int rowNum) throws SQLException {

		ScheduleCourse sc = new ScheduleCourse();

		sc.setTutorId(rs.getLong("tutorId"));
		sc.setTutorFName(rs.getString("tutorFName"));
		sc.setTutorLName(rs.getString("tutorLName"));
		sc.setCourseId(rs.getLong("courseId"));
		sc.setCourseName(rs.getString("courseName"));
		sc.setScheduleId(rs.getLong("scheduleId"));
		sc.setSchedule(rs.getDate("scheduleDate"));
		sc.setScheduleTime(rs.getString("scheduleTime"));   // TIME column, null-safe as a string

		return sc;
	}
}
