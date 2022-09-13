package com.sa.ui.dao;


import java.util.List;

import com.sa.ui.model.ScheduleCourse;


public interface ScheduleDAO {
	
	public List<ScheduleCourse> getAvailableDates(long courseId);

}
