package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Reply of a successful charge: { id } as in sa-mongo, plus the registration result when
 * the request named a user and a course date.
 */
@Getter
@Setter
public class CreditCardResponseObject {

	private String id;                  // Stripe charge id
	private String msgReturned;
	private String apiResponseStatus;   // registration result: STATUS_SUCCESS / STATUS_FAIL
}
