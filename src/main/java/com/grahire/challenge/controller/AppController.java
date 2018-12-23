/**
 * 
 */
package com.grahire.challenge.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.helper.AuthHelper;
import com.grahire.challenge.model.Challenge;
import com.grahire.challenge.model.LoginUser;
import com.grahire.challenge.model.PaidAnswer;
import com.grahire.challenge.model.QuestionAnswer;
import com.grahire.challenge.model.Response;
import com.grahire.challenge.model.User;
import com.grahire.challenge.model.UserChallenge;
import com.grahire.challenge.service.AppService;

/**
 * @author gurpr
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AppController {

	@Autowired
	AppService appService;

	@Autowired
	AuthHelper authHelper;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody @Valid LoginUser userDetails) throws AppException {
		Response response = appService.login(userDetails);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathParam("userId") String userId, @PathParam("token") String token)
			throws AppException {
		authHelper.checkAuth(userId, token);
		User user = appService.getUser(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionAnswer>> getQuestions(@PathParam("userId") String userId,
			@PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		List<QuestionAnswer> questions = appService.getQuestions(userId);
		return new ResponseEntity<List<QuestionAnswer>>(questions, HttpStatus.OK);
	}

	@RequestMapping(value = "/question", method = RequestMethod.POST)
	public ResponseEntity<Response> updateQuestionAnswer(@RequestBody List<QuestionAnswer> questionAnswers,
			@PathParam("userId") String userId, @PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		Response res = appService.updateAnswers(questionAnswers, userId);
		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/challenge", method = RequestMethod.POST)
	public ResponseEntity<Response> createChallenge(@PathParam("challengedId") String challengedId,
			@PathParam("bettingAmount") Float bettingAmount, @PathParam("userId") String userId,
			@PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		Response res = appService.createChallenge(userId, challengedId, bettingAmount);
		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/challenge/{challengeId}", method = RequestMethod.GET)
	public ResponseEntity<Challenge> getChallenge(@PathVariable("challengeId") Long challengeId,
			@PathParam("userId") String userId, @PathParam("token") String token,
			@PathParam("username") String username) throws AppException {
		authHelper.checkAuth(userId, token);
		Challenge response = appService.getChallenge(userId, challengeId);
		return new ResponseEntity<Challenge>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/challenge/{challengeId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> acceptBet(@PathVariable("challengeId") Long challengeId,
			@PathParam("userId") String userId, @PathParam("token") String token,
			@PathParam("username") String username) throws AppException {
		authHelper.checkAuth(userId, token);
		Response response = appService.acceptChallenge(username, userId, challengeId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/challenge/{challengeId}/bet", method = RequestMethod.GET)
	public ResponseEntity<List<QuestionAnswer>> getChallengeQuestions(@PathVariable("challengeId") Long challengeId,
			@PathParam("userId") String userId, @PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		List<QuestionAnswer> questions = appService.getChallengeQuestion(challengeId);
		return new ResponseEntity<List<QuestionAnswer>>(questions, HttpStatus.OK);
	}

	@RequestMapping(value = "/challenge/{challengeId}", method = RequestMethod.POST)
	public ResponseEntity<Response> submitChallenge(@PathVariable("challengeId") Long challengeId,
			@RequestBody List<QuestionAnswer> questionAnswers, @PathParam("userId") String userId,
			@PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		Response response = appService.submitChallenge(challengeId, questionAnswers, userId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/challenge", method = RequestMethod.GET)
	public ResponseEntity<UserChallenge> getAllPreviousChallenges(@PathParam("userId") String userId,
			@PathParam("token") String token) throws AppException {
		authHelper.checkAuth(userId, token);
		UserChallenge response = appService.getAllPreviousChallenge(userId);
		return new ResponseEntity<UserChallenge>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/challenge/{challengeId}/paid", method = RequestMethod.GET)
	public ResponseEntity<List<PaidAnswer>> getPaidAnswer(@PathVariable("challengeId") Long challengeId,
			@PathParam("userId") String userId, @PathParam("token") String token, @PathParam("init") Boolean init)
			throws AppException {
		authHelper.checkAuth(userId, token);
		List<PaidAnswer> paidAnswers = appService.getPaidAnswers(userId, challengeId, init);
		return new ResponseEntity<List<PaidAnswer>>(paidAnswers, HttpStatus.OK);
	}

}
