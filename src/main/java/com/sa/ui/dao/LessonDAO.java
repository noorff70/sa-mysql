package com.sa.ui.dao;

import com.sa.ui.model.Lesson;

public interface LessonDAO {

	// content of a course's lesson page; empty lists (never null) when the course has none
	public Lesson getLessonByCourseId(long courseId);
}
