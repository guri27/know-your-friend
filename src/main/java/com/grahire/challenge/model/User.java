/**
 * 
 */
package com.grahire.challenge.model;

/**
 * @author gurpr
 *
 */
public class User {
	
	private String username;
	
	private Boolean trial;
	
	private Float available;
	
	private Float spent;
	
	private Float win;
	
	private Float loss;
	
	private Integer awaitingChl;
	
	private Integer winChl;
	
	private Integer lossChl;

	/**
	 * @return the trial
	 */
	public Boolean getTrial() {
		return trial;
	}

	/**
	 * @param trial the trial to set
	 */
	public void setTrial(Boolean trial) {
		this.trial = trial;
	}

	/**
	 * @return the available
	 */
	public Float getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(Float available) {
		this.available = available;
	}

	/**
	 * @return the spent
	 */
	public Float getSpent() {
		return spent;
	}

	/**
	 * @param spent the spent to set
	 */
	public void setSpent(Float spent) {
		this.spent = spent;
	}

	/**
	 * @return the win
	 */
	public Float getWin() {
		return win;
	}

	/**
	 * @param win the win to set
	 */
	public void setWin(Float win) {
		this.win = win;
	}

	/**
	 * @return the loss
	 */
	public Float getLoss() {
		return loss;
	}

	/**
	 * @param loss the loss to set
	 */
	public void setLoss(Float loss) {
		this.loss = loss;
	}

	/**
	 * @return the awaitingChl
	 */
	public Integer getAwaitingChl() {
		return awaitingChl;
	}

	/**
	 * @param awaitingChl the awaitingChl to set
	 */
	public void setAwaitingChl(Integer awaitingChl) {
		this.awaitingChl = awaitingChl;
	}

	/**
	 * @return the winChl
	 */
	public Integer getWinChl() {
		return winChl;
	}

	/**
	 * @param winChl the winChl to set
	 */
	public void setWinChl(Integer winChl) {
		this.winChl = winChl;
	}

	/**
	 * @return the lossChl
	 */
	public Integer getLossChl() {
		return lossChl;
	}

	/**
	 * @param lossChl the lossChl to set
	 */
	public void setLossChl(Integer lossChl) {
		this.lossChl = lossChl;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
