package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Reply object of most endpoints. Same field names as sa-mongo's APIResponseObject,
 * which the Angular app reads.
 */
@Getter
@Setter
public class UserAccessReturnObject {

	String msgReturned;
	String apiResponseStatus;   // STATUS_SUCCESS / STATUS_FAIL (instructor-led registration)
	boolean success;
	boolean loginSuccess;
	boolean addContentToUserSuccess;
	Student student;
	Tutor tutor;
}
