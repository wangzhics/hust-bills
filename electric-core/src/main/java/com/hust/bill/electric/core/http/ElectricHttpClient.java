package com.hust.bill.electric.core.http;

import java.io.IOException;
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
	
	public void perpare() throws IOException, ClientProtocolException {
		HttpGet httpGet = new HttpGet(HttpElements.url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		updateCurrentDocument(response);
	}

	public void executeRequest(IRequest request) throws IOException, ClientProtocolException {
		List<BasicNameValuePair> pairList = updatePostParams();
		for(NameValuePair nameValuePair : request.perparePostForm()){
			pairList.add(new BasicNameValuePair(nameValuePair.getName(), nameValuePair.getValue()));
		}
		HttpPost httpPost = new HttpPost(HttpElements.url);
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairList, "utf-8");
		httpPost.setEntity(formEntity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		updateCurrentDocument(response);
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}
	
	private void updateCurrentDocument(CloseableHttpResponse response) throws IOException{
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
