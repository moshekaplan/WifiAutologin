package com.moshekaplan.android;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class EasyHTTPClient {
	// Based on http://stackoverflow.com/questions/16389149/change-http-post-request-to-https-post-request
	public static HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	
	public static void connect(String username, String password){
		HttpClient httpclient = getNewHttpClient();
		String url = "https://1.1.1.1/login.html";
		
		HttpPost httppost = new HttpPost(url);
		Log.d("WifiConnectedReceiver", url);
	    try {
	        // Add the data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("buttonClicked", "4"));
	        nameValuePairs.add(new BasicNameValuePair("redirect_url", "www.gstatic.com%2Fgenerate_204"));
	        nameValuePairs.add(new BasicNameValuePair("err_flag", "0"));
	        nameValuePairs.add(new BasicNameValuePair("username", username));
	        nameValuePairs.add(new BasicNameValuePair("password", password));
	        
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        Log.d("WifiConnectedReceiver", "response:" + new BasicResponseHandler().handleResponse(response));

		} catch (ClientProtocolException e) {
			Log.d("WifiConnectedReceiver", "Exception: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("WifiConnectedReceiver", "Exception: " + e);
			e.printStackTrace();
		}
	}
	
}
