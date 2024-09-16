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

import java.lang.reflect.Method;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 *  ExtendedExceptionHandlerExceptionResolver provides custom exception resolution through the 
 *  invocation of global exception handlers.
 *  
 *  @author Brian Dussault 
 */

public class ExtendedExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

	private Object handler;

	private ExceptionHandlerMethodResolver methodResolver;

	/**
	 * Provide a handler with @{@link ExceptionHandler} methods.
	 */
	public void setExceptionHandler(Object handler) {
		this.handler = handler;
		this.methodResolver = new ExceptionHandlerMethodResolver(handler.getClass());
	}

	@Override
	protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
		ServletInvocableHandlerMethod result = super.getExceptionHandlerMethod(handlerMethod, exception);
		if (result != null) {
			return result;
		}
		Method method = this.methodResolver.resolveMethod(exception);
		return (method != null) ? new ServletInvocableHandlerMethod(this.handler, method) : null;
	}

}
