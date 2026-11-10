package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCourse {

	Long userCourseId;
	Long courseId;
	Long userId;
	String userName;   // sent by the Angular app; used when userId is missing

}
