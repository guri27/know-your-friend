/****************************************************
 * UserChallenge.java
 *
 *	Date		Author					Remark
 *	18-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.model;

import java.util.List;

/**
 * @author Gurpreet Singh
 *
 */
public class UserChallenge {
	
	private List<Challenge> userChallenge;
	
	private List<Challenge> userChallenged;

	/**
	 * @return the userChallenge
	 */
	public List<Challenge> getUserChallenge() {
		return userChallenge;
	}

	/**
	 * @param userChallenge the userChallenge to set
	 */
	public void setUserChallenge(List<Challenge> userChallenge) {
		this.userChallenge = userChallenge;
	}

	/**
	 * @return the userChallenged
	 */
	public List<Challenge> getUserChallenged() {
		return userChallenged;
	}

	/**
	 * @param userChallenged the userChallenged to set
	 */
	public void setUserChallenged(List<Challenge> userChallenged) {
		this.userChallenged = userChallenged;
	}

}
