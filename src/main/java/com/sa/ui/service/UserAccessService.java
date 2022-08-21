package com.sa.ui.service;

import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;

public interface UserAccessService {
	
	public UserAccessReturnObject registerUser (User user);
	public UserAccessReturnObject loginUser (User user);
}
