/****************************************************
 * InstamojoRepository.java
 *
 *	Date		Author					Remark
 *	03-Dec-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grahire.challenge.entity.InstamojoEntity;

/**
 * @author Gurpreet Singh
 *
 */
@Repository
public interface InstamojoRepository extends CrudRepository<InstamojoEntity, Long>{

	public InstamojoEntity findByInstaId(String instaId);
	
}
