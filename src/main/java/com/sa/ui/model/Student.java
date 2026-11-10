package com.sa.ui.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student extends User{

	// enrolled courses; the Angular app reads student.course after login and enrolment
	// (same field name as sa-mongo's User.course)
	private List<Course> course;
}
