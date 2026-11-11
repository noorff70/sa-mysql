package com.sa.ui.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.PaymentDAO;

@Transactional
@Repository
public class PaymentDAOImpl implements PaymentDAO {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void recordPayment(long userId, Integer offerId, String stripeChargeId, int amount, String currency) {
		jdbcTemplate.update(
				"INSERT INTO payment (userId, offerId, stripeChargeId, amount, currency) VALUES (?, ?, ?, ?, ?)",
				userId, offerId, stripeChargeId, amount, currency == null ? "usd" : currency.toLowerCase());
	}
}
