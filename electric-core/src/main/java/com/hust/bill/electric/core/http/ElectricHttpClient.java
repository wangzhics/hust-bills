package com.hust.bill.electric.core.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElectricHttpClient {
	
	private static Logger logger = LoggerFactory.getLogger(ElectricHttpClient.class);
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private Document currentDocument = null;
	
	public void perpare() throws RequestException {
		HttpGet httpGet = new HttpGet(HttpElements.url);
		CloseableHttpResponse response =  tryThreeTime(httpGet);;
		updateCurrentDocument(response);
	}

	public void executeRequest(IRequest request) throws RequestException {
		List<BasicNameValuePair> pairList = updatePostParams();
		for(NameValuePair nameValuePair : request.perparePostForm()){
			pairList.add(new BasicNameValuePair(nameValuePair.getName(), nameValuePair.getValue()));
		}
		HttpPost httpPost = new HttpPost(HttpElements.url);
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairList, "utf-8");
			httpPost.setEntity(formEntity);
			CloseableHttpResponse response =  tryThreeTime(httpPost);;
			updateCurrentDocument(response);
		} catch (UnsupportedEncodingException e) {
			throw new RequestException("UnsupportedEncoding: utf8-[" + pairList + "]", e);
		}
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}
	
	private CloseableHttpResponse tryThreeTime(HttpRequestBase request) throws RequestException {
		Throwable lastException = null;
		for(int i = 0; i < 3; i++) {
			try {
				return httpClient.execute(request);
			} catch (ClientProtocolException e) {
				lastException = e;
				logger.warn("http request[" + request + "] occur http protocol error, try again");
			} catch (IOException e) {
				throw new RequestException("request[" + request + "] occur io error", e);
			}
		}
		throw new RequestException("request[" + request + "] occur http protocol error, already try 3 times", lastException);
	}
	
	private void updateCurrentDocument(CloseableHttpResponse response) throws RequestException {
		try {
			HttpEntity entity = response.getEntity();
			currentDocument = Jsoup.parse(entity.getContent(), "utf-8", HttpElements.url);
		} catch (IOException e) {
			throw new RequestException("response can not be paser to jsoup document", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				throw new RequestException("response can not closed", e);
			}
		}
	}
	
	private List<BasicNameValuePair> updatePostParams() {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(10);
		Element eventArgumentElement = currentDocument.getElementById(HttpElements.__EVENTARGUMENT);
		Element eventTargetElement = currentDocument.getElementById(HttpElements.__EVENTTARGET);
		Element eventValidationElement = currentDocument.getElementById(HttpElements.__EVENTVALIDATION);
		Element lastFocusElement = currentDocument.getElementById(HttpElements.__LASTFOCUS);
		Element viewStateElement = currentDocument.getElementById(HttpElements.__VIEWSTATE);
		if(eventArgumentElement != null) {
			pairs.add(new BasicNameValuePair(HttpElements.__EVENTARGUMENT, eventArgumentElement.val()));
		}
		if(eventTargetElement != null) {
			pairs.add(new BasicNameValuePair(HttpElements.__EVENTTARGET, eventTargetElement.val()));
		}
		if(eventValidationElement != null) {
			pairs.add(new BasicNameValuePair(HttpElements.__EVENTVALIDATION, eventValidationElement.val()));
		}
		if(lastFocusElement != null) {
			pairs.add(new BasicNameValuePair(HttpElements.__LASTFOCUS, lastFocusElement.val()));
		}
		if(viewStateElement != null) {
			pairs.add(new BasicNameValuePair(HttpElements.__VIEWSTATE, viewStateElement.val()));
		}
		return pairs;
	}
}
