package com.sa.ui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.ScheduleDAO;
import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.model.ScheduleUser;
import com.sa.ui.model.Tutor;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.ScheduleService;


@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleDAO scheduleDAO;

	@Override
	public UserAccessReturnObject getSchedule(long courseId) {
		
		UserAccessReturnObject accessObject = new UserAccessReturnObject();
		List<ScheduleCourse> scheduelCourses = new ArrayList<ScheduleCourse>();
		
		scheduelCourses = scheduleDAO.getAvailableDates(courseId);
		
		Tutor tutor = new Tutor();
		if (scheduelCourses != null) {
			tutor.setFirstName(scheduelCourses.get(0).getTutorFName());
			tutor.setLastName(scheduelCourses.get(0).getTutorLName());
		}
		accessObject.setTutor(tutor);
		accessObject.setScheduleCourse(scheduelCourses);
		
		return accessObject;
	}

	@Override
	public String registerSchedule(ScheduleCourse sc) {
		
		ScheduleUser su = new ScheduleUser();
		su.setScheduleId(sc.getScheduleId());
		su.setUserId(sc.getUserId());
		
		int returnVal = scheduleDAO.registerSchedule(su);
		
		if (returnVal == 0)
			return "Please contact Admin- Registration Failure";
		else 
			return "Successfully registered for Event. Wait for a confirmation Result.";
	}

}
