package ir.sobhe.smsd;

import java.util.Timer;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

public class Smsd extends Activity {
	private Fetch fetch;
	private TextView tv;
	Timer timer = new Timer();
	Handler handel;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        handel = new Handler();
//      doBindService();
        start();
    }
    
    public void start(){
    	Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while(true){
					i++;
					final int value = i;
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					handel.post(new Runnable() {
						@Override
						public void run() {
							tv.append(new Integer(value).toString() + "\n");
						}
					});
				}
			}
    	};
    	new Thread(runnable).start();
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {  
        public void onServiceConnected(ComponentName className, IBinder binder) {  
            fetch = ((Fetch.MyBinder) binder).getService();
            Toast.makeText(Smsd.this, "Connected", Toast.LENGTH_SHORT).show();  
        }
  
        public void onServiceDisconnected(ComponentName className) {  
            fetch = null;
            Toast.makeText(Smsd.this, "Dis - Connected", Toast.LENGTH_SHORT).show();  
        }  
    };

	private void doBindService() {
		bindService(new Intent(this, Fetch.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void showServiceData() {
		if (fetch != null) {
			String s = fetch.getTheThing();
			Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
			tv.append(s + "\n");
		}
	}
}