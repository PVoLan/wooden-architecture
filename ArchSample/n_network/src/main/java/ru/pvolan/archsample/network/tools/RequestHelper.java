package ru.pvolan.archsample.network.tools;



import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


public class RequestHelper {

    private static final String apiBaseUrl = "http://api.openweathermap.org/data/2.5/";
    private static final String apiKey = "4cdec8dceebef51c985972f2133aa6f5";


    private Context appContext;

    public RequestHelper (Context appContext) {
        this.appContext = appContext;
    }


    public PRequest createGetRequest(String path, PParam... params){

        HttpUrl.Builder httpBuilder = HttpUrl.parse(apiBaseUrl + path).newBuilder();
        for (PParam param : params) {
            httpBuilder.addQueryParameter(param.getName(),param.getValue());
        }
        httpBuilder.addQueryParameter("appid", apiKey);

        Request.Builder request = new Request.Builder()
            .method("GET", null)
            .url(httpBuilder.build());

        return new PRequest(request.build());
    }

}




