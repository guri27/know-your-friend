/**
 * 
 */
package com.grahire.challenge.constant;

/**
 * @author gurpr
 *
 */
public enum AppConstant {
	
	ERR_UNKNOWN("ERR_UNKNOWN","Unknown error"),
	ERR_USR_001("ERR_USR_001","No such user"),
	ERR_USR_002("ERR_USR_002","Error connecting with facebook"),
	ERR_USR_003("ERR_USR_003","Unauthorized user"),
	ERR_QUE_001("ERR_QUE_001","Error retreiving questions"),
	ERR_QUE_002("ERR_QUE_002","Minimun 15 answers needs to be given"),
	ERR_CHL_001("ERR_CHL_001","Previous bet awaiting"),
	ERR_CHL_002("ERR_CHL_002","Betting amount cannot be zero"),
	ERR_CHL_003("ERR_CHL_003","Available tokens are less, please add more tokens in profile"),
	ERR_CHL_004("ERR_CHL_004","No such challenge"),
	ERR_CHL_005("ERR_CHL_005","Not enough tokens available, please add tokens in profile"),
	ERR_CHL_006("ERR_CHL_006","Please give answers before creating challenge"),
	ERR_CHL_007("ERR_CHL_007","Please give all answers"),
	SUC_USR_001("SUC_USR_001","Login success"),
	SUC_USR_002("SUC_USR_002","New user"),
	SUC_CHL_001("SUC_CHL_001","Challenge created"),
	ERR_PAY_001("ERR_PAY_001","Error creating payment request"),
	ERR_PAY_002("ERR_PAY_002","Available token are less");
	
	private String statusCode;
	
	private String statusMessage;
	
	/**
	 * 
	 */
	private AppConstant(String statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}
