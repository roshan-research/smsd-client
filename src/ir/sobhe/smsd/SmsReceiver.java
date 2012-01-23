package ir.sobhe.smsd;

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
		if (extras != null){
			Object[] pdus = (Object[]) extras.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i = 0; i < msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu( (byte[]) pdus[i]);
				str += "SMS from " + msgs[i].getOriginatingAddress();
				str += " : ";
				str += msgs[i].getMessageBody().toString();
				str += "\n";
			}
			Toast.makeText(context, str, Toast.LENGTH_LONG).show();
		}
	}
}
