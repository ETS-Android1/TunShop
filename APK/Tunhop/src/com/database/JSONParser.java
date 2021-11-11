package com.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 Classe JSON qui permet d'obtenir les données à travers des scripts PHP
 *
 */
public class JSONParser {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    public static  boolean isConnected(Context context)
    {
    	ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo info = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		  if (info == null||!info.isConnected()||!info.isAvailable())
			  return false;		
			  else return true; 
    }
  public static  boolean HeightConnection(Context c)
    {
	 /*
	 * Les nivaux du signal 
	 * Level 4  -55 <= RSSI
	 * Level 3  -66 <= RSSI < -55
	 * Level 2  -77 <= RSSI < -67
	 * Level 1  -88 <= RSSI < -78
	 * Level 0         RSSI < -88
	 */
    	WifiManager myWifiManager = (WifiManager)c.getSystemService(Context.WIFI_SERVICE);
	    WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
    	int numberOfLevels=5;
        int level=WifiManager.calculateSignalLevel(myWifiInfo.getRssi(), numberOfLevels);
        Log.e("wifi",level+"_"+myWifiInfo.getLinkSpeed()+"_"+myWifiInfo.getRssi());        
        return (level< 3);
    }
 
    // fonction sert à obtenir JSON à partir d'un URL en faisant HTTP POST ou GET 
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) throws Exception {
    	
    	int a = (int) System.currentTimeMillis();
        HttpConnectionParams.setConnectionTimeout(new BasicHttpParams(), 10000);
        HttpConnectionParams.setSoTimeout(new BasicHttpParams(), 10000);
        DefaultHttpClient  httpClient = new DefaultHttpClient(new BasicHttpParams());
        HttpResponse httpResponse;
        HttpEntity httpEntity;
        
        // check for request method
            if(method == "POST"){
            	
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params)); 
                httpResponse= httpClient.execute(httpPost);
                 httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
 
            }else if(method == "GET"){
                // request method is GET
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url); 
                httpResponse = httpClient.execute(httpGet);
                httpEntity= httpResponse.getEntity();
                is = httpEntity.getContent();
            }           

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            jObj = new JSONObject(json);
          int  b = (int) System.currentTimeMillis();
		    Log.e("Geolocaliser : temps de réponse",(b-a)+" ms" );  
            // return JSON String
        return jObj;
 
    }
   
}