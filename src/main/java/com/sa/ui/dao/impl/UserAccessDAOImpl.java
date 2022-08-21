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

import com.sa.ui.dao.UserAccessDAO;
import com.sa.ui.model.Student;
import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.rowmapper.UserRowMapper;

@Transactional
@Repository
public class UserAccessDAOImpl implements UserAccessDAO{
	
	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	@Override
	public UserAccessReturnObject registerUser(User user) {
		
		UserAccessReturnObject returnObject = new UserAccessReturnObject();
		
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
		    parameters.put("FIRSTNAME", user.getFirstName());
		    parameters.put("LASTNAME", user.getLastName());
		    parameters.put("PASSWORD", user.getPassword());
		    parameters.put("USERNAME", user.getUserName());
		    parameters.put("EMAIL", user.getEmail());
		    
		    String sql = "SELECT * FROM USER WHERE USERNAME = ? ";

		    // check if user exists
			List<User> userList = jdbcTemplate.query (sql,  new UserRowMapper(), user.getUserName());
			
			if (userList.size() > 0) {
				returnObject.setSuccess(true);
				returnObject.setMsgReturned("User already exists. Please use a different Name");
				return returnObject;
			}
			else {
				SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
		                .withTableName("USER")
		                .usingGeneratedKeyColumns("USERID");
				
				Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
				
				if (id > 0) {
					returnObject.setSuccess(true);
					returnObject.setMsgReturned("User created");
				}
			}
			
		} catch (Exception e) {
			returnObject.setSuccess(false);
			returnObject.setMsgReturned("Please contact Admin ");
			}	
		return returnObject;
	}

	@Override
	public UserAccessReturnObject loginUser(User user) {
		
		UserAccessReturnObject returnObject = new UserAccessReturnObject();
		
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
		    parameters.put("USERNAME", user.getUserName());
		    parameters.put("PASSWORD", user.getPassword());
		    
		    String sql = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";

		    // check if user exists
			List<User> userList = jdbcTemplate.query (sql,  new UserRowMapper(), user.getUserName(), user.getPassword());
			
			if (userList.size() > 0) {
				returnObject.setLoginSuccess(true);
				
				Student student = new Student();
				student.setUserName(user.getUserName());
				student.setEmail(user.getEmail());
				
				returnObject.setStudent(student);
				return returnObject;
			}
			else {
				returnObject.setLoginSuccess(false);
				returnObject.setMsgReturned("User not Found");
			}
			
		} catch (Exception e) {
			returnObject.setSuccess(false);
			returnObject.setMsgReturned("Please contact Admin ");
			}	
		return returnObject;
	}
}

/*
 * 	public List<Contents> loginByUserName(Student st) {
		
		String userName = st.getUserName().toLowerCase();
		String password = st.getPassword();
		List<Contents> contents = null;
		
		// update student as logged
		String updateQuery = "update Student set userLogged = 1 where LOWER(username) = ? AND password =?";
		int updateColumns = jdbcTemplate1.update(updateQuery, userName, password);
		
		if (updateColumns == 1) {
			// retrieve logged user
			String queryStudent = "select * from Student where LOWER(username) = ? AND password =?";

	        Student student = jdbcTemplate1.queryForObject
	        		(queryStudent, new Object[]{userName, password}, new StudentRowMapper());
	        
	        if (student != null ) {
	        	//get all the contents
	        	contents = contentDAO.getContentsByContentIdList(student.getStudentId());
	        }
		}
		return contents;
	}
	*/
