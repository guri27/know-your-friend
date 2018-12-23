/****************************************************
 * OptionsEntity.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author gurpr
 *
 */
@Entity
@Table(name = "OPTIONS")
public class OptionsEntity extends AbstractEntity {
	
	@Column(name = "OPTION")
	private String option;
	
	@Column(name = "OPTION_ID")
	private String optionId;
	
	@ManyToOne
	@JoinColumn(name = "QUESTION")
	private QuestionEntity questionEntity;

	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * @return the optionId
	 */
	public String getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId the optionId to set
	 */
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	/**
	 * @return the questionEntity
	 */
	public QuestionEntity getQuestionEntity() {
		return questionEntity;
	}

	/**
	 * @param questionEntity the questionEntity to set
	 */
	public void setQuestionEntity(QuestionEntity questionEntity) {
		this.questionEntity = questionEntity;
	}

}
