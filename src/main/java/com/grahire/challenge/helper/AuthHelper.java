/****************************************************
 * AuthHelper.java
 *
 *	Date		Author					Remark
 *	22-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import com.grahire.challenge.constant.AppConstant;
import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.util.UserAuthMap;

/**
 * @author Gurpreet Singh
 *
 */
@Component
public class AuthHelper {

	@Autowired
	UserAuthMap userAuthMap;

	public void checkAuth(String userId, String token) throws AppException {
		String mapToken = userAuthMap.getToken(userId);
		if (mapToken == null) {
			throw new AppException(AppConstant.ERR_USR_003.getStatusCode(), AppConstant.ERR_USR_003.getStatusMessage());
		}
		if (!mapToken.equalsIgnoreCase(token)) {
			Facebook facebook = new FacebookTemplate(token);
			String[] fields = { "id" };
			try {
				org.springframework.social.facebook.api.User profile = facebook.fetchObject("me",
						org.springframework.social.facebook.api.User.class, fields);
				if (!profile.getId().equalsIgnoreCase(userId)) {
					throw new AppException(AppConstant.ERR_USR_003.getStatusCode(),
							AppConstant.ERR_USR_003.getStatusMessage());
				} else {
					userAuthMap.addToken(userId, token);
				}
			} catch (Exception e) {
				throw new AppException(AppConstant.ERR_USR_003.getStatusCode(),
						AppConstant.ERR_USR_003.getStatusMessage());
			}

		}
	}
}
