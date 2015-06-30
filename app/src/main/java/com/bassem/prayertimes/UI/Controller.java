package com.bassem.prayertimes.UI;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.format.Time;

import com.bassem.prayertimes.Settings.SettingsManager;
import com.bassem.prayertimes.model.DayPrayers;
import com.bassem.prayertimes.model.Prayer;
import com.bassem.prayertimes.model.TaskParameters;

/**
 * Created by Bassem on 6/18/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Controller extends Fragment implements LoaderCallbacks<List<DayPrayers>> {

	private static final int id = 0;
	static int currentIndex;
	static int currentMonth;
	static int currentYear;
	ArrayList<DayPrayers> MonthPrayers = new ArrayList<DayPrayers>();

	MainActivity activity;
	PrayersUpdateTask prayersUpdateTask;

	// TaskParameters taskParameters;

	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}

	public Context getContext() {
		return activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();

		currentIndex = today.monthDay - 1;
		currentMonth = today.month + 1;
		currentYear = today.year;

		// prayersUpdateTask = new PrayersUpdateTask(this , taskParameters);
		activity.getSupportLoaderManager().restartLoader(id, null, this).forceLoad();
	}

	String getDate() {
		if (!MonthPrayers.isEmpty()) {
			DayPrayers dayPrayer = MonthPrayers.get(currentIndex);
			return dayPrayer.day + "/" + dayPrayer.month + "/" + dayPrayer.year;
		}

		return "";
	}

	ArrayList<Prayer> getPrayers() {

		if (!MonthPrayers.isEmpty()) {
			return MonthPrayers.get(currentIndex).prayers;
		}

		return null;
	}

	void goNextDay() {
		if (currentIndex == MonthPrayers.size() - 1) {
			currentIndex = 0;
			currentMonth++;
			if (currentMonth == 12) {
				currentMonth = 1;
				currentYear++;
			}

			// PrayersUpdateTask nextPrayersUpdateTask = new
			// PrayersUpdateTask(this , taskParameters);
			activity.getSupportLoaderManager().restartLoader(id, null, this).forceLoad();

		} else {
			currentIndex++;
			activity.bindViews();
		}
	}

	void goPrevDay() {
		if (currentIndex == 0) {
			currentIndex--;
			currentMonth--;
			if (currentMonth == 1) {
				currentMonth = 12;
				currentYear--;
			}

			// PrayersUpdateTask prevPrayersUpdateTask = new
			// PrayersUpdateTask(this , taskParameters);
			activity.getSupportLoaderManager().restartLoader(id, null, this).forceLoad();
		} else {
			currentIndex--;
			activity.bindViews();
		}

	}

	void taskStart() {
		activity.showLoading();
	}

	void taskFinished(List<DayPrayers> prayers) {
		MonthPrayers.clear();
		MonthPrayers.addAll(prayers);

		if (currentIndex == -1) {
			currentIndex = MonthPrayers.size() - 1;
		}

		activity.dismissLoading();
		activity.bindViews();
	}

	@Override
	public Loader<List<DayPrayers>> onCreateLoader(int i, Bundle bundle) {

		activity.showLoading();
		return new loader(getContext(), this, getTaskParameters());
	}

	@Override
	public void onLoadFinished(Loader<List<DayPrayers>> loader, List<DayPrayers> prayers) {
		MonthPrayers.clear();
		MonthPrayers.addAll(prayers);

		if (currentIndex == -1) {
			currentIndex = MonthPrayers.size() - 1;
		}

		activity.dismissLoading();
		activity.bindViews();
	}

	@Override
	public void onLoaderReset(Loader<List<DayPrayers>> loader) {

	}

	public void refresh() {
		activity.getSupportLoaderManager().restartLoader(id, null, this).forceLoad();

	}

	private TaskParameters getTaskParameters() {

		TaskParameters taskParameters = new TaskParameters();
		if (SettingsManager.isDayLightEnabled(getContext())) {
			taskParameters.gmt = 3;
		} else
			taskParameters.gmt = 2;
		/*
		 * taskParameters.lng = 31.286347; taskParameters.lat = 29.988779;
		 */
		taskParameters.city = SettingsManager.getCity(getContext());

		taskParameters.day = currentIndex + 1;
		taskParameters.month = currentMonth;
		taskParameters.year = currentYear;

		return taskParameters;
	}
}
