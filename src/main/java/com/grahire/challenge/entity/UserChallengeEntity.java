/****************************************************
 * UserChallengeEntity.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author gurpr
 *
 */
@Entity
@Table(name = "USER_CHALLENGE")
public class UserChallengeEntity extends AbstractEntity {
	
	@Column(name = "BETTING_AMOUNT")
	private Float bettingAmount;
	
	@Column(name = "WIN", nullable = true)
	private Boolean win;
	
	@ManyToOne
	@JoinColumn(name = "USER")
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "USER_CHALLENGED", nullable = true)
	private UserEntity userChallenged;
	
	@OneToMany(mappedBy = "userChallenge")
	private List<BetAnswer> betAnswers;

	/**
	 * @return the betAnswers
	 */
	public List<BetAnswer> getBetAnswers() {
		return betAnswers;
	}

	/**
	 * @param betAnswers the betAnswers to set
	 */
	public void setBetAnswers(List<BetAnswer> betAnswers) {
		this.betAnswers = betAnswers;
	}

	/**
	 * @return the bettingAmount
	 */
	public Float getBettingAmount() {
		return bettingAmount;
	}

	/**
	 * @param bettingAmount the bettingAmount to set
	 */
	public void setBettingAmount(Float bettingAmount) {
		this.bettingAmount = bettingAmount;
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
	 * @return the userEntity
	 */
	public UserEntity getUserEntity() {
		return userEntity;
	}

	/**
	 * @param userEntity the userEntity to set
	 */
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	/**
	 * @return the userChallenged
	 */
	public UserEntity getUserChallenged() {
		return userChallenged;
	}

	/**
	 * @param userChallenged the userChallenged to set
	 */
	public void setUserChallenged(UserEntity userChallenged) {
		this.userChallenged = userChallenged;
	}

}
