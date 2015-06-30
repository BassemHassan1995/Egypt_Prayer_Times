package com.bassem.prayertimes.UI;

import android.os.AsyncTask;

import com.bassem.prayertimes.database.DatabaseImplementation;
import com.bassem.prayertimes.model.DayPrayers;
import com.bassem.prayertimes.model.TaskParameters;
import com.bassem.prayertimes.server.DataFromJson;
import com.bassem.prayertimes.server.ServerImplementation;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Bassem on 6/19/2015.
 */

public class PrayersUpdateTask extends AsyncTask<Void, Void, List<DayPrayers>> {

	TaskParameters taskParameters;
	Controller controller;

	PrayersUpdateTask(Controller controller, TaskParameters taskParameters) {
		this.controller = controller;
		this.taskParameters = taskParameters;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		controller.taskStart();
	}

	@Override
	protected List<DayPrayers> doInBackground(Void... params) {
		List<DayPrayers> monthPrayers;
		monthPrayers = DatabaseImplementation.query(controller.getContext(), taskParameters.month, taskParameters.year,
				taskParameters.city.latitude, taskParameters.city.longitude, taskParameters.gmt);

		if (monthPrayers == null || monthPrayers.isEmpty()) {

			DataFromJson dataFromJson = new DataFromJson();
			ServerImplementation json = new ServerImplementation(taskParameters);
			String prayertimeJsonStr = json.getJsonStr(taskParameters);

			try {

				monthPrayers = dataFromJson.getDataFromJson(prayertimeJsonStr, taskParameters);
				DatabaseImplementation.save(controller.getContext(), monthPrayers);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return monthPrayers;
	}

	@Override
	protected void onPostExecute(List<DayPrayers> dayPrayers) {
		super.onPostExecute(dayPrayers);
		controller.taskFinished(dayPrayers);
	}
}
