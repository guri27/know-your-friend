/**
 * 
 */
package com.grahire.challenge.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import com.grahire.challenge.constant.AppConstant;
import com.grahire.challenge.entity.BetAnswer;
import com.grahire.challenge.entity.OptionsEntity;
import com.grahire.challenge.entity.QuestionEntity;
import com.grahire.challenge.entity.UserAnswerEntity;
import com.grahire.challenge.entity.UserChallengeEntity;
import com.grahire.challenge.entity.UserEntity;
import com.grahire.challenge.exception.AppException;
import com.grahire.challenge.helper.AppHelper;
import com.grahire.challenge.model.Challenge;
import com.grahire.challenge.model.LoginUser;
import com.grahire.challenge.model.Option;
import com.grahire.challenge.model.PaidAnswer;
import com.grahire.challenge.model.QuestionAnswer;
import com.grahire.challenge.model.Response;
import com.grahire.challenge.model.User;
import com.grahire.challenge.model.UserChallenge;
import com.grahire.challenge.repository.BetAnswerRepo;
import com.grahire.challenge.repository.QuestionRepository;
import com.grahire.challenge.repository.UserAnswerRepository;
import com.grahire.challenge.repository.UserChallengeRepository;
import com.grahire.challenge.repository.UserRepository;
import com.grahire.challenge.util.UserAuthMap;;

/**
 * @author gurpr
 *
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {

	private static Logger logger = LoggerFactory.getLogger(AppService.class);
	private static final String RES_ERR = "Error";
	private static final String RES_SUCCESS = "Success";
	private static final int WIN_THRESHOLD = 8;
	private static final Float PAID_ANSWER_RATE = 5f;
	private static final int MIN_ANSWER = 15;
	private static final long CHL_QUE_LIMIT = 10;

	@Autowired
	UserRepository userRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	UserAnswerRepository userAnswerRepo;

	@Autowired
	UserChallengeRepository userChallengeRepo;

	@Autowired
	BetAnswerRepo betAnswerRepo;

	@Autowired
	AppHelper bettingHelper;

	@Autowired
	UserAuthMap userAuthMap;

	@Override
	public Response login(LoginUser userDetails) throws AppException {
		Response response = new Response();
		try {
			if (userAuthMap.getToken(userDetails.getUserId()) != null) {
				userAuthMap.addToken(userDetails.getUserId(), userDetails.getToken());
				response.setResponseCode(AppConstant.SUC_USR_001.getStatusCode());
				response.setResponseMessage(AppConstant.SUC_USR_001.getStatusMessage());
			} else {
				Facebook facebook = new FacebookTemplate(userDetails.getToken());
				String[] fields = { "id", "email" };
				org.springframework.social.facebook.api.User profile = facebook.fetchObject("me",
						org.springframework.social.facebook.api.User.class, fields);
				if (profile.getId().equalsIgnoreCase(userDetails.getUserId())) {
					createUser(userDetails.getUserId(), userDetails.getUsername(), profile.getEmail());
					userAuthMap.addToken(userDetails.getUserId(), userDetails.getToken());
					response.setResponseCode(AppConstant.SUC_USR_002.getStatusCode());
					response.setResponseMessage(AppConstant.SUC_USR_002.getStatusMessage());
				} else {
					throw new AppException(AppConstant.ERR_USR_003.getStatusCode(),
							AppConstant.ERR_USR_003.getStatusMessage());
				}
			}
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return response;
	}

	@Override
	public User getUser(String userId) throws AppException {
		logger.debug("Get user details for: {}", userId);
		User user = new User();
		try {
			UserEntity userEntity = userRepo.findByUserId(userId);
			if (userEntity == null) {
				logger.error("No such user: {}", userId);
				throw new AppException(AppConstant.ERR_USR_001.getStatusCode(),
						AppConstant.ERR_USR_001.getStatusMessage());
			}
			BeanUtils.copyProperties(userEntity, user);
			logger.debug("Returning user details for: {}", userId);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return user;
	}

	@Override
	public List<QuestionAnswer> getQuestions(String userId) throws AppException {
		logger.debug("Get all questions");
		List<QuestionAnswer> questions = new ArrayList<>();
		try {
			// Get previous answers
			UserEntity user = userRepo.findByUserId(userId);
			List<UserAnswerEntity> userAnswerEntities = user.getUserAnswerEntities();

			List<QuestionEntity> questionEntities = questionRepo.findAll();
			if (questionEntities == null || questionEntities.isEmpty()) {
				logger.error("No questions found");
				throw new AppException(AppConstant.ERR_USR_001.getStatusCode(),
						AppConstant.ERR_USR_001.getStatusMessage());
			}
			Collections.shuffle(questionEntities);

			questionEntities.forEach(que -> {
				QuestionAnswer question = new QuestionAnswer();
				question.setQuestion(que.getText());
				question.setQuestionId(que.getQuestionId());
				question.setUserOption(getOptionValue(que.getQuestionId(), userAnswerEntities));
				List<Option> options = new ArrayList<>();
				que.getOptionsEntities().forEach(opt -> {
					Option option = new Option();
					option.setOption(opt.getOption());
					option.setOptionId(opt.getOptionId());
					options.add(option);
				});
				question.setOptions(options);
				questions.add(question);
			});
			logger.debug("Returning questions");
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return questions;
	}

	@Override
	public Response updateAnswers(List<QuestionAnswer> questionAnswers, String userId) throws AppException {
		Response response = new Response();
		response.setResponseCode(RES_ERR);
		try {
			UserEntity user = userRepo.findByUserId(userId);
			List<QuestionEntity> questionEntities = questionRepo.findAll();
			List<UserAnswerEntity> userAnswerEntities = user.getUserAnswerEntities();

			for (QuestionAnswer queAnswer : questionAnswers) {
				if (queAnswer.getUserOption() != null && !queAnswer.getUserOption().isEmpty()) {
					QuestionEntity queEntity = questionEntities.stream()
							.filter(e -> e.getQuestionId().equals(queAnswer.getQuestionId())).findFirst().get();
					UserAnswerEntity userAnswerEntity;
					try {
						userAnswerEntity = userAnswerEntities.stream()
								.filter(u -> u.getQuestionEntity().equals(queEntity)).findFirst().get();
					} catch (NoSuchElementException exp) {
						userAnswerEntity = new UserAnswerEntity();
					}
					userAnswerEntity.setQuestionEntity(queEntity);
					for (OptionsEntity opt : queEntity.getOptionsEntities()) {
						if (opt.getOptionId().equalsIgnoreCase(queAnswer.getUserOption())) {
							userAnswerEntity.setOptionsEntity(opt);
							break;
						}
					}
					userAnswerEntity.setUserEntity(user);
					userAnswerEntities.add(userAnswerEntity);
				}

			}
			if (userAnswerEntities.size() < MIN_ANSWER) {
				logger.error("Minimun answers not given");
				throw new AppException(AppConstant.ERR_QUE_002.getStatusCode(),
						AppConstant.ERR_QUE_002.getStatusMessage());
			}
			userAnswerRepo.save(userAnswerEntities);
			response.setResponseCode(RES_SUCCESS);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return response;
	}

	@Override
	public Response createChallenge(String userId, String userChallenged, Float bettingAmount) throws AppException {
		Response response = new Response();
		try {
			UserEntity user = userRepo.findByUserId(userId);
			UserEntity chlUserEntity = null;
			if (userChallenged != null) {
				chlUserEntity = userRepo.findByUserId(userChallenged);
			}
			if (user.getUserAnswerEntities().size() < MIN_ANSWER) {
				logger.error("Answers not provided", userId);
				throw new AppException(AppConstant.ERR_CHL_006.getStatusCode(),
						AppConstant.ERR_CHL_006.getStatusMessage());
			}

			UserChallengeEntity userChlEntity = userChallengeRepo.findByUserEntityAndUserChallengedAndWin(user,
					chlUserEntity, null);
			if (userChlEntity != null) {
				logger.error("Previous bet awaiting between user: {} and challenged: {}", userId, userChallenged);
				throw new AppException(AppConstant.ERR_CHL_001.getStatusCode(),
						AppConstant.ERR_CHL_001.getStatusMessage());
			} else {

				if (user.getTrial()) {
					userChlEntity = new UserChallengeEntity();
					userChlEntity.setBettingAmount(0f);
					userChlEntity.setUserEntity(user);
					userChlEntity.setUserChallenged(chlUserEntity);
					user.setTrial(false);
					user.setAwaitingChl(user.getAwaitingChl() + 1);
					userRepo.save(user);
					userChallengeRepo.save(userChlEntity);
				} else {
					userChlEntity = bettingHelper.getUserChlEntity(user, chlUserEntity, bettingAmount);
				}
				userChallengeRepo.save(userChlEntity);
				userRepo.save(user);
			}

			response.setResponseCode(userChlEntity.getId().toString());
			response.setResponseMessage(AppConstant.SUC_CHL_001.getStatusMessage());
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return response;
	}

	@Override
	public Challenge getChallenge(String userId, Long challengeId) throws AppException {
		Challenge challenge = new Challenge();
		try {
			UserChallengeEntity userChallengeEntity = userChallengeRepo.findOne(challengeId);
			if (userChallengeEntity == null || userChallengeEntity.getWin() != null) {
				logger.error("Not intended for him", challengeId, userId);
				throw new AppException(AppConstant.ERR_CHL_004.getStatusCode(),
						AppConstant.ERR_CHL_004.getStatusMessage());
			}

			if (userChallengeEntity.getUserChallenged() != null) {
				if (!userChallengeEntity.getUserChallenged().getUserId().equalsIgnoreCase(userId)) {
					logger.error("Not intended for him", challengeId, userId);
					throw new AppException(AppConstant.ERR_USR_003.getStatusCode(),
							AppConstant.ERR_USR_003.getStatusMessage());
				}
			}

			challenge.setUser(userChallengeEntity.getUserEntity().getUsername());
			challenge.setChallenge(userId);
			challenge.setBetAmount(userChallengeEntity.getBettingAmount());
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return challenge;
	}

	@Override
	public Response acceptChallenge(String username, String userId, Long challengeId) throws AppException {
		Response response = new Response();
		response.setResponseCode("SUCCESS");
		try {
			UserChallengeEntity userChallengeEntity = userChallengeRepo.findOne(challengeId);
			if (userChallengeEntity == null || userChallengeEntity.getWin() != null) {
				logger.error("Not intended for him", challengeId, username);
				throw new AppException(AppConstant.ERR_CHL_004.getStatusCode(),
						AppConstant.ERR_CHL_004.getStatusMessage());
			}
			if (userChallengeEntity.getUserChallenged() != null) {
				if (!userChallengeEntity.getUserChallenged().getUserId().equalsIgnoreCase(userId)) {
					logger.error("Not intended for him", challengeId, userId);
					throw new AppException(AppConstant.ERR_USR_003.getStatusCode(),
							AppConstant.ERR_USR_003.getStatusMessage());
				}
			}
			if (userChallengeEntity.getUserChallenged() != null) {
				logger.error("Already accepted", challengeId);
			} else {
				UserEntity user = userRepo.findByUserId(userId);
				if (user.getAvailable() < userChallengeEntity.getBettingAmount()) {
					logger.error("Not enough tokens", challengeId);
					throw new AppException(AppConstant.ERR_CHL_003.getStatusCode(),
							AppConstant.ERR_CHL_003.getStatusMessage());
				}
				user.setAvailable(user.getAvailable() - userChallengeEntity.getBettingAmount());
				userChallengeEntity.setUserChallenged(user);
				userChallengeRepo.save(userChallengeEntity);
			}
			response.setResponseMessage(userChallengeEntity.getUserEntity().getUserId());
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return response;
	}

	@Override
	public List<QuestionAnswer> getChallengeQuestion(Long challengeId) throws AppException {
		logger.debug("Get all challenge questions");
		List<QuestionAnswer> questionAnswers = new ArrayList<>();
		try {
			UserChallengeEntity userChallengeEntity = userChallengeRepo.findOne(challengeId);
			if (userChallengeEntity == null || userChallengeEntity.getWin() != null) {
				logger.error("Not intended for him", challengeId);
				throw new AppException(AppConstant.ERR_CHL_004.getStatusCode(),
						AppConstant.ERR_CHL_004.getStatusMessage());
			}
			List<UserAnswerEntity> allUserAnswerEntities = userChallengeEntity.getUserEntity().getUserAnswerEntities();
			// Shuffle and limit to 10
			Collections.shuffle(allUserAnswerEntities);
			List<UserAnswerEntity> userAnswerEntities = allUserAnswerEntities.stream().limit(CHL_QUE_LIMIT)
					.collect(Collectors.toList());

			for (UserAnswerEntity userAnswerEntity : userAnswerEntities) {
				QuestionAnswer questionAnswer = new QuestionAnswer();
				QuestionEntity que = userAnswerEntity.getQuestionEntity();
				questionAnswer.setQuestion(que.getText());
				questionAnswer.setQuestionId(que.getQuestionId());
				List<Option> options = new ArrayList<>();
				que.getOptionsEntities().forEach(opt -> {
					Option option = new Option();
					option.setOption(opt.getOption());
					option.setOptionId(opt.getOptionId());
					options.add(option);
				});
				questionAnswer.setOptions(options);
				questionAnswers.add(questionAnswer);
			}
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return questionAnswers;
	}

	@Override
	public Response submitChallenge(Long challengeId, List<QuestionAnswer> questionAnswers, String userId)
			throws AppException {
		logger.debug("Update answer for challengeId: {} by user: {}", challengeId, userId);
		Response response = new Response();
		try {
			int optionsGiven = 0;
			for (QuestionAnswer questionAnswer : questionAnswers) {
				if (questionAnswer.getUserOption() != null) {
					optionsGiven++;
				}
			}
			if (optionsGiven <= 9) {
				throw new AppException(AppConstant.ERR_CHL_007.getStatusCode(),
						AppConstant.ERR_CHL_007.getStatusMessage());
			}

			int winCount = 0;
			List<BetAnswer> betAnswers = new ArrayList<>();
			UserChallengeEntity userChallengeEntity = userChallengeRepo.findOne(challengeId);
			if (userChallengeEntity == null || userChallengeEntity.getWin() != null) {
				logger.error("Not intended for him", challengeId, userId);
				throw new AppException(AppConstant.ERR_CHL_004.getStatusCode(),
						AppConstant.ERR_CHL_004.getStatusMessage());
			}
			List<UserAnswerEntity> userAnswerEntities = userChallengeEntity.getUserEntity().getUserAnswerEntities();
			for (QuestionAnswer queAns : questionAnswers) {
				BetAnswer betAnswer = new BetAnswer();
				betAnswer.setUserChallenge(userChallengeEntity);
				UserAnswerEntity usrAnsEntity = userAnswerEntities.stream()
						.filter(e -> e.getQuestionEntity().getQuestionId().equalsIgnoreCase(queAns.getQuestionId()))
						.findFirst().get();
				// Set question
				betAnswer.setQuestion(usrAnsEntity.getQuestionEntity());
				List<OptionsEntity> optionsEntities = usrAnsEntity.getQuestionEntity().getOptionsEntities();
				for (OptionsEntity optionsEntity : optionsEntities) {
					if (optionsEntity.getOptionId().equalsIgnoreCase(queAns.getUserOption())) {
						// Set option
						betAnswer.setAnswer(optionsEntity);
						betAnswer.setCorrectAnswer(usrAnsEntity.getOptionsEntity());
						if (usrAnsEntity.getOptionsEntity().getOptionId().equalsIgnoreCase(queAns.getUserOption())) {
							winCount++;
						}
						break;
					}
				}
				betAnswers.add(betAnswer);
			}

			// Update userChallenge
			if (winCount < WIN_THRESHOLD) {
				userChallengeEntity.setWin(true);

				// User
				UserEntity user = userChallengeEntity.getUserEntity();
				user.setAvailable(user.getAvailable() + userChallengeEntity.getBettingAmount() * 2);
				user.setWin(user.getWin() + userChallengeEntity.getBettingAmount());
				user.setAwaitingChl(user.getAwaitingChl() - 1);
				user.setWinChl(user.getWinChl() + 1);

				// User Challenged
				UserEntity userChallenged = userChallengeEntity.getUserChallenged();
				userChallenged.setLoss(userChallenged.getLoss() + userChallengeEntity.getBettingAmount());
				userChallenged.setLossChl(userChallenged.getLossChl() + 1);

				// Response
				response.setResponseCode("LOSS");
				response.setResponseMessage(
						"Oho! You lossed the bet and " + userChallengeEntity.getBettingAmount() + " tokens");
			} else {
				userChallengeEntity.setWin(false);

				// User Challenged
				UserEntity userChallenged = userChallengeEntity.getUserChallenged();
				userChallenged.setAvailable(userChallenged.getAvailable() + userChallengeEntity.getBettingAmount() * 2);
				userChallenged.setWin(userChallenged.getWin() + userChallengeEntity.getBettingAmount());
				userChallenged.setWinChl(userChallenged.getWinChl() + 1);

				// User
				UserEntity user = userChallengeEntity.getUserEntity();
				user.setLoss(user.getLoss() + userChallengeEntity.getBettingAmount());
				user.setAwaitingChl(user.getAwaitingChl() - 1);
				user.setLossChl(user.getLossChl() + 1);

				// Response
				response.setResponseCode("WIN");
				response.setResponseMessage(
						"Hurry! You won the bet and " + userChallengeEntity.getBettingAmount() + " tokens");
			}
			betAnswerRepo.save(betAnswers);
			userChallengeRepo.save(userChallengeEntity);
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return response;
	}

	@Override
	public UserChallenge getAllPreviousChallenge(String userId) throws AppException {
		UserChallenge userChallenge = new UserChallenge();
		try {
			UserEntity user = userRepo.findByUserId(userId);
			List<UserChallengeEntity> userList = user.getUserChallengeEntities();
			List<Challenge> usrChallenge = new ArrayList<>();
			userList.forEach(chl -> {
				if (chl.getWin() != null) {
					Challenge chll = new Challenge();
					chll.setChlId(chl.getId());
					chll.setBetAmount(chl.getBettingAmount());
					chll.setWin(chl.getWin());
					chll.setUser(user.getUsername());
					chll.setChallenge(chl.getUserChallenged().getUsername());
					usrChallenge.add(chll);
				}
			});
			userChallenge.setUserChallenge(usrChallenge);

			List<UserChallengeEntity> userChallengeList = user.getUserChallengedEntities();
			List<Challenge> usrChallenged = new ArrayList<>();
			userChallengeList.forEach(chl -> {
				if (chl.getWin() != null) {
					Challenge chll = new Challenge();
					chll.setChlId(chl.getId());
					chll.setBetAmount(chl.getBettingAmount());
					chll.setWin(chl.getWin());
					chll.setUser(chl.getUserEntity().getUsername());
					chll.setChallenge(user.getUsername());
					usrChallenged.add(chll);
				}
			});
			userChallenge.setUserChallenged(usrChallenged);
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return userChallenge;
	}

	@Override
	public List<PaidAnswer> getPaidAnswers(String userId, Long challengeId, Boolean initiator) throws AppException {
		List<PaidAnswer> paidAnswers = new ArrayList<>();
		try {
			UserChallengeEntity userChallengeEntity = userChallengeRepo.findOne(challengeId);
			UserEntity userEntity = null;
			if (initiator) {
				userEntity = userChallengeEntity.getUserEntity();
			} else {
				userEntity = userChallengeEntity.getUserChallenged();
			}
			if (!userEntity.getUserId().equalsIgnoreCase(userId)) {
				logger.error("Unauthorized", userId);
				throw new AppException(AppConstant.ERR_USR_003.getStatusCode(),
						AppConstant.ERR_USR_003.getStatusMessage());
			}
			if (userEntity.getAvailable() < PAID_ANSWER_RATE) {
				logger.error("Not enough token", userId);
				throw new AppException(AppConstant.ERR_CHL_005.getStatusCode(),
						AppConstant.ERR_CHL_005.getStatusMessage());
			}
			userEntity.setAvailable(userEntity.getAvailable() - PAID_ANSWER_RATE);
			userEntity.setSpent(userEntity.getSpent() + PAID_ANSWER_RATE);

			userChallengeEntity.getBetAnswers().forEach(que -> {
				PaidAnswer paidAnswer = new PaidAnswer();
				paidAnswer.setQuestion(que.getQuestion().getText());
				String usrans = null;
				String chlans = null;
				if (initiator) {
					usrans = que.getCorrectAnswer().getOption();
					chlans = que.getAnswer().getOption();
				} else {
					chlans = que.getCorrectAnswer().getOption();
					usrans = que.getAnswer().getOption();
				}
				paidAnswer.setUserAnswer(usrans);
				paidAnswer.setChallengedAnswer(chlans);
				paidAnswer.setCorrect(usrans.equalsIgnoreCase(chlans));
				paidAnswers.add(paidAnswer);
			});
			userRepo.save(userEntity);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(AppConstant.ERR_UNKNOWN.getStatusCode(), AppConstant.ERR_UNKNOWN.getStatusMessage());
		}
		return paidAnswers;
	}

	private void createUser(String userId, String userName, String email) throws AppException {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userId);
		userEntity.setUsername(userName);
		userEntity.setTrial(true);
		userEntity.setAvailable(0f);
		userEntity.setSpent(0f);
		userEntity.setWin(0f);
		userEntity.setLoss(0f);
		userEntity.setAwaitingChl(0);
		userEntity.setWinChl(0);
		userEntity.setLossChl(0);
		userEntity.setEmail(email);
		userRepo.save(userEntity);
	}

	private String getOptionValue(String questionId, List<UserAnswerEntity> userAnswerEntities) {
		String optionValue = null;
		for (UserAnswerEntity ans : userAnswerEntities) {
			if (ans.getQuestionEntity().getQuestionId().equalsIgnoreCase(questionId)) {
				optionValue = ans.getOptionsEntity().getOptionId();
				break;
			}
		}
		return optionValue;
	}
}
