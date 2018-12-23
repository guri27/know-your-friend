/****************************************************
 * QuestionRepository.java
 *
 *	Date		Author					Remark
 *	10-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grahire.challenge.entity.QuestionEntity;

/**
 * @author gurpr
 *
 */
@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {

	public List<QuestionEntity> findAll();

}
