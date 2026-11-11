package com.sa.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sa.ui.model.CreditCardRequestObject;
import com.sa.ui.model.RequestObject;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.model.WebCourse;
import com.sa.ui.service.CheckoutService;
import com.sa.ui.service.WebCourseService;

/*
 * Instructor-led courses and card payment. Same request and reply JSON as the sa-mongo
 * endpoints with the same names under /mongo/.
 */
@RestController
public class WebCourseController {

	@Autowired
	private WebCourseService webCourseService;

	@Autowired
	private CheckoutService checkoutService;

	@PostMapping("/ui/getWebCourseList")
	public WebCourse getWebCourseList(@RequestBody RequestObject request) {
		return webCourseService.getWebCourseList(request.getWebCourseSearchCriteria());
	}

	@PostMapping("/ui/addStudentToScheduledCourse")
	public UserAccessReturnObject addStudentToScheduledCourse(@RequestBody RequestObject request) {
		return webCourseService.addStudentToScheduledCourse(request);
	}

	@PostMapping("/ui/creditcard/checkout")
	public ResponseEntity<?> checkout(@RequestBody CreditCardRequestObject request) {
		return checkoutService.checkout(request);
	}
}
