package com.sa.ui.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleCourse {
	
	private long scheduleId;
	private long courseId;
	private long tutorId;
	private Date schedule;
	private String tutorFName;
	private String tutorLName;
	private String courseName;

}
