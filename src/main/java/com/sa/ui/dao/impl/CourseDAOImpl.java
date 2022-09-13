package com.sa.ui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	
	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	/*@Override
	public List<Contents> getAllContentsById(long id) {
		
		String query = "SELECT * FROM contents WHERE courseId = ?";
		@SuppressWarnings("deprecation")
		List<Contents> contents = jdbcTemplate.query(
		  query, new Object[] { id}, new ContentsRowMapper());

		return contents;
		
	}*/
	// get contents list from usercontent table with student id
	public List<Course> getContentsByStudentId(long studentId) {
		
		String query = "SELECT * FROM COURSE c "
				+ "inner join USERCOURSE sc "
				+ "on c.COURSEID = sc.COURSEID "
				+ "inner join USER u "
				+ "on u.USERID = sc.USERID "
				+ " and u.USERID=?";
		@SuppressWarnings("deprecation")
		List<Course> contents = jdbcTemplate.query(
		  query, new Object[] { studentId }, new CourseRowMapper());

		return contents;
		
	}
	
	public List<Course> getContentsListByContentDesc(String desc) {
		
		desc = desc.toLowerCase();
		
		String query = "select * from course where LOWER(courseDesc) LIKE ? and courseType = 'FREE'";
		@SuppressWarnings("deprecation")
		List<Course> contents = jdbcTemplate.query(
				query, new Object[] { "%"+desc+ "%" }, new CourseRowMapper());

		return contents;
		
	}

	@Override
	public UserAccessReturnObject addContentToUser(Long userId, Long courseId) {
		
		UserAccessReturnObject returnedObject = new UserAccessReturnObject();
		
		try {
			
			final String INSERT_QUERY = "insert into USERCOURSE (USERID, COURSEID) values (?, ?)";
			
			int id = jdbcTemplate.update(INSERT_QUERY, userId, courseId);  
			
			if (id == 1) {
				returnedObject.setAddContentToUserSuccess(true);
				returnedObject.setMsgReturned("Course added");
			}
			
		} catch (Exception e) {
			returnedObject.setAddContentToUserSuccess(false);
			returnedObject.setMsgReturned("Error: Contact Admin");
		}
		return returnedObject;
	}
	
}
