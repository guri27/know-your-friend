/****************************************************
 * BetAnswer.java
 *
 *	Date		Author					Remark
 *	18-Sep-2017 Gurpreet Singh Saini	Initial Version
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
 * @author Gurpreet Singh
 *
 */
@Entity
@Table(name = "BET_ANSWER")
public class BetAnswer extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "USER_CHALLENGE")
	private UserChallengeEntity userChallenge;
	
	@ManyToOne
	@JoinColumn(name = "QUESTION")
	private QuestionEntity question;
	
	@ManyToOne
	@JoinColumn(name = "ANSWER")
	private OptionsEntity answer;
	
	@ManyToOne
	@JoinColumn(name = "CORRECT_ANSWER")
	private OptionsEntity correctAnswer;
	
	/**
	 * @return the correctAnswer
	 */
	public OptionsEntity getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * @param correctAnswer the correctAnswer to set
	 */
	public void setCorrectAnswer(OptionsEntity correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
	 * @return the userChallenge
	 */
	public UserChallengeEntity getUserChallenge() {
		return userChallenge;
	}

	/**
	 * @param userChallenge the userChallenge to set
	 */
	public void setUserChallenge(UserChallengeEntity userChallenge) {
		this.userChallenge = userChallenge;
	}

	/**
	 * @return the question
	 */
	public QuestionEntity getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public OptionsEntity getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(OptionsEntity answer) {
		this.answer = answer;
	}

}
