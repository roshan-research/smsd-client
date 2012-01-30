package ir.sobhe.smsd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				try{
					//Make HTTP POST
					HttpClient httpclient = new DefaultHttpClient();
			    	HttpPost httppost = new HttpPost(Constants.tell_rang_url);
					JSONObject postData = new JSONObject();
					postData.put("name", Constants.name);
					postData.put("key", Constants.key);
					postData.put("from", phoneNumber);
					StringEntity se = new StringEntity(postData.toString(), "UTF-8");
					httppost.setEntity(se);
					HttpResponse response = httpclient.execute(httppost);
					
					String responseString = Constants.httpResponseToString(response);
					//String to JSON
					Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();
				}
				catch(UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				catch(ClientProtocolException e) {
					e.printStackTrace();
				}
				catch(IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
