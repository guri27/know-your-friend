/****************************************************
 * RedeemEntity.java
 *
 *	Date		Author					Remark
 *	08-Dec-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Gurpreet Singh
 *
 */
@Entity
@Table(name = "redeem")
public class RedeemEntity extends AbstractEntity {
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "amount")
	private Float amount;
	
	@Column(name = "redeem_time")
	private Date redeemTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

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

	/**
	 * @return the amount
	 */
	public Float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Float amount) {
		this.amount = amount;
	}

	/**
	 * @return the redeemTime
	 */
	public Date getRedeemTime() {
		return redeemTime;
	}

	/**
	 * @param redeemTime the redeemTime to set
	 */
	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
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

}
