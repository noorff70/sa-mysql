package com.sa.ui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.UserAccessDAO;
import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.UserAccessService;

@Service("accessService")
public class UserAccessServiceImpl implements UserAccessService{
	
	@Autowired
	private UserAccessDAO accessDAO;

	@Override
	public UserAccessReturnObject registerUser(User user) {
		return accessDAO.registerUser(user);
	}

	@Override
	public UserAccessReturnObject loginUser(User user) {
		return accessDAO.loginUser(user);
	}

	



}

