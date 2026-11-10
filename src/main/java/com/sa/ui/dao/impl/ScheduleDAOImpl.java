package com.sa.ui.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
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

		String query = "SELECT t.tutorId, t.tutorFName, t.tutorLName, c.courseId, c.courseName, "
				+ "s.scheduleId, s.scheduleDate, s.scheduleTime "
				+ "FROM schedulecourse sc "
				+ "INNER JOIN course c ON c.courseId = sc.courseId "
				+ "INNER JOIN tutor t ON t.tutorId = sc.tutorId "
				+ "INNER JOIN schedule s ON s.scheduleId = sc.scheduleId "
				+ "WHERE sc.courseId = ? AND s.scheduleDate > CURDATE() "
				+ "ORDER BY s.scheduleDate, s.scheduleTime";

		return jdbcTemplate.query(query, new ScheduleCourseMapper(), courseId);
	}

	// returns REGISTERED, ALREADY_REGISTERED or FAILED
	public int registerSchedule(ScheduleUser su) {

		try {
			int rows = jdbcTemplate.update(
	                "insert into scheduleuser (userId, scheduleId) values(?,?)", su.getUserId(), su.getScheduleId());
			return rows == 1 ? REGISTERED : FAILED;
		} catch (DuplicateKeyException e) {
			return ALREADY_REGISTERED;
		} catch (Exception e) {
			LOG.error("registerSchedule failed for user " + su.getUserId() + ", schedule " + su.getScheduleId(), e);
		}
		return FAILED;

	}

}
