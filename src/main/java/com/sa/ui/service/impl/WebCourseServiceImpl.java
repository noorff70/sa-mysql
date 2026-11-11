package com.sa.ui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.UserAccessDAO;
import com.sa.ui.dao.WebCourseDAO;
import com.sa.ui.model.RequestObject;
import com.sa.ui.model.Student;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.WebCourse;
import com.sa.ui.service.WebCourseService;

@Service("webCourseService")
public class WebCourseServiceImpl implements WebCourseService {

	@Autowired
	private WebCourseDAO webCourseDAO;

	@Autowired
	private UserAccessDAO userAccessDAO;

	// true: dates whose startsAt has passed are not offered any more (property or env var
	// STUDYAID_WEBCOURSE_HIDEPASTOFFERS). Off by default, as sa-mongo showed every date.
	@Value("${studyaid.webcourse.hide-past-offers:false}")
	private boolean hidePastOffers;

	@Override
	public WebCourse getWebCourseList(String subjectName) {
		WebCourse webCourse = webCourseDAO.getWebCourseBySubjectName(subjectName, hidePastOffers);
		if (webCourse == null) {
			// sa-mongo failed with HTTP 500 for an unknown subject; reply with an empty subject instead
			webCourse = new WebCourse();
			webCourse.setWebCourseName(subjectName);
		}
		return webCourse;
	}

	@Override
	public UserAccessReturnObject addStudentToScheduledCourse(RequestObject request) {

		Long userId = resolveUserId(request.getWebAddStudent());
		if (userId == null) {
			return fail("No User found");
		}
		if (request.getWebAvailableCourseId() == null || request.getWebCourseScheduleId() == null) {
			return fail(WebCourseDAO.MSG_NO_DATE);
		}
		// paid courses are registered by /ui/creditcard/checkout after the payment
		// (sa-mongo registered anyone here, paid or not)
		Integer fee = webCourseDAO.findCourseFee(request.getWebAvailableCourseId());
		if (fee == null) {
			return fail(WebCourseDAO.MSG_NO_DATE);
		}
		if (fee > 0) {
			return fail(WebCourseDAO.MSG_PAY_FIRST);
		}
		return webCourseDAO.registerForOffer(request.getWebSubjectId(),
				request.getWebAvailableCourseId(), request.getWebCourseScheduleId(), userId);
	}

	// userId sent by the Angular app (MySQL login), or looked up by user name
	private Long resolveUserId(Student student) {
		if (student == null) {
			return null;
		}
		if (student.getUserId() != null) {
			return student.getUserId();
		}
		return userAccessDAO.findUserIdByUserName(student.getUserName());
	}

	private static UserAccessReturnObject fail(String message) {
		UserAccessReturnObject result = new UserAccessReturnObject();
		result.setApiResponseStatus(WebCourseDAO.STATUS_FAIL);
		result.setMsgReturned(message);
		return result;
	}
}
