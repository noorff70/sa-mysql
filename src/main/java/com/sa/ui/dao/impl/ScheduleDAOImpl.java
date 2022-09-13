package com.sa.ui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.ScheduleDAO;
import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.rowmapper.ScheduleCourseMapper;

@Transactional
@Repository
public class ScheduleDAOImpl implements ScheduleDAO{
	
	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<ScheduleCourse> getAvailableDates(long courseId) {
		
		String query = "SELECT T.TUTORID, T.TUTORFNAME, T.TUTORLNAME, C.COURSEID, C.COURSENAME,"
				+ "S.SCHEDULEID, S.SCHEDULEDATE "
				+ "FROM TUTOR T, COURSE C, SCHEDULECOURSE S "
				+ "WHERE C.COURSEID = ? AND T.TUTORID = S.TUTORID AND C.COURSEID = S.COURSEID "
				+ "AND S.SCHEDULEDATE>CURDATE()";
		
		
		@SuppressWarnings("deprecation")
		List<ScheduleCourse> sc = jdbcTemplate.query(
				query, new Object[] { courseId }, new ScheduleCourseMapper());
		
		return sc;
	}

}
