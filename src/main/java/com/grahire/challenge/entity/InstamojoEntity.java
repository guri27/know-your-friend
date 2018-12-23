/****************************************************
 * InstamojoTrans.java
 *
 *	Date		Author					Remark
 *	03-Dec-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Gurpreet Singh
 *
 */
@Entity
@Table(name = "INSTAMOJO_TRANS")
public class InstamojoEntity extends AbstractEntity {
	
	@Column(name = "INSTA_ID")
	private String instaId;
	
	@Column(name = "TRANS_ID")
	private String transId;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "PAYMENT_ID")
	private String paymentId;
	
	@Column(name = "STATUS")
	private String status;

	/**
	 * @return the instaId
	 */
	public String getInstaId() {
		return instaId;
	}

	/**
	 * @param instaId the instaId to set
	 */
	public void setInstaId(String instaId) {
		this.instaId = instaId;
	}

	/**
	 * @return the transId
	 */
	public String getTransId() {
		return transId;
	}

	/**
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
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
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
