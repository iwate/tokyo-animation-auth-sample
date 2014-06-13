package com.tokyoanimation.models;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class CallbackActivity extends Activity {
	private final String TAG = "Callback";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Uri uri = this.getIntent().getData();
        
        if(uri != null && uri.toString().startsWith("callback://app.tokyoanimation.com")){
            String accessToken = uri.getQueryParameter("access_token");
            String tokenType = uri.getQueryParameter("token_type");
            String expiresIn = uri.getQueryParameter("expires_in");
            String state = uri.getQueryParameter("state");
            Log.d(TAG, "access toke: " + accessToken);
            Log.d(TAG, "token type: " + tokenType);
            Log.d(TAG, "expires in: " + expiresIn);
            Log.d(TAG, "state: " + state);
            Client client = new Client(this);
            client.setToken(accessToken);
            client.setTokenType(tokenType);
        }
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		
		
	}
}
