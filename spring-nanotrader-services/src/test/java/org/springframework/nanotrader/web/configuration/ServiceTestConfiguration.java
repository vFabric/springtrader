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
package org.springframework.nanotrader.web.configuration;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.nanotrader.data.domain.Account;
import org.springframework.nanotrader.data.domain.Accountprofile;
import org.springframework.nanotrader.data.domain.Holding;
import org.springframework.nanotrader.data.domain.HoldingAggregate;
import org.springframework.nanotrader.data.domain.HoldingSummary;
import org.springframework.nanotrader.data.domain.MarketSummary;
import org.springframework.nanotrader.data.domain.Order;
import org.springframework.nanotrader.data.domain.PortfolioSummary;
import org.springframework.nanotrader.data.domain.Quote;
import org.springframework.nanotrader.data.service.TradingService;
import org.springframework.nanotrader.data.service.TradingServiceImpl;
import org.springframework.nanotrader.data.util.FinancialUtils;
import org.springframework.nanotrader.service.cache.DataCreationProgressCache;
import org.springframework.nanotrader.service.support.AdminServiceFacade;
import org.springframework.nanotrader.service.support.AdminServiceFacadeImpl;
import org.springframework.nanotrader.service.support.TradingServiceFacade;
import org.springframework.nanotrader.service.support.TradingServiceFacadeImpl;

/**
 *  ServiceTestConfiguration provides test objects and mock service layer for unit tests.
 *  
 *  @author Brian Dussault 
 *  @author
 */

@Configuration
@Profile("test")
public class ServiceTestConfiguration  {
	//Holding constants
	public static Integer HOLDING_ID = 100;
	public static Integer ACCOUNT_ID = 500;
	public static BigDecimal PURCHASE_PRICE =  BigDecimal.valueOf(50000);
	public static String SYMBOL = "VMW";
	public static BigDecimal QUANTITY =  BigDecimal.valueOf(200);
	
	//Account profile constants
	public static Integer PROFILE_ID 	=  400;
	public static String USER_ID 		= "johndoe";
	public static String EMAIL 		= "anon@springsource.com";
	public static String FULL_NAME 	= "John Doe";
	public static String CC_NUMBER 	= "999999999";
	public static String ADDRESS 		= "45 Test Dr.";
	public static Integer NOT_A_VALID_PROFILE = 900;
	public static String PASSWORD = "password";
	public static String BAD_USER_ID  = "NA";
	public static String BAD_PASSWORD  = "NA";
	//Order constants
	public static Integer ORDER_ID 	=  555;
	public static BigDecimal ORDER_PRICE = BigDecimal.valueOf(100);
	public static BigDecimal ORDER_QUANTITY = BigDecimal.valueOf(200);
	public static String ORDER_TYPE_BUY	=  "buy";
	public static String ORDER_STATUS_CLOSED	=  "closed";
	
	//Quote constants
	public static Integer QUOTE_ID = 1;
	public static String COMPANY_NAME	=  "VMware";
	public static BigDecimal HIGH	=   BigDecimal.valueOf(50.02);
	public static BigDecimal OPEN	=  BigDecimal.valueOf(40.11);
	public static BigDecimal VOLUME	= BigDecimal.valueOf(3000);
	public static BigDecimal CURRENT_PRICE	=  BigDecimal.valueOf(48.44);
	public static Integer RANDOM_QUOTES_COUNT = 5;
	
	//Account constants
	public static BigDecimal ACCOUNT_OPEN_BALANCE	=   BigDecimal.valueOf(55.02);
	public static BigDecimal ACCOUNT_BALANCE	=   BigDecimal.valueOf(40.11);
	public static Integer LOGOUT_COUNT	=  new Integer(5);
	public static Integer LOGIN_COUNT	=  new Integer(4);
	public static String AUTH_TOKEN	=  "faef8649-280d-4ba4-bdf6-574e758a04a7";
	
	
	//Portfolio Summary
	public static Integer HOLDING_COUNT	=  1;
	public static BigDecimal BASIS =   BigDecimal.valueOf(150.25);
	public static BigDecimal MARKET_VALUE =  BigDecimal.valueOf(300.10);
	
	//Market Summary
	public static BigDecimal MARKET_INDEX =  	BigDecimal.valueOf(100.25);
	public static BigDecimal MARKET_OPENING =   BigDecimal.valueOf(35.25);
	public static BigDecimal MARKET_VOLUME =   	BigDecimal.valueOf(40.45);
	
	//Holding Summary
	public static BigDecimal HOLDING_SUMMARY_GAINS =   BigDecimal.valueOf(1000.54);
	public static BigDecimal GAIN1 =   BigDecimal.valueOf(600.54);
	public static BigDecimal GAIN2 =   BigDecimal.valueOf(400.00);
	public static String SYMBOL2 = "OTHER";
	public static String PAGE_LABEL = "page";
	public static String PAGE_SIZE = "pageSize";
	public static String TOTAL_RECORDS = "totalRecords";
	public static Long RESULT_COUNT  = new Long(1);
	public static String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date(1329759342904l));
	
	@Bean 
	public TradingService tradingService() {
		TradingService tradingService = Mockito.mock(TradingService.class);
		when(tradingService.findHolding(eq(100), eq(ACCOUNT_ID))).thenReturn(holding());
		when(tradingService.findHoldingsByAccountId(eq(ACCOUNT_ID),  any(Integer.class), any(Integer.class))).thenReturn(holdings());
		when(tradingService.findCountOfHoldingsByAccountId(eq(ACCOUNT_ID))).thenReturn(RESULT_COUNT);
		when(tradingService.findCountOfOrders(eq(ACCOUNT_ID) , any(String.class))).thenReturn(RESULT_COUNT);
		when(tradingService.updateHolding(any(Holding.class))).thenReturn(holding());
		when(tradingService.findAccountProfile(400)).thenReturn(accountProfile());
		when(tradingService.findAccountProfile(NOT_A_VALID_PROFILE)).thenReturn(null);
		when(tradingService.updateAccountProfile(any(Accountprofile.class), any(String.class))).thenReturn(accountProfile());
		when(tradingService.findOrder(eq(999), eq(ACCOUNT_ID))).thenReturn(order());
		when(tradingService.saveOrder(any(Order.class))).thenReturn(null);
		when(tradingService.saveAccountProfile(any(Accountprofile.class))).thenReturn(accountProfile());
		when(tradingService.updateOrder(any(Order.class))).thenReturn(null);
		when(tradingService.findOrdersByStatus(eq(ACCOUNT_ID), any(String.class), any(Integer.class), any(Integer.class))).thenReturn(orders());
		when(tradingService.findOrders(eq(ACCOUNT_ID), any(Integer.class), any(Integer.class))).thenReturn(orders());
		when(tradingService.findQuoteBySymbol(eq(SYMBOL))).thenReturn(quote());
		when(tradingService.findRandomQuotes(RANDOM_QUOTES_COUNT)).thenReturn(quotes());
		when(tradingService.findQuotesBySymbols(anySetOf(String.class))).thenReturn(quotes());
		when(tradingService.findAccount(eq(ACCOUNT_ID))).thenReturn(account());
		when(tradingService.findAccountByProfile(any(Accountprofile.class))).thenReturn(account());
		when(tradingService.findPortfolioSummary(eq(ACCOUNT_ID))).thenReturn(portfolioSummary());
		when(tradingService.findMarketSummary()).thenReturn(marketSummary());
		when(tradingService.login(eq(USER_ID), eq(PASSWORD))).thenReturn(accountProfile());
		when(tradingService.login(eq(BAD_USER_ID), eq(BAD_PASSWORD))).thenReturn(null);
		when(tradingService.findHoldingSummary(eq(ACCOUNT_ID))).thenReturn(holdingSummary());

		doNothing().when(tradingService).logout(any(String.class));
		return tradingService;
	}
	
	@Bean
	public TradingServiceFacade tradingServiceFacade() {
		return new TradingServiceFacadeImpl();
	}
	
	@Bean
	public AdminServiceFacade adminServiceFacade() {
		return new AdminServiceFacadeImpl();
	}
	
	@Bean
	public DataCreationProgressCache getProgressCache() {
		return new DataCreationProgressCache();
	}

	@Bean 
	public Holding holding() {
		Holding holding = new Holding();
		holding.setHoldingid(HOLDING_ID);
		holding.setAccountAccountid(ACCOUNT_ID);
		holding.setPurchasedate(new Date(1329759342904l));
		holding.setQuoteSymbol(SYMBOL);
		holding.setPurchaseprice(PURCHASE_PRICE);
		holding.setQuantity(QUANTITY);
		return holding;
	}
	

	@Bean 
	public Account account() {
		Account account = new Account();
		account.setAccountid(ACCOUNT_ID);
		account.setBalance(ACCOUNT_BALANCE);
		account.setOpenbalance(ACCOUNT_OPEN_BALANCE);
		account.setLogincount(LOGIN_COUNT);
		account.setLogoutcount(LOGOUT_COUNT);
		account.setCreationdate(new Date(1329759342904l));
		account.setLastlogin(new Date(1329759342904l));
		return account;
	}
	

	@Bean 
	public Accountprofile accountProfile() {
		Accountprofile accountProfile = new Accountprofile();
		accountProfile.setProfileid(PROFILE_ID);
		accountProfile.setUserid(USER_ID);
		accountProfile.setPasswd(PASSWORD);
		accountProfile.setAddress(ADDRESS);
		accountProfile.setEmail(EMAIL);
		accountProfile.setFullname(FULL_NAME);
		accountProfile.setCreditcard(CC_NUMBER);
		accountProfile.setAuthtoken(AUTH_TOKEN);
		Set<Account> accounts = new HashSet<Account>();
		accounts.add(account());
		accountProfile.setAccounts(accounts);
		return accountProfile;
	}
	
	@Bean 
	public Order order() {
		Order order = new Order();
		Account account = new Account();
		account.setAccountid(ACCOUNT_ID);
		order.setAccountAccountid(account);
		order.setOrderid(ORDER_ID);
		order.setPrice(ORDER_PRICE);
		order.setOrderstatus(ORDER_STATUS_CLOSED);
		order.setOrdertype(ORDER_TYPE_BUY);
		order.setOpendate(new Date(1329759342904l));
		order.setCompletiondate(new Date(1329759342904l));
		order.setHoldingHoldingid(holding());
		order.setQuantity(ORDER_QUANTITY);
		order.setOrderfee(TradingServiceImpl.DEFAULT_ORDER_FEE);
		order.setQuote(quote());
		return order;
	}

	public Quote quote() { 
		Quote quote = new Quote();
		quote.setQuoteid(QUOTE_ID);
		quote.setSymbol(SYMBOL);
		quote.setCompanyname(COMPANY_NAME);
		quote.setHigh(HIGH);
		quote.setOpen1(OPEN);
		quote.setVolume(VOLUME);
		quote.setPrice(CURRENT_PRICE);
		return quote;
	}
	
	public List<Quote> quotes() { 
		List<Quote> quotes = new ArrayList<Quote>();
		quotes.add(quote());
		return quotes;
	}
	
	public List<Order> orders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(order());
		return orders;
	}
	
	public List<Holding> holdings() {
		List<Holding> holdings = new ArrayList<Holding>();
		holdings.add(holding());
		return holdings;
	}
	
	
	public PortfolioSummary portfolioSummary() {
		PortfolioSummary portfolioSummary = new PortfolioSummary();
		portfolioSummary.setNumberOfHoldings(HOLDING_COUNT);
		portfolioSummary.setTotalBasis(BASIS);
		portfolioSummary.setTotalMarketValue(MARKET_VALUE);
		return portfolioSummary;
	}

	public MarketSummary marketSummary() {
		MarketSummary marketSummary = new MarketSummary();
		marketSummary.setSummaryDate(new Date(1329759342904l));
		marketSummary.setTradeStockIndexAverage(MARKET_INDEX);
		marketSummary.setTradeStockIndexOpenAverage(MARKET_OPENING);
		marketSummary.setTradeStockIndexVolume(MARKET_VOLUME);
		List<Quote> loserQuotes = new ArrayList<Quote>();
		loserQuotes.add(quote());
		marketSummary.setTopLosers(loserQuotes);
		List<Quote> gainingQuotes = new ArrayList<Quote>();
		gainingQuotes.add(quote());
		marketSummary.setTopGainers(gainingQuotes);
		return marketSummary;
	}

	public HoldingSummary holdingSummary() {
		HoldingSummary holdingSummary = new HoldingSummary();
		List<HoldingAggregate> holdings = new ArrayList<HoldingAggregate>();
		holdingSummary.setHoldingsTotalGains(HOLDING_SUMMARY_GAINS.setScale(2, RoundingMode.HALF_UP));
		HoldingAggregate holding1 = new HoldingAggregate();
		holding1.setSymbol(SYMBOL);
		holding1.setGain(GAIN1.setScale(2, RoundingMode.HALF_UP));
		holding1.setPercent(FinancialUtils.calculateGainPercentage(holding1.getGain(), holdingSummary.getHoldingsTotalGains()).setScale(2, RoundingMode.HALF_UP));
		holdings.add(holding1);
		HoldingAggregate holding2 = new HoldingAggregate();
		holding2.setSymbol(SYMBOL2);
		holding2.setGain(GAIN2.setScale(2, RoundingMode.HALF_UP));
		holding2.setPercent(FinancialUtils.calculateGainPercentage(holding2.getGain(), holdingSummary.getHoldingsTotalGains()).setScale(2, RoundingMode.HALF_UP));
		holdings.add(holding2);
		holdingSummary.setHoldingRollups(holdings);
		return holdingSummary;
	}
	 

}
