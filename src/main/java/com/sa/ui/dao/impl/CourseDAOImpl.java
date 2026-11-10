package com.sa.ui.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.CourseDAO;
import com.sa.ui.model.Course;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.rowmapper.CourseRowMapper;


@Transactional
@Repository
public class CourseDAOImpl  implements CourseDAO{

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	// courses the user is enrolled in (usercourse)
	public List<Course> getContentsByStudentId(long studentId) {

		String query = "SELECT c.* FROM course c "
				+ "INNER JOIN usercourse uc ON uc.courseId = c.courseId "
				+ "WHERE uc.userId = ? "
				+ "ORDER BY c.courseName";

		return jdbcTemplate.query(query, new CourseRowMapper(), studentId);
	}

	// FREE courses whose description contains desc; a null or blank desc returns all FREE courses
	public List<Course> getContentsListByContentDesc(String desc) {

		if (desc == null || desc.trim().isEmpty()) {
			return jdbcTemplate.query(
					"SELECT * FROM course WHERE courseType = 'FREE'", new CourseRowMapper());
		}

		// the _ai_ci collation already ignores case; escape LIKE wildcards typed by the user
		String pattern = "%" + escapeLike(desc.trim()) + "%";

		String query = "SELECT * FROM course WHERE courseDesc LIKE ? AND courseType = 'FREE'";
		return jdbcTemplate.query(query, new CourseRowMapper(), pattern);
	}

	// all courses (FREE and PAID) whose description contains desc; a null or blank desc returns
	// every course. Same behaviour as sa-mongo's /mongo/getCourseListByCourseDesc.
	public List<Course> getCourseListByCourseDesc(String desc) {

		if (desc == null || desc.trim().isEmpty()) {
			return jdbcTemplate.query("SELECT * FROM course ORDER BY courseName", new CourseRowMapper());
		}

		String pattern = "%" + escapeLike(desc.trim()) + "%";
		return jdbcTemplate.query("SELECT * FROM course WHERE courseDesc LIKE ? ORDER BY courseName",
				new CourseRowMapper(), pattern);
	}

	private static String escapeLike(String text) {
		return text.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
	}

	@Override
	public UserAccessReturnObject addContentToUser(Long userId, Long courseId) {

		UserAccessReturnObject returnedObject = new UserAccessReturnObject();
		returnedObject.setAddContentToUserSuccess(false);

		try {

			final String INSERT_QUERY = "insert into usercourse (userId, courseId) values (?, ?)";

			int rows = jdbcTemplate.update(INSERT_QUERY, userId, courseId);

			if (rows == 1) {
				returnedObject.setAddContentToUserSuccess(true);
				returnedObject.setMsgReturned("Course added");
			} else {
				returnedObject.setMsgReturned("Error: Contact Admin");
			}

		} catch (DuplicateKeyException e) {
			returnedObject.setMsgReturned("Already enrolled in this course");
		} catch (DataIntegrityViolationException e) {
			// foreign key: the user or the course does not exist
			LOG.warn("addContentToUser: unknown user {} or course {}", userId, courseId);
			returnedObject.setMsgReturned("Course or user not found");
		} catch (Exception e) {
			LOG.error("addContentToUser failed for user " + userId + ", course " + courseId, e);
			returnedObject.setMsgReturned("Error: Contact Admin");
		}
		return returnedObject;
	}

}
