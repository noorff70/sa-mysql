package com.sa.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sa.ui.model.Course;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.ScheduleService;

@RestController
public class ScheduleController {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/ui/getSchedule")
	public UserAccessReturnObject getScheduleList (@RequestBody Course course) {
		
		UserAccessReturnObject userAccess = scheduleService.getSchedule(course.getCourseId());
		
		return userAccess;
	}

}
