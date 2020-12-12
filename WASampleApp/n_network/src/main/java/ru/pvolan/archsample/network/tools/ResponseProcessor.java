package ru.pvolan.archsample.network.tools;


import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.pvolan.archsample.network.exception.ConnectionFailedException;
import ru.pvolan.archsample.network.exception.ErrorCodeRetrievedException;
import ru.pvolan.archsample.network.exception.InvalidResponseException;
import ru.pvolan.archsample.network.exception.NetworkException;

import ru.pvolan.tools.json.JsonHelper;
import ru.pvolan.tools.string.StringHelper;


public class ResponseProcessor
{

    private Context appContext;

    public ResponseProcessor(Context appContext) {
        this.appContext = appContext;
    }

    //******************************
    //Main method

    public <TResult> TResult processResponse(PResponse response, SuccessResultProcessor<TResult> successResultProcessor)
            throws NetworkException {

        Response okHttpResponse = response.getResponse();
        String requestUrl = response.getRequestUrl();

        try
        {
            int actualStatusCode = okHttpResponse.code();

            String strContent = silentlyGetContentOrEmpty(okHttpResponse, requestUrl);
            JSONObject jsonContent = parseResponseContent(strContent, requestUrl);

            if(actualStatusCode != 200){
                int errorCode = JsonHelper.getInt(jsonContent, "cod");
                String errorMessage = JsonHelper.getStringOrEmptyString(jsonContent, "message");

                throw new ErrorCodeRetrievedException(appContext, requestUrl, errorCode, errorMessage, jsonContent);
            }

            return successResultProcessor.processResult(jsonContent);
        }
        catch (JSONException e)
        {
            throw new InvalidResponseException(appContext, requestUrl, e);
        }
    }


    //******************************
    //Main method parts


    private String silentlyGetContentOrEmpty(Response okHttpResponse, String requestUrl) throws NetworkException {

        ResponseBody body = okHttpResponse.body();
        if(body == null) throw new InvalidResponseException(appContext, requestUrl, "No body presented in response");
        try {
            return body.string();
        } catch (IOException e) {
            throw new ConnectionFailedException(appContext, e);
        }
    }


    @NonNull
    private JSONObject parseResponseContent(String strContent, String requestUrl) throws InvalidResponseException {

        if(StringHelper.isNullOrEmptyOrWhitespace(strContent)) throw new InvalidResponseException(appContext, requestUrl, "Empty response");
        strContent = strContent.trim();

        try {
            return JsonHelper.toJsonObject(strContent);
        } catch (JSONException e) {
            throw new InvalidResponseException(appContext, requestUrl, e);
        }
    }

}
