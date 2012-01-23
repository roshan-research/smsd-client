package ir.sobhe.smsd;

import java.util.Timer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Smsd extends Activity {
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
}