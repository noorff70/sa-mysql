package com.sa.ui.service;

import com.sa.ui.model.Tutor;
import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;

public interface UserAccessService {
	
	public UserAccessReturnObject registerUser (User user);
	public UserAccessReturnObject loginUser (User user);
	// tutor profile by tutorKey (was /mongo/findTutorByTutorId); never null
	public Tutor findTutorByTutorId (String tutorKey);
}
