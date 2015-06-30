package com.bassem.prayertimes.UI;

import android.annotation.TargetApi;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;

import com.bassem.prayertimes.database.DatabaseImplementation;
import com.bassem.prayertimes.model.DayPrayers;
import com.bassem.prayertimes.model.TaskParameters;
import com.bassem.prayertimes.server.DataFromJson;
import com.bassem.prayertimes.server.ServerImplementation;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Bassem on 6/28/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class loader extends AsyncTaskLoader<List<DayPrayers>> {

	Controller controller;
	TaskParameters taskParameters;

	public loader(Context context, Controller controller, TaskParameters taskParameters) {
		super(context);
		this.controller = controller;
		this.taskParameters = taskParameters;
	}

	public loader(Context context) {
		super(context);
	}

	@Override
	public List<DayPrayers> loadInBackground() {
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
}
