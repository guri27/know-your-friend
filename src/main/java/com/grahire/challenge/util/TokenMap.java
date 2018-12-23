/****************************************************
 * TokenMap.java
 *
 *	Date		Author					Remark
 *	03-Dec-2017 Gurpreet Singh Saini	Initial Version
 *
 *
 *  © Grahire
 ***************************************************/
package com.grahire.challenge.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Gurpreet Singh
 *
 */
@Component
public class TokenMap {
	
	//Key: Instamojo Id, Value: userId
	private Map<String, String> tokenMap = new HashMap<>();

	/**
	 * @return the tokenMap
	 */
	public Map<String, String> getTokenMap() {
		return tokenMap;
	}

	public void addToMap(String id, String userId) {
		tokenMap.put(id, userId);
	}

}
