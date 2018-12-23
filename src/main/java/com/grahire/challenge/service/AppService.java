/**
 * 
 */
package com.grahire.challenge.service;

import java.util.List;

import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.model.Challenge;
import com.grahire.challenge.model.LoginUser;
import com.grahire.challenge.model.PaidAnswer;
import com.grahire.challenge.model.QuestionAnswer;
import com.grahire.challenge.model.Response;
import com.grahire.challenge.model.User;
import com.grahire.challenge.model.UserChallenge;

/**
 * @author gurpr
 *
 */
public interface AppService {
	
	public Response login(LoginUser userDetails) throws AppException;

	public User getUser(String userId) throws AppException;

	public List<QuestionAnswer> getQuestions(String userId) throws AppException;

	public Response updateAnswers(List<QuestionAnswer> questionAnswers, String userId) throws AppException;

	public Response createChallenge(String userId, String userChallenged, Float bettingAmount) throws AppException;

	public Challenge getChallenge(String username, Long challengeId) throws AppException; 
	
	public Response acceptChallenge(String username, String userId, Long challengeId) throws AppException;

	public List<QuestionAnswer> getChallengeQuestion(Long challengeId) throws AppException;

	public Response submitChallenge(Long challengeId, List<QuestionAnswer> questionAnswers, String userId)
			throws AppException;
	
	public UserChallenge getAllPreviousChallenge(String userId) throws AppException;
	
	public List<PaidAnswer> getPaidAnswers(String userId, Long challengeId, Boolean initiator) throws AppException;

}
