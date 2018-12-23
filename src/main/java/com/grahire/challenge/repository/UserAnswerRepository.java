/****************************************************
 * UserAnswerRepository.java
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

import com.grahire.challenge.entity.UserAnswerEntity;

/**
 * @author gurpr
 *
 */
@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswerEntity, Long>{
	
}
