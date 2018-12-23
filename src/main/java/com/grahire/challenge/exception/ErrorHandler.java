package com.grahire.challenge.exception;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.grahire.challenge.model.Response;

@RestControllerAdvice
public class ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	public static final String HANDLING_EXCEPTION = "Handling excpetion: ";

	/**
	 * Handler for Not Found exception
	 * 
	 * @param notFoundException
	 * @return
	 */
	@ExceptionHandler(value = { AppException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Response handleAppException(AppException appException) {
		logger.debug("Handling AppException: ", appException);
		return new Response(appException.getErrorCode(), appException.getErrorMessage());
	}

	/**
	 * This method handles path variable and header validation exception.
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Response handleValidationException(ConstraintViolationException exception) {
		logger.debug(HANDLING_EXCEPTION, exception);
		return new Response(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
	}

	/**
	 * This method handles other Exceptions.
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Response handleOtherException(Exception exception) {
		logger.debug(HANDLING_EXCEPTION, exception);
		return new Response(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Unknown Error");
	}
}
