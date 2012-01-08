package ir.sobhe.smsd;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabWidget extends TabActivity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		intent = new Intent().setClass(this, Smsd.class);
		
		spec = tabHost.newTabSpec("smsd").setIndicator("smsd", res.getDrawable(R.drawable.ic_tab_smsd)).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, Status.class);
		spec = tabHost.newTabSpec("status").setIndicator("Status", res.getDrawable(R.drawable.ic_tab_status)).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, Settings.class);
		spec = tabHost.newTabSpec("settings").setIndicator("Settings", res.getDrawable(R.drawable.ic_tab_settings)).setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(2);
	}
}
