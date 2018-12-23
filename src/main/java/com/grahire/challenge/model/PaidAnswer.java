/****************************************************
 * PaidAnswer.java
 *
 *	Date		Author					Remark
 *	23-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.model;

/**
 * @author Gurpreet Singh
 *
 */
public class PaidAnswer {
	
	private String question;
	
	private String userAnswer;
	
	private String challengedAnswer;
	
	private Boolean correct;

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
	 * @return the userAnswer
	 */
	public String getUserAnswer() {
		return userAnswer;
	}

	/**
	 * @param userAnswer the userAnswer to set
	 */
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	/**
	 * @return the challengedAnswer
	 */
	public String getChallengedAnswer() {
		return challengedAnswer;
	}

	/**
	 * @param challengedAnswer the challengedAnswer to set
	 */
	public void setChallengedAnswer(String challengedAnswer) {
		this.challengedAnswer = challengedAnswer;
	}

	/**
	 * @return the correct
	 */
	public Boolean getCorrect() {
		return correct;
	}

	/**
	 * @param correct the correct to set
	 */
	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

}
