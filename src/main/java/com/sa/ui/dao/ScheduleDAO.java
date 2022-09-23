package com.sa.ui.dao;


import java.util.List;

import com.sa.ui.model.ScheduleCourse;
import com.sa.ui.model.ScheduleUser;


public interface ScheduleDAO {
	
	public List<ScheduleCourse> getAvailableDates(long courseId);
	public int registerSchedule(ScheduleUser su) ;

}
