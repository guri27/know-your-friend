/****************************************************
 * Challenge.java
 *
 *	Date		Author					Remark
 *	18-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.model;

/**
 * @author Gurpreet Singh
 *
 */
public class Challenge {
	
	private Long chlId;
	
	private String user;
	
	private String challenge;
	
	private Boolean win;
	
	private Float betAmount;

	/**
	 * @return the chlId
	 */
	public Long getChlId() {
		return chlId;
	}

	/**
	 * @param chlId the chlId to set
	 */
	public void setChlId(Long chlId) {
		this.chlId = chlId;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the challenge
	 */
	public String getChallenge() {
		return challenge;
	}

	/**
	 * @param challenge the challenge to set
	 */
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	/**
	 * @return the win
	 */
	public Boolean getWin() {
		return win;
	}

	/**
	 * @param win the win to set
	 */
	public void setWin(Boolean win) {
		this.win = win;
	}

	/**
	 * @return the betAmount
	 */
	public Float getBetAmount() {
		return betAmount;
	}

	/**
	 * @param betAmount the betAmount to set
	 */
	public void setBetAmount(Float betAmount) {
		this.betAmount = betAmount;
	}

}
