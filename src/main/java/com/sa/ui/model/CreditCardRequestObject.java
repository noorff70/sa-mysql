package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Body of /ui/creditcard/checkout. token/price/currency/description are what the Angular
 * checkout page sends today (same as sa-mongo). The other fields are optional: when the
 * user and the course date are sent, the price is taken from the database, the payment is
 * recorded and the user is registered for that date.
 */
@Getter
@Setter
public class CreditCardRequestObject {

	private String token;           // Stripe card token
	private int price;              // used only when webCourseId is not sent
	private String currency;
	private String description;

	private Long userId;
	private String userName;
	private Integer webCourseId;
	private Integer webCourseOfferNumber;
}
