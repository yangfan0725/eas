package com.kingdee.eas.fdc.contract.app;

import org.apache.commons.httpclient.methods.PostMethod;

import com.kingdee.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.security.cert.X509Certificate;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 本工具类主要用于发送向服务器发送http服务请求
 * author:zengfanxin
 * date:2017-11-22 20:17:18
 */
public class HttpClientUtil {

    /**
     * 发送https请求
     *
     * @param urlPath
     *            目的地址
     * @param method
     *            请求方法
     *@param contentType
     *            请求类型
     *@param encodeType
     *            编码类型
     *@param headers
     *            请求头参数
     * @param parameters
     *            请求Json结构参数，字符串类型。
     * @return 远程响应结果
     * @throws Exception 
     * @throws Exception 异常
     */
    public static String sendRequest(String urlPath, String method, String contentType, String encodeType,Map<String, String> headers, String parameters) throws Exception{
        String result = "";
        BufferedReader reader = null;
        OutputStream outwritestream = null;
        HttpsURLConnection conn = null;
        try {
            SslUtils.ignoreSsl();
            URL url = new URL(urlPath);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(3*60*1000);
            conn.setRequestProperty("Connection", "Keep-Alive");
            String codeType = StringUtils.isEmpty(encodeType)?"UTF-8":encodeType;
            conn.setRequestProperty("Charset", codeType);
            if(!StringUtils.isEmpty(contentType)){
                // 设置文件类型:
                conn.setRequestProperty("Content-Type",contentType+"; charset="+codeType);
                //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                //设置接收类型否则返回415错误
                conn.setRequestProperty("accept",contentType);
            }
//            if(Optional.ofNullable(headers).isPresent()){
            if(headers!=null){
            	for (Map.Entry<String, String> entry : headers.entrySet()) {
            		conn.setRequestProperty(entry.getKey() , entry.getValue());
            	}
            }
//            }

//            if(Optional.ofNullable(parameters).isPresent()){
                // 往服务器里面发送数据
                byte[] writebytes = parameters.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                outwritestream = conn.getOutputStream();
                outwritestream.write(parameters.getBytes(codeType));
                outwritestream.flush();
//            }

            System.out.println("doJsonPost response code: "+conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),codeType));
            }else{
                if(conn.getErrorStream() != null){
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(),codeType));
                }
            }
            String s=null;
            if(reader != null){
                while((s=reader.readLine()) != null){
                    result = result+s;
                }
            }else{
                result="{\"code\":\"NACK\",\"message\":\"服务错误[service response code:"+conn.getResponseCode()+"]\"}";
            }
        } catch (Exception e) {
           throw e;
        } finally {
            /*
            if(wout != null){
                wout.close();;
            }*/
            if (outwritestream != null) {
                try {
                    outwritestream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                conn.disconnect();
            }
        }
        return result;

    }

    /**
     * 发送http请求
     *
     * @param urlPath
     *            目的地址
     * @param method
     *            请求方法
     *@param contentType
     *            请求类型
     *@param encodeType
     *            编码类型
     *@param headers
     *            请求头参数
     * @param parameters
     *            请求Json结构参数，字符串类型。
     * @return 远程响应结果
     * @throws Exception 异常
     */
    public static String sendHttpRequest(String urlPath, String method, String contentType, String encodeType,Map<String, String> headers, String parameters) throws Exception{
        String result = "";
        BufferedReader reader = null;
        OutputStream outwritestream = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(15*60*1000);
            conn.setRequestProperty("Connection", "Keep-Alive");
            String codeType = StringUtils.isEmpty(encodeType)?"UTF-8":encodeType;
            conn.setRequestProperty("Charset", codeType);
            if(!StringUtils.isEmpty(contentType)){
                // 设置文件类型:
                conn.setRequestProperty("Content-Type",contentType+"; charset="+codeType);
                //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                //设置接收类型否则返回415错误
                conn.setRequestProperty("accept",contentType);
            }
//        	for (Map.Entry<String, String> entry : headers.entrySet()) {
//        		conn.setRequestProperty(entry.getKey() , entry.getValue());
//        	}

                // 往服务器里面发送数据
            byte[] writebytes = parameters.getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
            outwritestream = conn.getOutputStream();
            outwritestream.write(parameters.getBytes(codeType));
            outwritestream.flush();

            System.out.println("doJsonPost response code: "+conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),codeType));
            }else{
                if(conn.getErrorStream() != null){
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(),codeType));
                }
            }
            String s=null;
            if(reader != null){
                while((s=reader.readLine()) != null){
                    result = result+s;
                }
            }else{
                result="{\"code\":\"NACK\",\"message\":\"服务错误[service response code:"+conn.getResponseCode()+"]\"}";
            }
        } catch (Exception e) {
            throw e;
        } finally {
            /*
            if(wout != null){
                wout.close();;
            }*/
            if (outwritestream != null) {
                try {
                    outwritestream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                conn.disconnect();


            }
        }
        return result;

    }


    /**
     * 生成soap体消息
     * @param soapNameSpace
     * @param namespaceAlias
     * @param serviceMethod
     * @param message
     * @return
     */
    public static String getSoapXML(String soapNameSpace,String namespaceAlias,String serviceMethod,String message){

        String soapXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                +"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:"+namespaceAlias+"=\""+soapNameSpace+"\">\n"
                +"<SOAP-ENV:Body>"
                +"<"+namespaceAlias+":"+serviceMethod+">"
                +message
                +"</"+namespaceAlias+":"+serviceMethod+">"
                +"</SOAP-ENV:Body>"
                +"</SOAP-ENV:Envelope>";
        return soapXML;
    }


}
