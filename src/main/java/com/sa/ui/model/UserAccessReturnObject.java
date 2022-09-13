package com.sa.ui.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccessReturnObject {
	
	String msgReturned;
	boolean success;
	boolean loginSuccess;
	boolean addContentToUserSuccess;
	Student student;
	Tutor tutor;
	List<ScheduleCourse> scheduleCourse;
}
