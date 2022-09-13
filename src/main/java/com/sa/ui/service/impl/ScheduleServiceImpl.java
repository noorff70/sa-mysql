package com.sa.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.ScheduleDAO;
import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.ScheduleService;


@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleDAO scheduleDAO;

	@Override
	public UserAccessReturnObject getSchedule(long courseId) {
		
		UserAccessReturnObject accessObject = new UserAccessReturnObject();
		List<ScheduleCourse> ua = new ArrayList<ScheduleCourse>();
		
		ua = scheduleDAO.getAvailableDates(courseId);
		
		accessObject.setScheduleCourse(ua);
		
		return accessObject;
	}

}
