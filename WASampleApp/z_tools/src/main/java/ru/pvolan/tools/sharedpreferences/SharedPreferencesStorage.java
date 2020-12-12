package ru.pvolan.tools.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedPreferencesStorage {

    private SharedPreferences preferences;

    public SharedPreferencesStorage(Context appContext, String preferencesFileName) {
        preferences = appContext.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
    }


    public String getString(String key) {
        return getString(key, null);
    }


    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }


    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }


    public int getInt(String key) {
        return getInt(key, -1);
    }


    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }


    public long getLong(String key) {
        return getLong(key, -1);
    }


    public void saveLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }


    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public float getFloat(String key) {
        return getFloat(key, 0f);
    }


    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }


    public void saveFloat(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, new HashSet<String>());
    }


    public void saveStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


    public boolean containsKey(String key) {
        return preferences.contains(key);
    }


    public void deleteKey(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }



    /**
     * @param key
     * @return empty list if not exist
     */
    public List<String> getStringList(String key)
    {
        if(!preferences.contains(key)) return new ArrayList<>();
        String strVal = preferences.getString(key, "[]");

        try {
            JSONArray jsonArray = new JSONArray(strVal);
            List<String> res = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                res.add(jsonArray.getString(i));
            }
            return res;

        } catch (JSONException e) {
            throw new RuntimeException("Invalid value in storage");
        }
    }


    public void saveStringList(String key, List<String> value)
    {
        if(value == null) throw new IllegalArgumentException("value can't be null");

        JSONArray jsonArray = new JSONArray();
        for (String s : value) {
            jsonArray.put(s);
        }
        String strVal = jsonArray.toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, strVal);
        editor.commit();
    }


}
