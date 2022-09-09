package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {

	private long courseId;
	private String courseName;
	private String courseDesc;
	private long tutorId;
	private long topicId;
}
