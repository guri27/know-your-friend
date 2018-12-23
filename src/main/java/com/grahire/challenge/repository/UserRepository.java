/**
 * 
 */
package com.grahire.challenge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grahire.challenge.entity.UserEntity;

/**
 * @author gurpr
 *
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
	
	public UserEntity findByUserId(String userId);

}
