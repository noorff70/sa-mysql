package com.sa.ui.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/*
 * Instructor-led courses of one subject ('Java', 'Web Technologies', ...).
 * Same JSON as sa-mongo's WebCourse / WebAvailableCourse / WebCourseSchedule, except that
 * webCourseStudentList is always empty: only placesTaken is sent (no student names or emails).
 */
@Getter
@Setter
public class WebCourse {

	private long subjectId;
	private String webCourseName;
	private List<WebAvailableCourse> availableCourses = new ArrayList<>();

	@Getter
	@Setter
	public static class WebAvailableCourse {
		private long courseId;              // webcourse.webCourseId
		private String courseIntroduction;
		private String courseName;
		private String courseObjective;
		private String duration;
		private boolean available;
		private String courseInstruction;
		private String courseType;
		private int courseSize;             // maximum students per date
		private int courseFee;
		private List<String> tags = new ArrayList<>();
		private List<WebCourseSchedule> webCourseSchedule = new ArrayList<>();
		private String tutorId;             // tutor.tutorKey
	}

	@Getter
	@Setter
	public static class WebCourseSchedule {
		private int webCourseOfferNumber;
		private String webCourseScheduleDate;   // label shown on the site
		private String startsAt;                // UTC, ISO 8601, or null
		private int placesTaken;
		private List<Student> webCourseStudentList = new ArrayList<>();   // always empty (privacy)
	}
}
