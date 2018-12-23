/****************************************************
 *	QuestionEntity.java
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author gurpr
 *
 */
@Entity
@Table(name = "QUESTION")
public class QuestionEntity extends AbstractEntity {
	
	@Column(name = "TEXT")
	private String text;
	
	@Column(name = "QUESTION_ID")
	private String questionId;
	
	@OneToMany(mappedBy = "questionEntity")
	private List<OptionsEntity> optionsEntities;

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the questionId
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the optionsEntities
	 */
	public List<OptionsEntity> getOptionsEntities() {
		return optionsEntities;
	}

	/**
	 * @param optionsEntities the optionsEntities to set
	 */
	public void setOptionsEntities(List<OptionsEntity> optionsEntities) {
		this.optionsEntities = optionsEntities;
	}
	
	

}
