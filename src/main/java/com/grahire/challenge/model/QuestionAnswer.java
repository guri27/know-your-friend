/****************************************************
 * Question.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.model;

import java.util.List;

/**
 * @author gurpr
 *
 */
public class QuestionAnswer {
	
	private String question;
	
	private String questionId;
	
	private List<Option> options;
	
	private String userOption;

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
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
	 * @return the options
	 */
	public List<Option> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<Option> options) {
		this.options = options;
	}

	/**
	 * @return the userOption
	 */
	public String getUserOption() {
		return userOption;
	}

	/**
	 * @param userOption the userOption to set
	 */
	public void setUserOption(String userOption) {
		this.userOption = userOption;
	}
}
