package com.sa.ui.service;

import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.model.UserAccessReturnObject;

public interface ScheduleService {
	
	public UserAccessReturnObject getSchedule (long courseId) ;
	public String registerSchedule(ScheduleCourse sc) ;

}
