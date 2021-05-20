package com.kingdee.eas.fdc.invite.supplier;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.json.JSONUtils;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.jdbc.rowset.IRowSet;

public class NetworkUtils {
	
	
	public static void processResponseResult(Context ctx,String result) throws ContractException{
		boolean isSuccess = false;
		HashMap<String,Object> preMap = new HashMap<String, Object>();
		try {
			preMap = (HashMap<String, Object>) JSONUtils.convertJsonToObject(ctx, result);
		} catch (Exception e) {
			throw new ContractException(ContractException.WITHMSG,new String[]{"同步数据失败，详细信息如下：\n"+result});
		}
		if(preMap.containsKey("code")){
			Double resultCode =(Double) preMap.get("code");
			if(resultCode.intValue() == 0){
				isSuccess = true;
			}
			
			if(!isSuccess){
				throw new ContractException(ContractException.WITHMSG,new String[]{"同步数据失败，详细信息如下：\n"+preMap.get("msg")});
			}
		}
	}
	
	
	public static String getBaseUrl(Context ctx){
		String url = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select url from t_gys_webserver where isenabled = 1");
		IRowSet rs;
		try {
			rs = builder.executeQuery();
			while(rs.next()){
				url = rs.getString("url");
			}
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	public static String post(String url, Map<String, String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		
		HttpPost post = postForm(url, params);
		
		body = invoke(httpclient, post);
		
		httpclient.getConnectionManager().shutdown();
		
		return body;
	}
	
	public static String get(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
		
		httpclient.getConnectionManager().shutdown();
		
		return body;
	}
		
	
	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = parseResponse(response);
		
		return body;
	}

	private static String parseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		
		String charset = "utf-8";
		
		String body = null;
		try {
			body = EntityUtils.toString(entity,charset);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) {
		HttpResponse response = null;
		
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params){
		
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		Set<String> keySet = params.keySet();
		for(String key : keySet) {
			nvps.add(new BasicNameValuePair(key, StringUtils.isEmpty(params.get(key))?"":params.get(key)));
		}
		
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return httpost;
	}
}
