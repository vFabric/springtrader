/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.nanotrader.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.nanotrader.service.support.exception.AuthenticationException;
import org.springframework.nanotrader.service.support.exception.NoRecordsFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  GlobalExceptionHandler provides a consistent error handling facility for 
 *  nanotraders REST based API
 *  
 *  @author Brian Dussault 
 */

public class GlobalExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private static final String FAILURE_MESSSAGE = "An error has occured while processing the request: ";
	private static final String NO_RECORDS_FOUND_MESSSAGE = "No records found for the specified criteria";
	private static final String CONSTRAINT_VIOLATION_MESSAGE = "The record already exists.";
	private static final String UNAUTHORIZED_MESSAGE = "Authentication Failed: Can't find username and password combination.";
	private static final String NO_ACCESS = "Access Denied.";
	private static final String CANNOT_UPDATE_CLOSED_RECORD = "Update failed since processing status was 'complete'";
	private static final String CANNOT_RETRIEVE_RECORD = "The record does not exist.";
	
	@ExceptionHandler(value = NoRecordsFoundException.class)
	@ResponseStatus( HttpStatus.NOT_FOUND )
	public @ResponseBody ServiceException handle(NoRecordsFoundException exception) {
		ServiceException serviceException = new ServiceException(NO_RECORDS_FOUND_MESSSAGE );
		return serviceException;
	}
	
	
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	@ResponseStatus( HttpStatus.UNSUPPORTED_MEDIA_TYPE )
	public @ResponseBody ServiceException handle(HttpMediaTypeNotSupportedException exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(FAILURE_MESSSAGE + exception.getMessage() );
		return serviceException;
	}
	
	@ExceptionHandler(value = IncorrectUpdateSemanticsDataAccessException.class)
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	public @ResponseBody ServiceException handle(IncorrectUpdateSemanticsDataAccessException exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(CANNOT_UPDATE_CLOSED_RECORD);
		return serviceException;
	}	
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	public @ResponseBody ServiceException handle(DataIntegrityViolationException exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(CONSTRAINT_VIOLATION_MESSAGE);
		return serviceException;
	}	
	
	@ExceptionHandler(value = DataRetrievalFailureException.class)
	@ResponseStatus( HttpStatus.NOT_FOUND )
	public @ResponseBody ServiceException handle(DataRetrievalFailureException exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(CANNOT_RETRIEVE_RECORD);
		return serviceException;
	}	
	
	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseStatus( HttpStatus.UNAUTHORIZED )
	public @ResponseBody ServiceException handle(AuthenticationException exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(UNAUTHORIZED_MESSAGE);
		return serviceException;
	}	
	
	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus( HttpStatus.UNAUTHORIZED )
	public @ResponseBody ServiceException handle(AccessDeniedException exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(NO_ACCESS);
		return serviceException;
	}	
	
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus( HttpStatus.BAD_REQUEST )
	public @ResponseBody ServiceException handle(Exception exception) {
		logError(exception);
		ServiceException serviceException = new ServiceException(FAILURE_MESSSAGE + exception.getMessage() );
		return serviceException;
	}	
	
	private void logError(Exception exception) { 
		log.error("GlobalExceptionHandler.handle(" + exception.getClass().getName() + "):" + exception.getMessage());
	}
	
}
