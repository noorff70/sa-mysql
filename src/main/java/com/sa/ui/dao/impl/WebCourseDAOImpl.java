package com.sa.ui.dao.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.WebCourseDAO;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.WebCourse;

@Transactional
@Repository
public class WebCourseDAOImpl implements WebCourseDAO {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(readOnly = true)
	public WebCourse getWebCourseBySubjectName(String subjectName, boolean hidePastOffers) {

		List<Map<String, Object>> subject = jdbcTemplate.queryForList(
				"SELECT subjectId, webCourseName FROM websubject WHERE webCourseName = ?", subjectName);
		if (subject.isEmpty()) {
			return null;
		}
		WebCourse webCourse = new WebCourse();
		long subjectId = ((Number) subject.get(0).get("subjectId")).longValue();
		webCourse.setSubjectId(subjectId);
		webCourse.setWebCourseName((String) subject.get(0).get("webCourseName"));

		Map<Long, WebCourse.WebAvailableCourse> courses = new HashMap<>();
		jdbcTemplate.query(
				"SELECT w.*, t.tutorKey FROM webcourse w LEFT JOIN tutor t ON t.tutorId = w.tutorId "
				+ "WHERE w.subjectId = ? ORDER BY w.webCourseId",
				rs -> {
					WebCourse.WebAvailableCourse c = new WebCourse.WebAvailableCourse();
					c.setCourseId(rs.getLong("webCourseId"));
					c.setCourseName(rs.getString("courseName"));
					c.setCourseIntroduction(rs.getString("courseIntroduction"));
					c.setCourseObjective(rs.getString("courseObjective"));
					c.setDuration(rs.getString("duration"));
					c.setAvailable(rs.getBoolean("available"));
					c.setCourseInstruction(rs.getString("courseInstruction"));
					c.setCourseType(rs.getString("courseType"));
					c.setCourseSize(rs.getInt("courseSize"));
					c.setCourseFee(rs.getInt("courseFee"));
					c.setTutorId(rs.getString("tutorKey"));
					courses.put(c.getCourseId(), c);
					webCourse.getAvailableCourses().add(c);
				}, subjectId);

		jdbcTemplate.query(
				"SELECT t.webCourseId, t.tag FROM webcoursetag t JOIN webcourse w ON w.webCourseId = t.webCourseId "
				+ "WHERE w.subjectId = ? ORDER BY t.webCourseId, t.position",
				rs -> {
					WebCourse.WebAvailableCourse c = courses.get(rs.getLong("webCourseId"));
					if (c != null) {
						c.getTags().add(rs.getString("tag"));
					}
				}, subjectId);

		// dates with the number of places taken; full dates are left out (as in sa-mongo),
		// and past dates too when hidePastOffers is set
		jdbcTemplate.query(
				"SELECT o.offerId, o.webCourseId, o.offerNumber, o.scheduleLabel, o.startsAt, COUNT(r.userId) AS taken "
				+ "FROM webcourseoffer o JOIN webcourse w ON w.webCourseId = o.webCourseId "
				+ "LEFT JOIN webcourseofferuser r ON r.offerId = o.offerId "
				+ "WHERE w.subjectId = ? "
				+ (hidePastOffers ? "AND (o.startsAt IS NULL OR o.startsAt > UTC_TIMESTAMP()) " : "")
				+ "GROUP BY o.offerId "
				+ "ORDER BY o.webCourseId, o.startsAt IS NULL, o.startsAt, o.offerNumber",
				rs -> {
					WebCourse.WebAvailableCourse c = courses.get(rs.getLong("webCourseId"));
					if (c == null) {
						return;
					}
					int taken = rs.getInt("taken");
					if (c.getCourseSize() > 0 && taken >= c.getCourseSize()) {
						return;   // full
					}
					WebCourse.WebCourseSchedule s = new WebCourse.WebCourseSchedule();
					s.setWebCourseOfferNumber(rs.getInt("offerNumber"));
					s.setWebCourseScheduleDate(rs.getString("scheduleLabel"));
					LocalDateTime startsAt = rs.getObject("startsAt", LocalDateTime.class);
					s.setStartsAt(startsAt == null ? null : startsAt.toString() + "Z");
					s.setPlacesTaken(taken);
					c.getWebCourseSchedule().add(s);
				}, subjectId);

		return webCourse;
	}

	@Override
	public UserAccessReturnObject registerForOffer(Integer subjectId, int webCourseId, int offerNumber, long userId) {

		UserAccessReturnObject result = new UserAccessReturnObject();
		result.setApiResponseStatus(STATUS_FAIL);

		// lock the date so that two registrations at the same time cannot both take the last place
		List<Map<String, Object>> offer = jdbcTemplate.queryForList(
				"SELECT o.offerId, w.courseSize, w.subjectId FROM webcourseoffer o "
				+ "JOIN webcourse w ON w.webCourseId = o.webCourseId "
				+ "WHERE o.webCourseId = ? AND o.offerNumber = ? FOR UPDATE",
				webCourseId, offerNumber);
		if (offer.isEmpty()
				|| (subjectId != null && ((Number) offer.get(0).get("subjectId")).intValue() != subjectId)) {
			result.setMsgReturned(MSG_NO_DATE);
			return result;
		}
		int offerId = ((Number) offer.get(0).get("offerId")).intValue();
		int courseSize = ((Number) offer.get(0).get("courseSize")).intValue();

		Integer already = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM webcourseofferuser WHERE offerId = ? AND userId = ?", Integer.class, offerId, userId);
		if (already != null && already > 0) {
			result.setMsgReturned(MSG_ALREADY);
			return result;
		}

		Integer taken = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM webcourseofferuser WHERE offerId = ?", Integer.class, offerId);
		if (courseSize > 0 && taken != null && taken >= courseSize) {
			result.setMsgReturned(MSG_FULL);
			return result;
		}

		try {
			jdbcTemplate.update("INSERT INTO webcourseofferuser (offerId, userId) VALUES (?, ?)", offerId, userId);
			result.setApiResponseStatus(STATUS_SUCCESS);
			result.setMsgReturned(MSG_REGISTERED);
		} catch (DuplicateKeyException e) {
			result.setMsgReturned(MSG_ALREADY);
		} catch (Exception e) {
			LOG.error("registerForOffer failed for user " + userId + ", web course " + webCourseId + ", date " + offerNumber, e);
			result.setMsgReturned(MSG_ERROR);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer findOfferId(int webCourseId, int offerNumber) {
		List<Integer> ids = jdbcTemplate.queryForList(
				"SELECT offerId FROM webcourseoffer WHERE webCourseId = ? AND offerNumber = ?", Integer.class, webCourseId, offerNumber);
		return ids.isEmpty() ? null : ids.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer findCourseFee(int webCourseId) {
		List<Integer> fees = jdbcTemplate.queryForList(
				"SELECT courseFee FROM webcourse WHERE webCourseId = ?", Integer.class, webCourseId);
		return fees.isEmpty() ? null : fees.get(0);
	}
}
