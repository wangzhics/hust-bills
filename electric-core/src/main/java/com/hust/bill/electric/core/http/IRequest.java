package com.hust.bill.electric.core.http;


import org.apache.http.NameValuePair;

public interface IRequest {

	public abstract NameValuePair[] perparePostForm();
}
