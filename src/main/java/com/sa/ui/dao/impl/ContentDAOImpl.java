package com.sa.ui.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.ContentDAO;
import com.sa.ui.model.Contents;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.UserContent;
import com.sa.ui.rowmapper.ContentsRowMapper;
import com.sa.ui.rowmapper.UserContentRowMapper;


@Transactional
@Repository
public class ContentDAOImpl  implements ContentDAO{
	
	
	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
	
	/*@Override
	public List<Contents> getAllContentsById(long id) {
		
		String query = "SELECT * FROM contents WHERE contentId = ?";
		@SuppressWarnings("deprecation")
		List<Contents> contents = jdbcTemplate.query(
		  query, new Object[] { id}, new ContentsRowMapper());

		return contents;
		
	}*/
	// get contents list from usercontent table with student id
	public List<Contents> getContentsByStudentId(long studentId) {
		
		String query = "SELECT * FROM CONTENTS c "
				+ "inner join USERCONTENT sc "
				+ "on c.CONTENTID = sc.CONTENTID "
				+ "inner join USER u "
				+ "on u.USERID = sc.USERID "
				+ " and u.USERID=?";
		@SuppressWarnings("deprecation")
		List<Contents> contents = jdbcTemplate.query(
		  query, new Object[] { studentId }, new ContentsRowMapper());

		return contents;
		
	}
	
	public List<Contents> getContentsListByContentDesc(String desc) {
		
		desc = desc.toLowerCase();
		
		String query = "select * from contents where LOWER(contentDesc) LIKE ?";
		@SuppressWarnings("deprecation")
		List<Contents> contents = jdbcTemplate.query(
				query, new Object[] { "%"+desc+ "%" }, new ContentsRowMapper());

		return contents;
		
	}

	@Override
	public UserAccessReturnObject addContentToUser(Long userId, Long contentId) {
		
		UserAccessReturnObject returnedObject = new UserAccessReturnObject();
		
		try {
			
			final String INSERT_QUERY = "insert into USERCONTENT (USERID, CONTENTID) values (?, ?)";
			
			int id = jdbcTemplate.update(INSERT_QUERY, userId, contentId);  
			
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
