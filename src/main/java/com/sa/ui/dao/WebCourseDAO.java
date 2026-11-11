package com.sa.ui.dao;

import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.WebCourse;

public interface WebCourseDAO {

	String STATUS_SUCCESS = "STATUS_SUCCESS";
	String STATUS_FAIL = "STATUS_FAIL";

	// messages as in sa-mongo, which the Angular register page shows
	String MSG_REGISTERED = "You are Registered. We will send you Zoom link to attend.";
	String MSG_ALREADY = "You already have enrolled for this Training.";
	String MSG_FULL = "Course Enrollment is Full";
	String MSG_NO_DATE = "Course date not found";
	String MSG_ERROR = "Could not be saved. Please contact admin";
	String MSG_PAY_FIRST = "This course has a fee. Please register through the payment page.";

	// courses of one subject with their open dates; null when the subject does not exist
	public WebCourse getWebCourseBySubjectName(String subjectName, boolean hidePastOffers);

	// registers a user for a date: locks the date, refuses duplicates and full dates
	public UserAccessReturnObject registerForOffer(Integer subjectId, int webCourseId, int offerNumber, long userId);

	// offerId of a course date, or null
	public Integer findOfferId(int webCourseId, int offerNumber);

	// fee of a web course, or null when the course does not exist
	public Integer findCourseFee(int webCourseId);
}
