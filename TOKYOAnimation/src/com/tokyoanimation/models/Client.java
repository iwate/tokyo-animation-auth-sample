package com.tokyoanimation.models;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class Client {
	private final String PREF_KEY = "tokyo_animation_client";
	private final String TOKEN = "token";
	private final String TOKEN_TYPE = "token_type";
	private final String SCHEME = "http";
	private final String HOST = "tokyo-animation.azurewebsites.net";
	private final String CALLBACK_URL = "callback://app.tokyoanimation.com";
	
	private String token;
	private String tokenType;
	private Context context;
	private SharedPreferences preferences;
	
	public Client(Context context){
		this.context = context;
		this.preferences = this.context.getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
	}
	
	public void setToken(String token) {
		SharedPreferences.Editor editor = this.preferences.edit();
		editor.putString(TOKEN, token);
		editor.commit();
		this.token = token;
	}
	public void setTokenType(String type){
		SharedPreferences.Editor editor = this.preferences.edit();
		editor.putString(TOKEN_TYPE, type);
		editor.commit();
		this.tokenType = type;
	}
	public String getToken(){
		if(this.token == null){
			this.token = this.preferences.getString(TOKEN, null);
		}
		return this.token;
	}
	public String getTokenType(){
		if(this.tokenType == null){
			this.tokenType = this.preferences.getString(TOKEN_TYPE, null);
		}
		return this.tokenType;
	}
	
	private void Get(String controller, String action){
		Uri.Builder builder = new Uri.Builder();
		builder.scheme(SCHEME);
		builder.encodedAuthority(HOST);
	}
	
	public void register(RegisterModel model){
		Uri.Builder builder = new Uri.Builder();
		builder.scheme(SCHEME);
		builder.encodedAuthority(HOST);
		builder.path("/api/account/register");
		
		
	}
	
	public void loginWithFacebook() throws ClientException{
		Uri.Builder builder = new Uri.Builder();
		builder.scheme(SCHEME);
		builder.encodedAuthority(HOST);
		builder.path("/api/account/ExternalLogins");
		builder.appendQueryParameter("returnUrl", CALLBACK_URL);
		builder.appendQueryParameter("generateState", "true");
		
		new GetJSONTask(new ResponseJSONHandler(){

			@Override
			public void handleResponse(String strJSON) {
				JSONArray externals;
				String externalUrl = null;
				try {
					externals = new JSONArray(strJSON);
					for(int i = 0, len = externals.length(); i < len; i++){
				    	JSONObject external = externals.getJSONObject(i);
				    	if(external.getString("Name").equals("Facebook")){
				    		externalUrl = external.getString("Url");
				    	}
				    }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(externalUrl != null){
					open(externalUrl);
				}
			}}).execute(builder.build().toString());
	}
	private void open(String url) {
		Uri uri = Uri.parse(SCHEME + "://" + HOST + url);
		Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		((Activity) context).startActivity(intent); 
	}
	
	public class GetJSONTask extends AsyncTask<String, String, String> {
		private ResponseJSONHandler handler;
		
		public GetJSONTask(ResponseJSONHandler handler){
			this.handler = handler;
		}

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			HttpGet request = new HttpGet(url);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String result = null;
			try {
			    result = httpClient.execute(request, new ResponseHandler<String>() {
			        @Override
			        public String handleResponse(HttpResponse response)
			                throws ClientProtocolException, IOException {
			            
			            switch (response.getStatusLine().getStatusCode()) {
			            case HttpStatus.SC_OK:
			                return EntityUtils.toString(response.getEntity(), "UTF-8");
			            
			            case HttpStatus.SC_NOT_FOUND:
			                throw new RuntimeException("NOT FOUND"); //FIXME
			                
			            default:
			                throw new RuntimeException("UNKOWN ERROR"); //FIXME
			            }

			        }
			    });
			    Log.d("loginWithFacebook", result);
			} catch (ClientProtocolException e) {
			    throw new RuntimeException(e); //FIXME
			} catch (IOException e) {
			    throw new RuntimeException(e); //FIXME
			} finally {
			    httpClient.getConnectionManager().shutdown();
			}
			
			return result;
		}
		
		@Override
	    protected void onPostExecute(String result){
			handler.handleResponse(result);
		}
	}
	
	public class RegisterTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			String strJson = params[1];
			
			HttpPost request = new HttpPost(url);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			request.addHeader("Content-Type", "appication/json");
			
			try {
			    String result = httpClient.execute(request, new ResponseHandler<String>() {
			        @Override
			        public String handleResponse(HttpResponse response)
			                throws ClientProtocolException, IOException {
			            
			            switch (response.getStatusLine().getStatusCode()) {
			            case HttpStatus.SC_OK:
			                return EntityUtils.toString(response.getEntity(), "UTF-8");
			            
			            case HttpStatus.SC_NOT_FOUND:
			                throw new RuntimeException("NOT FOUND"); //FIXME
			                
			            default:
			                throw new RuntimeException("UNKOWN ERROR"); //FIXME
			            }
			        }
			    });
			    Log.d("loginWithFacebook", result);
			} catch (ClientProtocolException e) {
			    throw new RuntimeException(e); //FIXME
			} catch (IOException e) {
			    throw new RuntimeException(e); //FIXME
			} finally {
			    httpClient.getConnectionManager().shutdown();
			}
			
			return null;
		}
		
	}
	
	interface ResponseJSONHandler{
		public void handleResponse(String strJSON);
	}

}
