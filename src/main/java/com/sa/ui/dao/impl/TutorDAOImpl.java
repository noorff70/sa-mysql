package com.sa.ui.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.TutorDAO;
import com.sa.ui.model.Tutor;

@Transactional(readOnly = true)
@Repository
public class TutorDAOImpl implements TutorDAO {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public Tutor findByTutorKey(String tutorKey) {
		if (tutorKey == null || tutorKey.trim().isEmpty()) {
			return null;
		}
		List<Tutor> tutors = jdbcTemplate.query(
				"SELECT tutorKey, tutorFName, tutorLName, email, tutorBio, imageLocation FROM tutor WHERE tutorKey = ?",
				(rs, rowNum) -> {
					Tutor t = new Tutor();
					t.setUserName(rs.getString("tutorKey"));
					t.setFirstName(rs.getString("tutorFName"));
					t.setLastName(rs.getString("tutorLName"));
					t.setEmail(rs.getString("email"));
					t.setTutorBio(rs.getString("tutorBio"));
					t.setImageLocation(rs.getString("imageLocation"));
					return t;
				}, tutorKey);
		return tutors.isEmpty() ? null : tutors.get(0);
	}
}
