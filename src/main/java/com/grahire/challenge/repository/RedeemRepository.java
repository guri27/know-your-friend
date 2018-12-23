/****************************************************
 * RedeemRepository.java
 *
 *	Date		Author					Remark
 *	08-Dec-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grahire.challenge.entity.RedeemEntity;

/**
 * @author Gurpreet Singh
 *
 */
@Repository
public interface RedeemRepository extends CrudRepository<RedeemEntity, Long>{

}
