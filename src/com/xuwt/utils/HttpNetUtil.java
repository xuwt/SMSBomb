package com.xuwt.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class HttpNetUtil {
	public HttpNetUtil() {
		super();
	}

	public static String httpClient(String paramString, String charsetName) {
        StringBuilder localStringBuilder = new StringBuilder();
        HttpClient localHttpClient = new HttpClient();
        GetMethod localGetMethod = new GetMethod(paramString);
        try {
        	localHttpClient.executeMethod(((HttpMethod)localGetMethod));
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localGetMethod.getResponseBodyAsStream(
                    ), charsetName));
            while(true) {
                String v1_2 = localBufferedReader.readLine();
                if(v1_2 != null) {
                	localStringBuilder.append(v1_2);
                }else{
                	 break;  
                }
                             
            }
        }
        catch(Throwable v0_1) {
        	localGetMethod.releaseConnection();
        }
        localGetMethod.releaseConnection();
        return localStringBuilder.toString();
        
    }

	public static String httpPostRequest(String url, String charsetName, String paramString) {
        String result;
        try {
            System.getProperties().setProperty("http.proxyHost", "59.57.15.71");
            System.getProperties().setProperty("http.proxyPort", "80");
            StringBuilder localStringBuilder = new StringBuilder();
            HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            localHttpURLConnection.setRequestMethod("POST");
            localHttpURLConnection.setDoOutput(true);
            localHttpURLConnection.setDoInput(true);
            OutputStream localOutputStream = localHttpURLConnection.getOutputStream();
            localOutputStream.write(paramString.toString().getBytes(charsetName));
            localOutputStream.close();
            InputStreamReader localInputStreamReader = new InputStreamReader((localHttpURLConnection).getInputStream(
                    ), charsetName);
            BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
            while(true) {
                String str = localBufferedReader.readLine();
                if(str != null) {
                	localStringBuilder.append(str);
                }else{
                	 break; 
                }
                              
            }
            localBufferedReader.close();
            localInputStreamReader.close();
            localHttpURLConnection.disconnect();
            result = localStringBuilder.toString();
            
        }
        catch(Exception exception) {
        	exception.printStackTrace();
            result = "";
        }
        return result;
    }

	public static String httpPostRequest(String url, String charsetName, String paramString, Map paramMap) {
		StringBuilder localStringBuilder = new StringBuilder();
		String str;
		try {
        	
            System.getProperties().setProperty("http.proxyHost", "59.57.15.71");
            System.getProperties().setProperty("http.proxyPort", "80");
            
            HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            localHttpURLConnection.setRequestMethod("POST");
            localHttpURLConnection.setDoOutput(true);
            localHttpURLConnection.setDoInput(true);
            
            Iterator localIterator = paramMap.entrySet().iterator();
            while(localIterator.hasNext()) {
            	Map.Entry localEntry = (Map.Entry)localIterator.next();
            	localHttpURLConnection.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue
                        ());          	
            	
            }
            OutputStream localOutputStream = localHttpURLConnection.getOutputStream();
            localOutputStream.write(paramString.toString().getBytes(charsetName));
            localOutputStream.close();
            
            InputStreamReader localInputStreamReader = new InputStreamReader(localHttpURLConnection.getInputStream(), charsetName);
            BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
            
            while(true) {
            	str = localBufferedReader.readLine();
                if(str != null) {
                	localStringBuilder.append(str);
                }else{
                	 break;
                }
                               
            }
            localBufferedReader.close();
            localInputStreamReader.close();
            localHttpURLConnection.disconnect();
            
            
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	
        }
        return localStringBuilder.toString();
    }

	public static String httpRequest(String url, String charsetName, Map paramMap) {
		 StringBuilder localStringBuilder = new StringBuilder();
		 String str;
        try {
           
        	HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            Iterator localIterator = paramMap.entrySet().iterator();
            while(localIterator.hasNext()) {
            	Map.Entry localEntry = (Map.Entry)localIterator.next();
            	localHttpURLConnection.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue
                        ()); 
            }

            InputStreamReader localInputStreamReader = new InputStreamReader(localHttpURLConnection.getInputStream(), 
            		charsetName);
            BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
            while(true) {
                str = localBufferedReader.readLine();
                if(str != null) {
                	localStringBuilder.append(str);
                }else{
                	break;
                }
            }

            localBufferedReader.close();
            localInputStreamReader.close();
            localHttpURLConnection.disconnect();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return localStringBuilder.toString();
    }

	public static String httpRequest(String url, String charsetName) {
		 StringBuilder localStringBuilder = new StringBuilder();
		 String str;
        try {
        	HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStreamReader localInputStreamReader = new InputStreamReader(localHttpURLConnection.getInputStream(), 
            		charsetName);
            BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
            while(true) {
            	str=localBufferedReader.readLine();
            	if(str != null) {
                	localStringBuilder.append(str);
                }else{
                	break;
                }
            }

            localBufferedReader.close();
            localInputStreamReader.close();
            localHttpURLConnection.disconnect();
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return localStringBuilder.toString();
    }

	public static String httpRequestHeader(String url, String arg3, String arg4) {
		String str;
		try {
			HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(url).openConnection();
			String v1 = localHttpURLConnection.getHeaderField(arg4);
			localHttpURLConnection.disconnect();
			str = v1;
		} catch (Exception v0) {
			str = "";
		}

		return str;
	}
	
	 /**
     * 
    * @Description: 判断服务是否运行
    * @param pac_act
    * @return    
    * @return boolean
     */
	public boolean isServiceRunning(Context context,String pac_act) {
		boolean flag = false;
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals(pac_act)) {
				flag =  true;
			}
		}
		return flag;
	}
}
