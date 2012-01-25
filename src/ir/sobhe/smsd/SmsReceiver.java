package ir.sobhe.smsd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		
		String from;
		String text;
		if (extras != null){
			Object[] pdus = (Object[]) extras.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i = 0; i < msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu( (byte[]) pdus[i]);
				str += msgs[i].getMessageBody().toString();
			}
			from = msgs[0].getOriginatingAddress();
			text = str;
			
			try{
				//Make HTTP POST
				HttpClient httpclient = new DefaultHttpClient();
		    	HttpPost httppost = new HttpPost(Constants.tell_received_url);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("name", Constants.name));
				nameValuePairs.add(new BasicNameValuePair("key", Constants.key));
				nameValuePairs.add(new BasicNameValuePair("from", from));
				nameValuePairs.add(new BasicNameValuePair("text", text));
				UrlEncodedFormEntity urfe = new UrlEncodedFormEntity(nameValuePairs);
				urfe.setContentEncoding("");
				httppost.setEntity(urfe);
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
			}
		}
	}
}
