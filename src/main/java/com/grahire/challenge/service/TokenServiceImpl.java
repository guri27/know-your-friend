/****************************************************
 * TokenServiceImpl.java
 *
 *	Date		Author					Remark
 *	25-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.service;

import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grahire.challenge.constant.AppConstant;
import com.grahire.challenge.entity.InstamojoEntity;
import com.grahire.challenge.entity.RedeemEntity;
import com.grahire.challenge.entity.UserEntity;
import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.model.PaymentRequest;
import com.grahire.challenge.model.Response;
import com.grahire.challenge.repository.InstamojoRepository;
import com.grahire.challenge.repository.RedeemRepository;
import com.grahire.challenge.repository.UserRepository;
import com.grahire.challenge.util.InstaMojoUtil;
import com.grahire.challenge.util.TokenMap;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RedeemRepository redeemRepo;

	@Autowired
	InstaMojoUtil instaMojoUtil;

	@Autowired
	InstamojoRepository instamojoRepo;

	@Autowired
	TokenMap tokenMap;

	private static Logger logger = LoggerFactory.getLogger(TokenService.class);

	@Override
	public PaymentRequest createPaymentReq(Double token, String userId) throws AppException {
		logger.debug("Add tokens: {} to user: {}", token, userId);
		PaymentRequest paymentReq = new PaymentRequest();
		try {
			UserEntity userEntity = userRepo.findByUserId(userId);
			String paymentURL = instaMojoUtil.createPaymentURL(userId, userEntity.getUsername(), userEntity.getEmail(),
					token);
			paymentReq.setPaymentURL(paymentURL);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return paymentReq;
	}

	@Override
	public void addTokenToUser(Map<String, String> param) throws AppException {
		String status = param.get("status");
		String id = param.get("id");
		String paymentId = param.get("payment_id");
		Float amount = Float.parseFloat(param.get("amount"));

		String userId = tokenMap.getTokenMap().get(id);
		if (userId != null) {
			InstamojoEntity instamojoEntity = instamojoRepo.findByInstaId(id);
			instamojoEntity.setPaymentId(paymentId);

			if (status.equalsIgnoreCase("successful")) {
				UserEntity userEntity = userRepo.findByUserId(userId);
				userEntity.setAvailable(userEntity.getAvailable() + amount);
				userRepo.save(userEntity);
				instamojoEntity.setStatus("successful");
			} else {
				instamojoEntity.setStatus("unsuccessful");
			}

			instamojoRepo.save(instamojoEntity);
			// Remove from map
			tokenMap.getTokenMap().remove(id);

		}
	}

	@Override
	public Response redeemToken(String email, String userId) throws AppException {
		logger.debug("Redeem token for userId: {} and email: {}",userId,email);
		Response response = new Response();
		UserEntity userEntity = userRepo.findByUserId(userId);
		if (userEntity.getAvailable() < 200) {
			logger.error("Available token are less for redemption for user: {}",userId);
			throw new AppException(AppConstant.ERR_PAY_002.getStatusCode(), AppConstant.ERR_PAY_002.getStatusMessage());
		}

		RedeemEntity redeemEntity = new RedeemEntity();
		redeemEntity.setEmail(email);
		redeemEntity.setRedeemTime(new Date());
		redeemEntity.setAmount(userEntity.getAvailable());
		redeemEntity.setUserEntity(userEntity);
		redeemRepo.save(redeemEntity);
		
		userEntity.setAvailable(0f);
		userRepo.save(userEntity);
		return response;
	}

}
