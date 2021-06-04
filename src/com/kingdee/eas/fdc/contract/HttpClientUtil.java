package com.kingdee.eas.fdc.contract;



import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
 
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
 
/**
 * HTTPClient ������.
 * �򵥾ͺã����������ơ�gx
 */
@SuppressWarnings(value = "all")
public class HttpClientUtil {
 
	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	private static PoolingHttpClientConnectionManager connectionManager;
	private static String DEFAULT_STR = "";
	private static String UTF_8 = "UTF-8";
	private final static int CONNECT_TIMEOUT = 3000;// ���ӳ�ʱ���� ps����ʾ�������ӵĳ�ʱʱ��
	private final static int SOCKET_TIMEOUT = 10000;// ���䳬ʱ���� ps����ʾ���ݴ��䴦��ʱ��
	private final static int REQUESTCONNECT_TIMEOUT = 2000;// ���̳߳ػ�ȡ���ӳ�ʱʱ�����
	private final static int MAX_TOTAL = 50;// �̳߳ص����������
	private final static int CONNECT_DEFAULT_ROUTE = 5;// ÿ��·��Ĭ�ϻ�����������
 
	private static void init() {
		if (connectionManager == null) {
			connectionManager = new PoolingHttpClientConnectionManager();
			connectionManager.setMaxTotal(MAX_TOTAL);// �������ӳ����������
			// ���ÿ������ӹ���ʱ��,���ÿ�������ʱ���ȼ���Ƿ����ʱ�䳬�����ʱ�䣬����������ͷ�socket���½���
			//connectionManager.setValidateAfterInactivity(50000);
			connectionManager.setDefaultMaxPerRoute(CONNECT_DEFAULT_ROUTE);// ÿ·�������������Ĭ��ֵ��2
		}
	}
 
	/**
	 * ͨ�����ӳػ�ȡHttpClient
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		Builder builder = RequestConfig.custom();
		RequestConfig config = builder.setSocketTimeout(SOCKET_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT)
				.build();
		CloseableHttpClient client = HttpClients.custom()
				.setMaxConnPerRoute(CONNECT_DEFAULT_ROUTE)
				.disableConnectionState().setDefaultRequestConfig(config)
				.setConnectionManager(connectionManager).build();
		return client;
	}
 
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	public static String httpGetRequest(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}
 
	public static String httpGetRequest(String url, Map<String, Object> params) throws Exception {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);
 
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);
 
		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}
 
	public static String httpGetRequest(String url, Map<String, Object> headers
			, Map<String, Object> params) throws Exception {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);
 
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);
 
		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}
 
	public static String httpPostRequest(String url) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}
 
	public static String httpPostRequest(String url, Map<String, Object> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
		return getResult(httpPost);
	}
	
	public static String httpPostRequest(String url, String jsonParams) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		StringEntity se = new StringEntity(jsonParams,UTF_8); 
        httpPost.setEntity(se);
        httpPost.setHeader("Content-Type","application/json");
		return getResult(httpPost);
	}
	
	public static String httpPostXMLDataRequest(String url, String xmlData) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(new StringEntity(xmlData, UTF_8));
		return getResult(httpPost);
	}
 
	/**
	 * post
	 * @param url ��a=3&b=2 ��ʽ��
	 * @param headers ����ͷ
	 * @param params ����
	 * @return
	 * @throws IOException
	 */
	public static String httpPostRequest(String url, Map<String, Object> headers
			, Map<String, Object> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
 
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
 
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
 
 
		return getResult(httpPost);
	}
 
	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
		}
		return pairs;
	}
 
	/**
	 * post
	 * @param url ��JSON ��ʽ��
	 * @param headers ����ͷ
	 * @param params ����
	 * @return
	 * @throws IOException
	 */
	public static String httpPostRequest(String url, Map<String, Object> headers, String jsonParams
			) throws Exception {
		HttpPost httpPost = new HttpPost(url);
 
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.setHeader(param.getKey(), String.valueOf(param.getValue()));
		}
 
		StringEntity se = new StringEntity(jsonParams,UTF_8);
		httpPost.setEntity(se);
 
		return getResult(httpPost);
	}
 
	/**
	 * ����Http����
	 * 
	 * @param request
	 * @return string
	 * @throws IOException
	 */
	private static String getResult(HttpRequestBase request) throws IOException {
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		InputStream in = null;
		try {
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			in = response.getEntity().getContent();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				response.close();
				return result;
			}
		} catch (ConnectTimeoutException e) {
			// ���ӳ�ʱ�쳣
			logger.error("connect timeout {}", e);
			throw new IOException("�ӿ����ӳ�ʱ�쳣",e);
		} catch (SocketTimeoutException e) {
			// ��ȡ��ʱ�쳣
			logger.error("read timeout {}", e);
			throw new IOException("�ӿڷ���ֵ��ȡ��ʱ�쳣",e);
		} catch (ClientProtocolException e) {
			// ���쳣ͨ����Э�������:���繹��HttpGet����ʱ����Э�鲻��(��'http'д��'htp')or��Ӧ���ݲ�����
			logger.error("protocol exception {}", e);
			throw new IOException("�ӿ�Э�����",e);
		} catch (ParseException e) {
			// �����쳣
			logger.error("parse exception {}", e);
			throw new IOException("�ӿڷ���ֵ�����쳣",e);
		} catch (IOException e) {
			// ���쳣ͨ��������ԭ�������,��HTTP������δ������
			logger.error("network exception {}", e);
			throw new IOException("�����쳣",e);
		} catch (Exception e) {
			logger.error("other exception {}", e);
			throw new IOException("�ӿ�����ʱ��������δ֪�쳣",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//in.close();���þ��ǽ�����������ͷţ��´�������Ը���
            //�����ر�ע����ǣ������ʹ��in.close();������ʹ��response.close();����������ӻᱻ�رգ����Ҳ��ܱ����ã�������ʧȥ�˲������ӳص����塣
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return DEFAULT_STR;
	}

}