package com.bassem.prayertimes.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bassem.prayertimes.Location;

/**
 * Created by Bassem on 6/29/2015.
 */

public class SettingsManager {

    private static Location [] cities = {
        new Location(30.762296 , 29.696268),
        new Location(23.085540 , 32.808450),
        new Location(30.762296,29.696268),
        new Location(30.848099 ,30.343551),
        new Location(28.905778 , 30.573958),
        new Location(30.119799 , 31.537000),
        new Location(31.083202 ,31.491318),
        new Location(31.417539 , 31.814443),
        new Location(29.417017,30.712002),
        new Location(30.875356,31.033510),
        new Location(28.766622 ,29.232078),
        new Location(30.583093,32.265389),
        new Location(31.308544, 30.803947),
        new Location(29.569635,26.419389),
        new Location(28.250161,29.974053),
        new Location(30.597246,30.987632),
        new Location(24.545564,27.173532),
        new Location(30.608472,33.617577),
        new Location(31.075861,32.265389),
        new Location(30.329237,31.216847),
        new Location(26.051330,32.220006),
        new Location(25.107684,33.796461),
        new Location(30.732662,31.719546),
        new Location(26.501348,31.765136),
        new Location(29.310183,34.153195),
        new Location(29.368226,32.174605),
        new Location(25.743247,32.695547)
    };

    public static final String PREF_CITY_KEY = "pref_city_key";
    public static final String PREF_DAYLIGHT_KEY = "pref_daylightSaving_key";

    public static Location getCity(Context context) {
    	SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(context);
        return cities [Integer.parseInt(mSettings.getString(PREF_CITY_KEY, "0"))];
    }

    public static boolean isDayLightEnabled(Context context) {
    	SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(context);
        return mSettings.getBoolean(PREF_DAYLIGHT_KEY, false);
    }
}
