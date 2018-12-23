/****************************************************
 * TokenController.java
 *
 *	Date		Author					Remark
 *	25-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.controller;

import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.helper.AuthHelper;
import com.grahire.challenge.model.PaymentRequest;
import com.grahire.challenge.model.Response;
import com.grahire.challenge.service.TokenService;

/**
 * @author Gurpreet Singh
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TokenController {

	@Autowired
	AuthHelper authHelper;

	@Autowired
	TokenService tokenService;

	@RequestMapping(value = "/user/{profileToken}", method = RequestMethod.POST)
	public ResponseEntity<PaymentRequest> createPaymentReq(@PathVariable("profileToken") Double moneyToken,
			@PathParam("userId") String userId, @PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		PaymentRequest paymentURL = tokenService.createPaymentReq(moneyToken, userId);
		return new ResponseEntity<PaymentRequest>(paymentURL, HttpStatus.OK);
	}

	@RequestMapping(value = "/redeem/{email}", method = RequestMethod.POST)
	public ResponseEntity<Response> redeemToken(@PathVariable("email") String email, @PathParam("userId") String userId,
			@PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		Response response = tokenService.redeemToken(email, userId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/postpay", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<String> verifyPayment(@RequestParam Map<String, String> param) throws AppException {
		tokenService.addTokenToUser(param);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}

}
