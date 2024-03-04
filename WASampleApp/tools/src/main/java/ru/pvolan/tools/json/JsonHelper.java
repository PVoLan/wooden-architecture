package ru.pvolan.tools.json;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class JsonHelper
{

    public static String toString(JSONObject object)
    {
        return object.toString();
    }


    public static JSONObject toJsonObject(String jsonString) throws JSONException
    {
        if (jsonString == null) return null;
        return new JSONObject(jsonString);
    }


    public static JSONArray toJsonArray(String jsonString) throws JSONException
    {
        if (jsonString == null) return null;
        return new JSONArray(jsonString);
    }


    public static int getInt(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getInt(key);
    }


    public static Integer getIntOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : jsonObject.optInt(key);
    }


    public static int getIntOrZero(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? 0 : jsonObject.optInt(key);
    }


    public static long getLong(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getLong(key);
    }


    public static Long getLongOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : jsonObject.optLong(key);
    }


    public static long getLongOrZero(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? 0 : jsonObject.optLong(key);
    }


    public static Object getObjectOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : jsonObject.get(key);
    }


    public static JSONObject getJsonObjectOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : jsonObject.optJSONObject(key);
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getJSONObject(key);
    }


    public static String getStringOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : jsonObject.optString(key);
    }

    public static String getString(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getString(key);
    }


    public static boolean getBoolean(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getBoolean(key);
    }


    public static String getStringOrNullNoEmptyString(JSONObject jsonObject, String key) throws JSONException
    {
        if (jsonObject.isNull(key))
        {
            return null;
        }
        String string = jsonObject.optString(key);
        return string.equals("") ? null : string;
    }


    public static JSONArray getJsonArray(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getJSONArray(key);
    }

    public static JSONArray getJsonArrayOrEmptyArray(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? new JSONArray() : jsonObject.optJSONArray(key);
    }


    public static List<String> getStringListFromJsonArray(JSONArray jsonArray) throws JSONException
    {
        List<String> strings = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            String string = jsonArray.getString(i);
            strings.add(string);
        }

        return strings;
    }


    public static <T> List<T> getListFromJsonArray(JSONArray jsonArray, JsonParser<T> parser) throws JSONException
    {
        List<T> strings = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            strings.add(parser.fromJson(object));
        }

        return strings;
    }

    public interface JsonParser<T>{
        T fromJson(JSONObject object) throws JSONException;
    }


    public static <T> JSONArray listToJsonArray(List<T> list, JsonGenerator<T> generator) throws JSONException
    {
        JSONArray array = new JSONArray();
        for (T item : list) {
            array.put(generator.toJson(item));
        }
        return array;
    }

    public interface JsonGenerator<T>{
        JSONObject toJson(T object) throws JSONException;
    }


    public static <T> SparseArray<T> getSparseArrayFromJsonArray(JSONArray jsonArray, JsonSparseArrayParser<T> parser) throws JSONException
    {
        SparseArray<T> result = new SparseArray<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            result.put(parser.keyFromJson (object), parser.valueFromJson (object));
        }

        return result;
    }

    public interface JsonSparseArrayParser<T>{
        T valueFromJson(JSONObject object) throws JSONException;
        int keyFromJson(JSONObject object) throws JSONException;
    }



    public static <T> JSONArray sparseArrayToJsonArray(SparseArray<T> sparseArray, JsonSparseArrayGenerator<T> generator) throws JSONException
    {
        JSONArray array = new JSONArray();
        for (int i = 0; i<sparseArray.size (); i++) {
            array.put(generator.toJson( sparseArray.keyAt (i), sparseArray.valueAt (i)  ));
        }
        return array;
    }

    public interface JsonSparseArrayGenerator<T>{
        JSONObject toJson(int key, T value) throws JSONException;
    }



    public static String getStringOrEmptyString(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? "" : jsonObject.optString(key);
    }


    public static void putObjectOrNull(JSONObject jsonObject, Object value, String key) throws JSONException
    {
        Object putObject = value != null ? value : JSONObject.NULL;
        jsonObject.put(key, putObject);
    }


    public static void putIntOrNullIfZero(JSONObject jsonObject, int value, String key) throws JSONException
    {
        Object putObject = value != 0 ? Integer.valueOf(value) : JSONObject.NULL;
        jsonObject.put(key, putObject);
    }


    public static BigDecimal getBigDecimalOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        if (jsonObject.isNull(key)) return null;
        String valString = jsonObject.optString(key);
        return valString == null ? null : new BigDecimal(valString);
    }


    public static double getDouble(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return jsonObject.getDouble(key);
    }


    public static Double getDoubleOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : jsonObject.getDouble(key);
    }


    public static float getFloat(JSONObject jsonObject, String key) throws JSONException
    {
        if(jsonObject.isNull(key)) throw new JSONException("Value is null for key: " + key);
        return (float) jsonObject.getDouble(key);
    }


    public static Float getFloatOrNull(JSONObject jsonObject, String key) throws JSONException
    {
        return jsonObject.isNull(key) ? null : (float)jsonObject.getDouble(key);
    }



    public static List<String> jsonStringArrayToList(JSONArray value) throws JSONException
    {
        if (value == null) return null;
        List<String> result = new ArrayList<String>(value.length());
        for (int i = 0; i < value.length(); i++)
        {
            String curItem = value.getString(i);
            result.add(curItem);
        }
        return result;
    }



    public static JSONArray stringListToJsonArray(List<String> list)
    {
        JSONArray array = new JSONArray();
        for (String str : list)
        {
            array.put(str);
        }

        return array;
    }


    public static boolean isExactlyNull(JSONObject jsonObject, String key) throws JSONException
    {
        if (!jsonObject.has(key)){
            throw new JSONException("No value for key " + key);
        }

        return(jsonObject.isNull(key));
    }


    public static boolean isNullOrNotPresented(JSONObject jsonObject, String key)
    {
        return(jsonObject.isNull(key));
    }

}
