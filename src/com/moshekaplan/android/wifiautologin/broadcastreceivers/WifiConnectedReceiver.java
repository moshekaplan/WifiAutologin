package com.moshekaplan.android.wifiautologin.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.moshekaplan.android.EasyHTTPClient;


public class WifiConnectedReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		    final String action = intent.getAction();
		    Log.d("WifiConnectedReceiver", "action: " + action);
		    Log.d("WifiConnectedReceiver", "extra: " + intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false) );
		    if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
		        if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)){
		            // Wifi connected - now login!
		        	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		        	
		    		String username = preferences.getString("username", "");
		    		String password = preferences.getString("password", "");
		    		Log.d("WifiConnectedReceiver", "username: " + username + " password: " + password);
		    		EasyHTTPClient.connect(username, password);
		        }
		    }
		}		
}
