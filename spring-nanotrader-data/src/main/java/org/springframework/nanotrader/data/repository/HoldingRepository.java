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
package org.springframework.nanotrader.data.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.nanotrader.data.domain.Holding;
import org.springframework.stereotype.Repository;

/**
 *  @author Gary Russell
 * 	@author Brian Dussault
 */

@Repository
public interface HoldingRepository extends JpaSpecificationExecutor<Holding>, JpaRepository<Holding, Integer> {
	
	public List<Holding> findByAccountAccountid(Integer accountId, Pageable pageable);
	
	public Holding findByHoldingidAndAccountAccountid(Integer holdingId, Integer accountId);
	
	@Query("SELECT count(h) FROM Holding h WHERE h.accountAccountid = ?1")
	public Long findCountOfHoldings(Integer accountId);
	
}
