/**
 * 
 */
package com.grahire.challenge.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author gurpr
 *
 */
@Entity
@Table(name = "USER")
public class UserEntity extends AbstractEntity {
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "TRIAL")
	private Boolean trial;
	
	@Column(name = "AVAILABLE")
	private Float available;
	
	@Column(name = "SPENT")
	private Float spent;
	
	@Column(name = "WIN")
	private Float win;
	
	@Column(name = "LOSS")
	private Float loss;
	
	@Column(name = "AWAITING_CHL")
	private Integer awaitingChl;
	
	@Column(name = "WIN_CHL")
	private Integer winChl;
	
	@Column(name = "LOSS_CHL")
	private Integer lossChl;
	
	@Column(name = "EMAIL")
	private String email;
	
	@OneToMany(mappedBy = "userEntity")
	private List<UserAnswerEntity> userAnswerEntities;
	
	@OneToMany(mappedBy = "userEntity")
	private List<UserChallengeEntity> userChallengeEntities;
	
	@OneToMany(mappedBy = "userChallenged")
	private List<UserChallengeEntity> userChallengedEntities;
	
	/**
	 * @return the userChallengedEntities
	 */
	public List<UserChallengeEntity> getUserChallengedEntities() {
		return userChallengedEntities;
	}

	/**
	 * @param userChallengedEntities the userChallengedEntities to set
	 */
	public void setUserChallengedEntities(List<UserChallengeEntity> userChallengedEntities) {
		this.userChallengedEntities = userChallengedEntities;
	}

	/**
	 * @return the userChallengeEntities
	 */
	public List<UserChallengeEntity> getUserChallengeEntities() {
		return userChallengeEntities;
	}

	/**
	 * @param userChallengeEntities the userChallengeEntities to set
	 */
	public void setUserChallengeEntities(List<UserChallengeEntity> userChallengeEntities) {
		this.userChallengeEntities = userChallengeEntities;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

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
	 * @return the userAnswerEntities
	 */
	public List<UserAnswerEntity> getUserAnswerEntities() {
		return userAnswerEntities;
	}

	/**
	 * @param userAnswerEntities the userAnswerEntities to set
	 */
	public void setUserAnswerEntities(List<UserAnswerEntity> userAnswerEntities) {
		this.userAnswerEntities = userAnswerEntities;
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
