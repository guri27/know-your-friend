/****************************************************
 * BetAnswerRepo.java
 *
 *	Date		Author					Remark
 *	18-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.repository;

import org.springframework.data.repository.CrudRepository;

import com.grahire.challenge.entity.BetAnswer;

/**
 * @author Gurpreet Singh
 *
 */
public interface BetAnswerRepo extends CrudRepository<BetAnswer, Long>{

}
