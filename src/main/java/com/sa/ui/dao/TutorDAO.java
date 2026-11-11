package com.sa.ui.dao;

import com.sa.ui.model.Tutor;

public interface TutorDAO {

	// tutor profile by tutorKey ('faizulnoor'), or null
	public Tutor findByTutorKey(String tutorKey);
}
