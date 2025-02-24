package com.sa.ui.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.ScheduleDAO;
import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.model.ScheduleUser;
import com.sa.ui.rowmapper.ScheduleCourseMapper;

@Transactional
@Repository
public class ScheduleDAOImpl implements ScheduleDAO{
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<ScheduleCourse> getAvailableDates(long courseId) {
		
		String query = "SELECT t.tutorId, t.tutorFName, t.tutorLName, c.courseId, c.courseName,"
				+ "s.scheduleId, s.scheduleDate "
				+ "FROM tutor t, course c, schedule s, schedulecourse sc "
				+ "WHERE c.courseId = ? AND t.tutorId = sc.tutorId AND c.courseId = sc.courseId AND s.scheduleId = sc.scheduleId "
				+ "AND s.scheduleDate>CURDATE()";
		
		
		@SuppressWarnings("deprecation")
		List<ScheduleCourse> sc = jdbcTemplate.query(
				query, new Object[] { courseId }, new ScheduleCourseMapper());
		
		return sc;
	}
	
	public int registerSchedule(ScheduleUser su) {
		
		// returns 1 for success and 0 for failure
		try {
			return jdbcTemplate.update(
	                "insert into scheduleuser (userId, scheduleId) values(?,?)", su.getUserId(), su.getScheduleId());
		}catch(Exception e) {
			LOG.error("ScheduleDAOImpl- registerSchedule" + e.getMessage());
		}
		return 0;
		
	}

}
