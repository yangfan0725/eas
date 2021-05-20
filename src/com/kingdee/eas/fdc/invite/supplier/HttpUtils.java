package com.kingdee.eas.fdc.invite.supplier;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpUtils {
	
	
	public static void request(String url,String method,String param){
		
		HttpClient hc = new HttpClient();
		HttpMethod hm = new  GetMethod();
		try {
			hm.setURI(new URI("http://www.baidu.com",true,"Utf-8"));
			hc.executeMethod(hm);
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		request("","","");
	}

}
