package com.bassem.prayertimes.UI;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bassem.prayertimes.R;
import com.bassem.prayertimes.Settings.SettingsActivity;
import com.bassem.prayertimes.model.Prayer;

public class MainActivity extends FragmentActivity {

	TextView currentDate;
	TextView fajrtime;
	TextView sunrisetime;
	TextView duhrtime;
	TextView asrtime;
	TextView maghribtime;
	TextView ishatime;
	ArrayList<TextView> prayerTimes;

	Controller controller;
	final static String CONTROLLER_TAG = "Control";

	ProgressDialog progressDialog;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// addPreferencesFromResource(R.xml.settings);
		setContentView(R.layout.activity_main);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		controller = (Controller) getSupportFragmentManager().findFragmentByTag(CONTROLLER_TAG);
		if (controller == null) {
			controller = new Controller();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(controller, CONTROLLER_TAG);
			transaction.commit();
		}

		controller.setActivity(this);

		initViews();
		bindViews();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			// startActivity(new Intent(this , SettingsActivity.class));
			startActivityForResult(new Intent(this, SettingsActivity.class), 0);
			return true;
		} else if (id == R.id.qibla_activity) {
			startActivity(new Intent(this, QiblaActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void initViews() {

		currentDate = (TextView) findViewById(R.id.todayDate);

		fajrtime = (TextView) findViewById(R.id.fajrTime);
		sunrisetime = (TextView) findViewById(R.id.sunriseTime);
		duhrtime = (TextView) findViewById(R.id.dhuhrTime);
		asrtime = (TextView) findViewById(R.id.asrTime);
		maghribtime = (TextView) findViewById(R.id.maghribTime);
		ishatime = (TextView) findViewById(R.id.ishaTime);

		prayerTimes = new ArrayList<TextView>();

		prayerTimes.add(fajrtime);
		prayerTimes.add(sunrisetime);
		prayerTimes.add(duhrtime);
		prayerTimes.add(asrtime);
		prayerTimes.add(maghribtime);
		prayerTimes.add(ishatime);
	}

	public void bindViews() {

		currentDate.setText(controller.getDate());
		ArrayList<Prayer> prayers = controller.getPrayers();
		if (prayers != null) {
			for (int i = 0; i < prayers.size(); i++) {
				Prayer prayer = prayers.get(i);
				TextView prayerTime = prayerTimes.get(i);
				prayerTime.setText(prayer.prayerTime);
			}
		}
	}

	public void showLoading() {

		progressDialog.show();
	}

	public void dismissLoading() {
		progressDialog.dismiss();
	}

	public void next(View view) {
		controller.goNextDay();
	}

	public void prev(View view) {
		controller.goPrevDay();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == Activity.RESULT_OK) {
			controller.refresh();
		}
	}

}