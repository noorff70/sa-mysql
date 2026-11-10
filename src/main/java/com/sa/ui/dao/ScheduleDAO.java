package com.sa.ui.dao;


import java.util.List;

import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.model.ScheduleUser;


public interface ScheduleDAO {

	// results of registerSchedule
	int FAILED = 0;
	int REGISTERED = 1;
	int ALREADY_REGISTERED = 2;

	public List<ScheduleCourse> getAvailableDates(long courseId);
	public int registerSchedule(ScheduleUser su) ;

}
