/****************************************************
 * InstaMojoService.java
 *
 *	Date		Author					Remark
 *	03-Dec-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.util;

import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.grahire.challenge.constant.AppConstant;
import com.grahire.challenge.entity.InstamojoEntity;
import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.repository.InstamojoRepository;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;

/**
 * @author Gurpreet Singh
 *
 */
@Component
@Transactional
public class InstaMojoUtil {
	
	@Autowired
	TokenMap tokenMap;
	
	@Autowired
	InstamojoRepository instamojoRepo;

	private static Logger logger = LoggerFactory.getLogger(Instamojo.class);

	@Value("${instamojo.clientId}")
	private String clientId;

	@Value("${instamojo.clientSecret}")
	private String clientSecret;

	@Value("${instamojo.apiEndPoint}")
	private String apiEndPoint;

	@Value("${instamojo.authEndPoint}")
	private String authEndPoint;

	/**
	 * This method creates payment URL upon which payment to be made
	 * @param userName
	 * @param amount
	 * @return
	 * @throws AppException
	 */
	public String createPaymentURL(String userId, String userName, String email, Double amount) throws AppException {
		logger.debug("Going to create payment request");
		
		String paymentURL = null;
		
		PaymentOrder order = new PaymentOrder();
		order.setName(userName);
		if(email == null) {
			order.setEmail("test@test.com");
		}else {
			order.setEmail(email);
		}
		order.setPhone("9999999999");
		order.setCurrency("INR");
		order.setAmount(amount);
		order.setDescription("Add tokens to Know Your Friend");
		order.setRedirectUrl("https://knowyourfriend.online/redirect/payment.html");
		order.setWebhookUrl("https://knowyourfriend.online/api/postpay");
		order.setTransactionId(UUID.randomUUID().toString());
		try {
			Instamojo api = InstamojoImpl.getApi(clientId, clientSecret, apiEndPoint, authEndPoint);
			CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
			paymentURL = createPaymentOrderResponse.getPaymentOptions().getPaymentUrl();
			
			//Add to token map for postpay verification
			tokenMap.addToMap(createPaymentOrderResponse.getPaymentOrder().getId(), userId);
			
			//Track
			InstamojoEntity instamojoEntity = new InstamojoEntity();
			instamojoEntity.setInstaId(createPaymentOrderResponse.getPaymentOrder().getId());
			instamojoEntity.setTransId(createPaymentOrderResponse.getPaymentOrder().getTransactionId());
			instamojoEntity.setUserId(userId);
			instamojoEntity.setStatus("pending");
			instamojoRepo.save(instamojoEntity);
			
		} catch (InvalidPaymentOrderException e) {
			logger.error("Invalid payment order", e);
			throw new AppException(AppConstant.ERR_PAY_001.getStatusCode(), AppConstant.ERR_PAY_001.getStatusMessage());
		} catch (ConnectionException e) {
			logger.error("Error while connecting", e);
			throw new AppException(AppConstant.ERR_PAY_001.getStatusCode(), AppConstant.ERR_PAY_001.getStatusMessage());
		}
		return paymentURL;
	}
}
