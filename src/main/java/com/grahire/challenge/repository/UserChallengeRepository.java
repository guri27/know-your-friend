/****************************************************
 * UserChallengeRepository.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grahire.challenge.entity.UserChallengeEntity;
import com.grahire.challenge.entity.UserEntity;

/**
 * @author gurpr
 *
 */
@Repository
public interface UserChallengeRepository extends CrudRepository<UserChallengeEntity, Long> {

	public UserChallengeEntity findByUserEntityAndUserChallengedAndWin(UserEntity user, UserEntity userChallenged, Boolean win);
	
}
