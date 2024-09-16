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
package org.springframework.nanotrader.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.nanotrader.service.domain.Accountprofile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Provides JSON based REST api to Accountprofile repository
 * 
 * @author Brian Dussault
 */

@Controller
public class AccountProfileController extends BaseController {

	@RequestMapping(value = "/accountProfile/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Accountprofile> find(@PathVariable("id") final Integer id) {
		this.getSecurityUtil().checkAccountProfile(id);
		Accountprofile accountProfile = getTradingServiceFacade().findAccountProfile(id);
		return new ResponseEntity<Accountprofile>(accountProfile, getNoCacheHeaders(),
				HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/accountProfile", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody Accountprofile accountProfileRequest,  UriComponentsBuilder builder) {
		Integer accountProfileId = getTradingServiceFacade().saveAccountProfile(accountProfileRequest);
		HttpHeaders responseHeaders = new HttpHeaders();   
		responseHeaders.setLocation(builder.path("/accountProfile/{id}").buildAndExpand(accountProfileId).toUri());
		return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/accountProfile/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id") final Integer id, @RequestBody Accountprofile accountProfileRequest) {
		this.getSecurityUtil().checkAccountProfile(id);
		accountProfileRequest.setProfileid(id);
		getTradingServiceFacade().updateAccountProfile(accountProfileRequest, this.getSecurityUtil().getUsernameFromPrincipal());
	}

	@RequestMapping(value = "/accountProfile/{id}", method = RequestMethod.DELETE)
	@ResponseStatus( HttpStatus.METHOD_NOT_ALLOWED )
	public void delete() {
		
	}
}
