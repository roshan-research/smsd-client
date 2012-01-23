package ir.sobhe.smsd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Smsd extends Activity {
	private TextView tv;
	Handler handel;
	Runnable fetcherRunnable = new FetcherRunnable();
	Thread fetcher;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        handel = new Handler();
        fetcher = new Thread(fetcherRunnable);
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	fetcher.suspend();
    	fetcher.stop();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	fetcher.start();
    }
    
    class FetcherRunnable implements Runnable{

		@Override
		public void run() {
			int i = 0;
			while(true){
				i++;
				if (i == 1) {
					handel.post(new Runnable() {
						@Override
						public void run() {
							tv.append("fetcher started\n");
						}
					});
				}
				try{
					HttpClient httpclient = new DefaultHttpClient();
			    	HttpPost httppost = new HttpPost(Constants.fetch_url);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("name", "htc-tatto"));
					nameValuePairs.add(new BasicNameValuePair("key", "2f1a5ee55fe8435b6aa82782d318f5e2"));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					
					InputStream reponseStream = response.getEntity().getContent();
					Scanner in = new Scanner(reponseStream);
					
					String tempString = "";
					
					while(in.hasNext()){
						tempString += in.nextLine();
					}
					
					final String responseString = tempString;
					handel.post(new Runnable() {
						
						@Override
						public void run() {
							tv.append("Response: \n");
							tv.append(responseString + "\n");
						}
					});
					
					JSONObject json = (JSONObject) new JSONTokener(responseString).nextValue();
					JSONArray messages = json.getJSONArray("messages");
					
					if (messages.length() > 0) {
						//Put the messages in database
					}
				}
				catch(ClientProtocolException e){
					e.printStackTrace();
				}
				catch(IOException e){
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(Constants.fetch_interval);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    	
    }
}