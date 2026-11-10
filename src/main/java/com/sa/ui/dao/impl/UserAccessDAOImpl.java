package com.sa.ui.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserAccessReturnObject registerUser(User user) {

		UserAccessReturnObject returnObject = new UserAccessReturnObject();

		try {
			// email is UNIQUE but optional: store a blank email as NULL so that
			// several users without an email don't collide on ''
			String email = user.getEmail();
			if (email != null && email.trim().isEmpty()) {
				email = null;
			}

		    // check if user exists
			List<User> userList = jdbcTemplate.query("SELECT * FROM user WHERE userName = ?",
					new UserRowMapper(), user.getUserName());

			if (userList.size() > 0) {
				returnObject.setSuccess(false);
				returnObject.setMsgReturned("User already exists. Please use a different Name");
				return returnObject;
			}

			// check if the email is already registered (email is UNIQUE)
			if (email != null) {
				Integer emailCount = jdbcTemplate.queryForObject(
						"SELECT COUNT(*) FROM user WHERE email = ?", Integer.class, email);
				if (emailCount != null && emailCount > 0) {
					returnObject.setSuccess(false);
					returnObject.setMsgReturned("Email already registered. Please use a different Email");
					return returnObject;
				}
			}

			// plain INSERT: SimpleJdbcInsert looked up table metadata for "user" across every
			// database on the server and could pick up mysql.user instead of studyaid.user
			final String insertSql = "INSERT INTO user (firstName, lastName, email, userName, password) "
					+ "VALUES (?, ?, ?, ?, ?)";
			final String finalEmail = email;
			final String passwordHash = passwordEncoder.encode(user.getPassword());
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getFirstName());
				ps.setString(2, user.getLastName());
				ps.setString(3, finalEmail);
				ps.setString(4, user.getUserName());
				ps.setString(5, passwordHash);
				return ps;
			}, keyHolder);

			Number id = keyHolder.getKey();
			if (id != null && id.longValue() > 0) {
				returnObject.setSuccess(true);
				// the Angular register page (and sa-mongo) use loginSuccess as "sign-up succeeded"
				returnObject.setLoginSuccess(true);
				returnObject.setMsgReturned("User created");
			} else {
				returnObject.setSuccess(false);
				returnObject.setMsgReturned("Please contact Admin ");
			}

		} catch (DuplicateKeyException e) {
			// another sign-up with the same user name or email got in between the checks and the insert
			returnObject.setSuccess(false);
			returnObject.setMsgReturned("User name or Email already registered");
		} catch (Exception e) {
			LOG.error("registerUser failed for " + user.getUserName(), e);
			returnObject.setSuccess(false);
			returnObject.setMsgReturned("Please contact Admin ");
		}
		return returnObject;
	}

	@Override
	public UserAccessReturnObject loginUser(User user) {

		UserAccessReturnObject returnObject = new UserAccessReturnObject();
		returnObject.setLoginSuccess(false);

		try {
		    // check if user exists
			List<User> userList = jdbcTemplate.query("SELECT * FROM user WHERE userName = ?",
					new UserRowMapper(), user.getUserName());

			if (userList.size() == 1) {

				User dbUser = userList.get(0);

				if (passwordMatches(user.getPassword(), dbUser)) {
					Student student = new Student();
					student.setUserName(dbUser.getUserName());
					student.setEmail(dbUser.getEmail());
					student.setUserId(dbUser.getUserId());
					student.setFirstName(dbUser.getFirstName());
					student.setLastName(dbUser.getLastName());
					returnObject.setStudent(student);
					returnObject.setLoginSuccess(true);
				} else {
					returnObject.setMsgReturned("NO_MATCH");
				}
			}
			else {
				returnObject.setMsgReturned("User not Found");
			}

		} catch (Exception e) {
			// details go to the log only, not back to the browser
			LOG.error("loginUser failed for " + user.getUserName(), e);
			returnObject.setSuccess(false);
			returnObject.setMsgReturned("Please contact Admin");
		}
		return returnObject;
	}

	/*
	 * Checks the password against the stored BCrypt hash. Accounts created before hashing
	 * was introduced still hold the plain-text password: those are accepted once and
	 * re-saved as a hash, so they are migrated on their next successful login.
	 */
	private boolean passwordMatches(String rawPassword, User dbUser) {
		String stored = dbUser.getPassword();
		if (rawPassword == null || stored == null) {
			return false;
		}
		if (isBCryptHash(stored)) {
			return passwordEncoder.matches(rawPassword, stored);
		}
		if (stored.equals(rawPassword)) {
			jdbcTemplate.update("UPDATE user SET password = ? WHERE userId = ?",
					passwordEncoder.encode(rawPassword), dbUser.getUserId());
			LOG.info("Migrated plain-text password to BCrypt for userId {}", dbUser.getUserId());
			return true;
		}
		return false;
	}

	private static boolean isBCryptHash(String value) {
		return value.length() == 60 && (value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$"));
	}
}
