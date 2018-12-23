/**
 * 
 */
package com.grahire.challenge.exception;

/**
 * @author gurpr
 *
 */
public class AppException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8219884188319039675L;

	private String errorCode;
	
	private String errorMessage;
	
	/**
	 * 
	 */
	public AppException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
