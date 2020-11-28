package ru.pvolan.archsample.storage.settings;

import android.content.Context;

import ru.pvolan.tools.sharedpreferences.SharedPreferencesStorage;

public class SettingsStorage {

    private SharedPreferencesStorage sp;

    private static final String MIN_GOOD_TEMP_KEY = "MIN_GOOD_TEMP_KEY";
    private static final String MAX_GOOD_TEMP_KEY = "MAX_GOOD_TEMP_KEY";

    public SettingsStorage(Context appContext) {
        sp = new SharedPreferencesStorage(appContext, "SettingsStorage");
    }



    public int readMinGoodTemperature() {
        if(!sp.containsKey(MIN_GOOD_TEMP_KEY)){
            return 10; //Default
        }

        return sp.getInt(MIN_GOOD_TEMP_KEY);
    }

    public void saveMinGoodTemperature(int value){
        sp.saveInt(MIN_GOOD_TEMP_KEY, value);
    }



    public int readMaxGoodTemperature() {
        if(!sp.containsKey(MAX_GOOD_TEMP_KEY)){
            return 20; //Default
        }

        return sp.getInt(MAX_GOOD_TEMP_KEY);
    }

    public void saveMaxGoodTemperature(int value){
        sp.saveInt(MAX_GOOD_TEMP_KEY, value);
    }
}
