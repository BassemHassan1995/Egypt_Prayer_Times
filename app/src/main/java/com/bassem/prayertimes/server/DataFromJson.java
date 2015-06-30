package com.bassem.prayertimes.server;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.bassem.prayertimes.model.DayPrayers;
import com.bassem.prayertimes.model.Prayer;
import com.bassem.prayertimes.model.TaskParameters;

/**
 * Created by Bassem on 6/23/2015.
 */
public class DataFromJson {
	final String PRAYER_FAJR = "fajr";
	final String PRAYER_SUNRISE = "sunrise";
	final String PRAYER_ZUHR = "zuhr";
	final String PRAYER_ASR = "asr";
	final String PRAYER_MAGHRIB = "maghrib";
	final String PRAYER_ISHA = "isha";

	public ArrayList<DayPrayers> getDataFromJson(String prayertimeJsonStr, TaskParameters taskParameters)
			throws JSONException {

		JSONObject prayersJson = new JSONObject(prayertimeJsonStr);

		ArrayList<DayPrayers> monthPrayerTimes = new ArrayList<DayPrayers>();

		for (int i = 1; i <= prayersJson.length(); i++) {
			DayPrayers dayPrayers = new DayPrayers();
			ArrayList<Prayer> prayers = new ArrayList<Prayer>();

			dayPrayers.day = i;
			dayPrayers.month = taskParameters.month;
			dayPrayers.year = taskParameters.year;
			dayPrayers.lat = taskParameters.city.latitude;
			dayPrayers.lng = taskParameters.city.longitude;
			dayPrayers.gmt = taskParameters.gmt;

			String index = String.valueOf(i);
			JSONObject dayPrayersObject = prayersJson.getJSONObject(index);

			for (int j = 0; j < 6; j++) {
				Prayer prayer = new Prayer();
				prayer.index = j;

				switch (j) {
				case 0: {
					prayer.prayerTime = dayPrayersObject.getString(PRAYER_FAJR);
					break;
				}
				case 1: {
					prayer.prayerTime = dayPrayersObject.getString(PRAYER_SUNRISE);
					break;
				}

				case 2: {
					prayer.prayerTime = dayPrayersObject.getString(PRAYER_ZUHR);
					break;
				}

				case 3: {
					prayer.prayerTime = dayPrayersObject.getString(PRAYER_ASR);
					break;
				}

				case 4: {
					prayer.prayerTime = dayPrayersObject.getString(PRAYER_MAGHRIB);
					break;
				}

				case 5: {
					prayer.prayerTime = dayPrayersObject.getString(PRAYER_ISHA);
					break;
				}

				}
				prayers.add(prayer);
			}
			dayPrayers.prayers = prayers;
			monthPrayerTimes.add(dayPrayers);
		}

		return monthPrayerTimes;
	}
}
