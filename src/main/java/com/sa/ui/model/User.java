package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	Long userId;
	String firstName;
	String lastName;
	String email;
	String userName;
	String password;
	String userType;
}
