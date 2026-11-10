package com.sa.ui.dao;

import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;

public interface UserAccessDAO {
	
	public UserAccessReturnObject registerUser(User user);
	public UserAccessReturnObject loginUser(User user);
	// userId for a user name, or null when there is no such user
	public Long findUserIdByUserName(String userName);

}
