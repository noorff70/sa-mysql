package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Tutor profile, same JSON as sa-mongo's Tutor: userName is the tutorKey ('faizulnoor'),
 * plus firstName, lastName, email, tutorBio and imageLocation. No password is ever set.
 */
@Getter
@Setter
public class Tutor extends User{

	private String tutorBio;
	private String imageLocation;
}
