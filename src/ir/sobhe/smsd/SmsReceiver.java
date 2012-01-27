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
				JSONObject postData = new JSONObject();
				postData.put("name", Constants.name);
				postData.put("key", Constants.key);
				postData.put("from", from);
				postData.put("text", text);
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
