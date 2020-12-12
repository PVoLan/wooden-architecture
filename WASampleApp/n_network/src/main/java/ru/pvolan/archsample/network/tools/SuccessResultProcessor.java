package ru.pvolan.archsample.network.tools;


import org.json.JSONException;
import org.json.JSONObject;


public abstract class SuccessResultProcessor<TResult> {
    public abstract TResult processResult(JSONObject jsonObject) throws JSONException;
}
