package org.springframework.nanotrader;

import com.meterware.httpunit.*;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

/*
 * Negative Tests: ensuring no access is granted before login
 */
public class NanoTraderWithoutLoginTest {
  WebResponse response;
  WebRequest request;
  WebConversation conversation;

  static String context_uri = "";

  @BeforeClass
  public static void oneTimeSetUp() {
    context_uri = "https://10.150.82.211:8080/daytrader/app";
    HttpUnitOptions.setScriptingEnabled(false);
  }

  @AfterClass
  public static void oneTimeTearDown() {
    System.out.println("@AfterClass - oneTimeTearDown");
  }

  @Before
  public void setUp() throws Exception {
    conversation = new WebConversation();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetOpenOrders() {
    try {
      request = new GetMethodWebRequest(context_uri + "/account/1/orders?status=open");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testGetClosedOrders() {
    try {
      request = new GetMethodWebRequest(context_uri + "/account/1/orders?status=closed");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testCreateQuote() {
    try {
      request = new PostMethodWebRequest(context_uri + "/quotes");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testGetQuote() {
    try {
      request = new GetMethodWebRequest(context_uri + "/quotes/1");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testGetQuotes() {
    try {
      request = new GetMethodWebRequest(context_uri + "/quotes");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testGetAccountData() {
    try {
      request = new GetMethodWebRequest(context_uri + "/account/1");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testGetAccountProfile() {
    try {
      request = new GetMethodWebRequest(context_uri + "/account/1/profile");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }

  @Test
  public void testUpdateAccountProfile() {
    try {
      InputStream is = null;
      request = new PutMethodWebRequest(context_uri + "/account/1/profile", is, "");
      response = conversation.getResponse(request);
      assertTrue(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      assertTrue(false);
    }
  }
}