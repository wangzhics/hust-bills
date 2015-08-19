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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ElectricHttpClient {
	
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private Document currentDocument = null;
	
	public void perpare() throws RequestException {
		HttpGet httpGet = new HttpGet(HttpElements.url);
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			try {
				updateCurrentDocument(response);
			} catch (IOException e) {
				throw new RequestException("response can not be paser to jsoup document", e);
			}
		} catch (ClientProtocolException e) {
			throw new RequestException("request[" + httpGet + "] occur http protocol error", e);
		} catch (IOException e) {
			throw new RequestException("request[" + httpGet + "] occur io error", e);
		}
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
			CloseableHttpResponse response;
			try {
				response = httpClient.execute(httpPost);
				try {
					updateCurrentDocument(response);
				} catch (IOException e) {
					throw new RequestException("response can not be paser to jsoup document", e);
				}
			} catch (ClientProtocolException e) {
				throw new RequestException("request[" + httpPost + "] occur http protocol error", e);
			} catch (IOException e) {
				throw new RequestException("request[" + httpPost + "] occur io error", e);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RequestException("UnsupportedEncoding: utf8-[" + pairList + "]", e);
		}
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}
	
	private void updateCurrentDocument(CloseableHttpResponse response) throws IOException {
		try {
			HttpEntity entity = response.getEntity();
			currentDocument = Jsoup.parse(entity.getContent(), "utf-8", HttpElements.url);
		} finally {
			response.close();
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
