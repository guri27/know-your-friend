/****************************************************
 * TokenService.java
 *
 *	Date		Author					Remark
 *	25-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.service;

import java.util.Map;

import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.model.PaymentRequest;
import com.grahire.challenge.model.Response;

/**
 * @author Gurpreet Singh
 *
 */
public interface TokenService {
	
	public PaymentRequest createPaymentReq(Double token, String userId) throws AppException;

	public void addTokenToUser(Map<String, String> param) throws AppException;
	
	public Response redeemToken(String email, String userId) throws AppException;
	
}
