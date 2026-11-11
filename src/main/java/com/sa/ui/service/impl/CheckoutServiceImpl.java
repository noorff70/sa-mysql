package com.sa.ui.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.PaymentDAO;
import com.sa.ui.dao.UserAccessDAO;
import com.sa.ui.dao.WebCourseDAO;
import com.sa.ui.model.CreditCardRequestObject;
import com.sa.ui.model.CreditCardResponseObject;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

/*
 * Stripe card payment. The secret key comes from the environment variable STRIPE_SECRET_KEY
 * (sa-mongo had it in the source code). Without it, checkout answers 503.
 *
 * When the request names the course date (webCourseId + webCourseOfferNumber) and the user,
 * the amount is the course fee from the database (not the price the browser sent), the payment
 * is recorded in the payment table and the user is registered for that date.
 */
@Service("checkoutService")
public class CheckoutServiceImpl implements CheckoutService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Value("${STRIPE_SECRET_KEY:}")
	private String stripeSecretKey;

	@Autowired
	private WebCourseDAO webCourseDAO;

	@Autowired
	private PaymentDAO paymentDAO;

	@Autowired
	private UserAccessDAO userAccessDAO;

	@Override
	public ResponseEntity<?> checkout(CreditCardRequestObject req) {

		if (stripeSecretKey == null || stripeSecretKey.trim().isEmpty()) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Payments are not configured");
		}
		if (req.getToken() == null || req.getToken().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Missing card token");
		}

		Long userId = req.getUserId() != null ? req.getUserId() : userAccessDAO.findUserIdByUserName(req.getUserName());
		boolean forCourseDate = req.getWebCourseId() != null && req.getWebCourseOfferNumber() != null;
		if (forCourseDate && userId == null) {
			return ResponseEntity.badRequest().body("No User found");
		}

		int amount = req.getPrice();
		if (req.getWebCourseId() != null) {
			Integer fee = webCourseDAO.findCourseFee(req.getWebCourseId());
			if (fee == null) {
				return ResponseEntity.badRequest().body("Course not found");
			}
			amount = fee;
		}
		if (amount <= 0) {
			return ResponseEntity.badRequest().body("Nothing to pay");
		}
		String currency = (req.getCurrency() == null || req.getCurrency().trim().isEmpty()) ? "usd" : req.getCurrency().trim();

		Charge charge;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("amount", amount);
			params.put("currency", currency);
			params.put("source", req.getToken());
			charge = Charge.create(params, RequestOptions.builder().setApiKey(stripeSecretKey).build());
		} catch (StripeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		CreditCardResponseObject response = new CreditCardResponseObject();
		response.setId(charge.getId());

		if (userId != null) {
			Integer offerId = forCourseDate ? webCourseDAO.findOfferId(req.getWebCourseId(), req.getWebCourseOfferNumber()) : null;
			try {
				paymentDAO.recordPayment(userId, offerId, charge.getId(), amount, currency);
			} catch (Exception e) {
				// the card is charged; make sure the charge can be found and matched by hand
				LOG.error("Stripe charge " + charge.getId() + " for user " + userId + " (" + amount + " " + currency
						+ ") could not be recorded", e);
			}
		}
		if (forCourseDate) {
			UserAccessReturnObject registration = webCourseDAO.registerForOffer(null,
					req.getWebCourseId(), req.getWebCourseOfferNumber(), userId);
			response.setApiResponseStatus(registration.getApiResponseStatus());
			response.setMsgReturned(registration.getMsgReturned());
			if (!WebCourseDAO.STATUS_SUCCESS.equals(registration.getApiResponseStatus())) {
				LOG.warn("Paid charge " + charge.getId() + " but registration failed: " + registration.getMsgReturned());
			}
		}
		return ResponseEntity.ok(response);
	}
}
