/****************************************************
 * BettingHelper.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.helper;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.grahire.challenge.constant.AppConstant;
import com.grahire.challenge.entity.UserChallengeEntity;
import com.grahire.challenge.entity.UserEntity;
import com.grahire.challenge.exception.AppException;

/**
 * @author gurpr
 *
 */
@Component
public class AppHelper {

	private static Logger logger = LoggerFactory.getLogger(AppHelper.class);

	public UserChallengeEntity getUserChlEntity(UserEntity user, UserEntity challengedUser, Float bettingAmount)
			throws AppException {
		UserChallengeEntity userChlEntity = new UserChallengeEntity();
		if (bettingAmount == null || bettingAmount == 0) {
			logger.error("Betting amount cannot be zero");
			throw new AppException(AppConstant.ERR_CHL_002.getStatusCode(), AppConstant.ERR_CHL_002.getStatusMessage());
		} else if (user.getAvailable() < bettingAmount) {
			logger.error("User doesn't have enough tokens");
			throw new AppException(AppConstant.ERR_CHL_003.getStatusCode(), AppConstant.ERR_CHL_003.getStatusMessage());
		} else {
			userChlEntity.setBettingAmount(bettingAmount);
			userChlEntity.setUserEntity(user);
			userChlEntity.setUserChallenged(challengedUser);
			// User
			user.setAvailable(user.getAvailable() - bettingAmount);
			user.setAwaitingChl(user.getAwaitingChl() + 1);
		}
		return userChlEntity;
	}

}
