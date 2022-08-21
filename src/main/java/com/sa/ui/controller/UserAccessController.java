package com.sa.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sa.ui.model.User;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.UserAccessService;

@RestController
public class UserAccessController {
	
	@Autowired
	UserAccessService accessService;
	
	@PostMapping("/ui/register/user")
	public UserAccessReturnObject registerUser(@RequestBody User user) {
		
		UserAccessReturnObject returnObject = accessService.registerUser(user);
		return returnObject;
		
	}
	
	@PostMapping ("/ui/login/user")
	public UserAccessReturnObject loginUser(@RequestBody User user) {
	
		UserAccessReturnObject returnObject = accessService.loginUser(user);
		return returnObject;
	}

}
