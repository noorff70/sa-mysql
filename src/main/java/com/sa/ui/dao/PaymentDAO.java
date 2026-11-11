package com.sa.ui.dao;

public interface PaymentDAO {

	// records a Stripe charge; offerId may be null
	public void recordPayment(long userId, Integer offerId, String stripeChargeId, int amount, String currency);
}
