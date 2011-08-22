/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.geronimo.samples.daytrader.soap;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.geronimo.samples.daytrader.client.ws.AccountDataBean;
import org.apache.geronimo.samples.daytrader.client.ws.AccountProfileDataBean;
import org.apache.geronimo.samples.daytrader.client.ws.HoldingDataBean;
import org.apache.geronimo.samples.daytrader.client.ws.MarketSummaryDataBeanWS;
import org.apache.geronimo.samples.daytrader.client.ws.OrderDataBean;
import org.apache.geronimo.samples.daytrader.client.ws.QuoteDataBean;
import org.apache.geronimo.samples.daytrader.client.ws.RunStatsDataBean;
import org.apache.geronimo.samples.daytrader.client.ws.TradeWSServices;
import org.apache.geronimo.samples.daytrader.core.TradeAction;

/** 
 * This is a TradeAction wrapper to handle web service handling
 * of collections.  Instead this class uses typed arrays.
 */
@WebService(wsdlLocation = "/WEB-INF/wsdl/TradeServices.wsdl", portName = "TradeWSServices", targetNamespace = "http://daytrader.samples.geronimo.apache.org")
//@WebService
public class TradeWSAction implements TradeWSServices {
	TradeAction trade;
	
	public TradeWSAction() {
		trade = new TradeAction();
	}

	public MarketSummaryDataBeanWS getMarketSummary() throws RemoteException {
		try {
            return Convert.convertMarketSummaryDataBean(trade.getMarketSummary());
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}

	@WebMethod
	public OrderDataBean buy(
			@WebParam(name = "userID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String userID, 
			@WebParam(name = "symbol", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String symbol, 
			@WebParam(name = "quantity", targetNamespace = "http://daytrader.samples.geronimo.apache.org") double quantity, 
			@WebParam(name = "orderProcessingMode", targetNamespace = "http://daytrader.samples.geronimo.apache.org") int orderProcessingMode) throws RemoteException {
		try {
            return Convert.convertOrderDataBean(trade.buy(userID, symbol, quantity, orderProcessingMode));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	@WebMethod
	public OrderDataBean sell(
			@WebParam(name = "userID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String userID, 
			@WebParam(name = "holdingID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") Integer holdingID, 
			@WebParam(name = "orderProcessingMode", targetNamespace = "http://daytrader.samples.geronimo.apache.org") int orderProcessingMode) throws RemoteException {
		try {
            return Convert.convertOrderDataBean(trade.sell(userID, holdingID, orderProcessingMode));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}

	@WebMethod
	public void queueOrder(
			@WebParam(name = "orderID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") Integer orderID, 
			@WebParam(name = "twoPhase", targetNamespace = "http://daytrader.samples.geronimo.apache.org") boolean twoPhase) throws RemoteException {
		trade.queueOrder(orderID, twoPhase);
	}
	
	@WebMethod
	public OrderDataBean completeOrder(
			@WebParam(name = "orderID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") Integer orderID, 
			@WebParam(name = "twoPhase", targetNamespace = "http://daytrader.samples.geronimo.apache.org") boolean twoPhase) throws RemoteException {
		return Convert.convertOrderDataBean(trade.completeOrder(orderID, twoPhase));
	}

	@WebMethod
	public void cancelOrder(
			@WebParam(name = "orderID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") Integer orderID, 
			@WebParam(name = "twoPhase", targetNamespace = "http://daytrader.samples.geronimo.apache.org") boolean twoPhase) throws RemoteException {
		trade.cancelOrder(orderID, twoPhase);
	}
	
	@WebMethod
	public void orderCompleted(
			@WebParam(name = "userID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String userID, 
			@WebParam(name = "orderID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") Integer orderID) throws RemoteException {
		try {
            trade.orderCompleted(userID, orderID);
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	@WebMethod
	public OrderDataBean[] getOrders(
			@WebParam(name = "userID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String userID) throws RemoteException {
        try {
            return Convert.convertOrderDataBeanCollection(trade.getOrders(userID));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	@WebMethod
	public OrderDataBean[] getClosedOrders(
			@WebParam(name = "userID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String userID) throws RemoteException {
        try {
            return Convert.convertOrderDataBeanCollection(trade.getClosedOrders(userID));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	@WebMethod
	public QuoteDataBean createQuote(
			@WebParam(name = "symbol", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String symbol, 
			@WebParam(name = "companyName", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String companyName, 
			@WebParam(name = "price", targetNamespace = "http://daytrader.samples.geronimo.apache.org") BigDecimal price) throws RemoteException {
		try {
            return Convert.convertQuoteDataBean(trade.createQuote(symbol, companyName, price));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	@WebMethod
	public QuoteDataBean getQuote(@WebParam(name = "symbol", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String symbol) throws RemoteException {
		try {
            return Convert.convertQuoteDataBean(trade.getQuote(symbol));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}

	@WebMethod
	public QuoteDataBean[] getAllQuotes() throws RemoteException {
		try {
            return Convert.convertQuoteDataBeanCollection(trade.getAllQuotes());
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public QuoteDataBean updateQuotePriceVolume(String symbol, BigDecimal newPrice, double sharesTraded) throws RemoteException {
		try {
            return Convert.convertQuoteDataBean(trade.updateQuotePriceVolume(symbol, newPrice, sharesTraded));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public HoldingDataBean[] getHoldings(String userID) throws RemoteException {
		try {
            return Convert.convertHoldingDataBeanCollection(trade.getHoldings(userID));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public HoldingDataBean getHolding(Integer holdingID) throws RemoteException {
		try {
            return Convert.convertHoldingDataBean(trade.getHolding(holdingID));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public AccountDataBean getAccountData(String userID) throws RemoteException {
		try {
            return Convert.convertAccountDataBean(trade.getAccountData(userID));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	@WebMethod
	public AccountProfileDataBean getAccountProfileData(@WebParam(name = "userID", targetNamespace = "http://daytrader.samples.geronimo.apache.org") String userID) throws RemoteException {
		try {
            return Convert.convertAccountProfileDataBean(trade.getAccountProfileData(userID));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean profileData) throws RemoteException {
		try {
            return Convert.convertAccountProfileDataBean(trade.updateAccountProfile(Convert.convertAccountProfileDataBean(profileData)));
        } catch (Exception e) {
            throw new RemoteException("", e);            
        }
	}
	
	public AccountDataBean login(String userID, String password) throws RemoteException {
		try {
            return Convert.convertAccountDataBean(trade.login(userID, password));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public void logout(String userID) throws RemoteException {
		try {
            trade.logout(userID);
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public AccountDataBean register(String userID, String password, String fullname, String address, String email, String creditcard, BigDecimal openBalance) throws RemoteException {
		try {
            return Convert.convertAccountDataBean(trade.register(userID, password, fullname, address, email, creditcard, openBalance));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }
	}
	
	public RunStatsDataBean resetTrade(boolean deleteAll) throws RemoteException {
		try {
            return Convert.convertRunStatsDataBean(trade.resetTrade(deleteAll));
        } catch (Exception e) {
            throw new RemoteException("", e);
        }                
	}

}
