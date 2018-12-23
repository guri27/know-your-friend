/****************************************************
 * UserAnswerEntity.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author gurpr
 *
 */
@Entity
@Table(name = "USER_ANSWER")
public class UserAnswerEntity extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "USER")
	private UserEntity userEntity;

	@ManyToOne
	@JoinColumn(name = "QUESTION")
	private QuestionEntity questionEntity;

	@ManyToOne
	@JoinColumn(name = "OPTION")
	private OptionsEntity optionsEntity;

	/**
	 * @return the userEntity
	 */
	public UserEntity getUserEntity() {
		return userEntity;
	}

	/**
	 * @param userEntity
	 *            the userEntity to set
	 */
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	/**
	 * @return the questionEntity
	 */
	public QuestionEntity getQuestionEntity() {
		return questionEntity;
	}

	/**
	 * @param questionEntity
	 *            the questionEntity to set
	 */
	public void setQuestionEntity(QuestionEntity questionEntity) {
		this.questionEntity = questionEntity;
	}

	/**
	 * @return the optionsEntity
	 */
	public OptionsEntity getOptionsEntity() {
		return optionsEntity;
	}

	/**
	 * @param optionsEntity
	 *            the optionsEntity to set
	 */
	public void setOptionsEntity(OptionsEntity optionsEntity) {
		this.optionsEntity = optionsEntity;
	}

}
