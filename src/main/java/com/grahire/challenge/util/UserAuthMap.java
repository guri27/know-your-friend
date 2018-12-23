/****************************************************
 * UserAuthMap.java
 *
 *	Date		Author					Remark
 *	17-Sep-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grahire.challenge.entity.UserEntity;
import com.grahire.challenge.repository.UserRepository;

/**
 * @author Gurpreet Singh
 *
 */
@Component
public class UserAuthMap {
	
	@Autowired
	UserRepository userRepo;
	
	private Map<String, String> authMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		List<UserEntity> usrEntity = (List<UserEntity>) userRepo.findAll();
		usrEntity.forEach(usr -> {
			authMap.put(usr.getUserId(), "Init");
		});
	}
	
	public boolean validateToken(String userId, String token) {
		return authMap.get(userId).equalsIgnoreCase(token);
	}
	
	public void addToken(String userId, String token) {
		authMap.put(userId, token);
	}
	
	public String getToken(String userId) {
		return authMap.get(userId);
	}

}
