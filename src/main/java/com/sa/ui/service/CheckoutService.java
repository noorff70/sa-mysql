package com.sa.ui.service;

import org.springframework.http.ResponseEntity;

import com.sa.ui.model.CreditCardRequestObject;

public interface CheckoutService {

	// charges a card with Stripe (was /mongo/creditcard/checkout)
	public ResponseEntity<?> checkout(CreditCardRequestObject request);
}
