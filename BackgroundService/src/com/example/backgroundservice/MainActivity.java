package com.example.backgroundservice;

import com.example.backgroundservice.helper.LocationHelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private BroadcastReceiver mBroadcastReceiver;
	public static final String LOCATION_UPDATE = "locationUpdate";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button startButton = (Button) findViewById(R.id.button_start);
		startButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent locationService = new Intent(MainActivity.this,
						LocationService.class);
				MainActivity.this.startService(locationService);
				return false;
			}
		});

		Button stopButton = (Button) findViewById(R.id.button_stop);
		stopButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent locationService = new Intent(MainActivity.this,
						LocationService.class);
				MainActivity.this.stopService(locationService);
				return false;
			}
		});
	}

	public void onResume() {
		super.onResume();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(LOCATION_UPDATE);

		mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				Location location = intent.getExtras().getParcelable(LocationHelper.LOCATION_OBJECT);
				Log.d("test", "test");
			}
		};

		this.registerReceiver(mBroadcastReceiver, intentFilter);
	}

	public void onPause() {
		super.onPause();
		this.unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
