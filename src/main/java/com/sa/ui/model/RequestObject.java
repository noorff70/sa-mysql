package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Request body of the endpoints the Angular app calls with a RequestObject
 * (same JSON as sa-mongo's RequestObject).
 */
@Getter
@Setter
public class RequestObject {

	private String courseDescription;        // course search text
	private String webCourseSearchCriteria;  // instructor-led subject name, e.g. 'Java'
	private String tutorId;                  // tutor key, e.g. 'faizulnoor'

	// register for an instructor-led course date
	private Integer webSubjectId;
	private Integer webAvailableCourseId;    // webcourse.webCourseId
	private Integer webCourseScheduleId;     // webcourseoffer.offerNumber
	private Student webAddStudent;
}
