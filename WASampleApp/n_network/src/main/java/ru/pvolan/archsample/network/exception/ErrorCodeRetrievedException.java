package ru.pvolan.archsample.network.exception;


import android.content.Context;

import org.json.JSONObject;

import ru.pvolan.archsample.network.R;


public class ErrorCodeRetrievedException extends NetworkException {


    private int errorCode;
    private String errorMessage;
    private JSONObject response;


    public ErrorCodeRetrievedException(Context appContext, String url, int errorCode, String errorMessage, JSONObject response) {
        super(appContext.getString(R.string.unexpected_error_code_exception_message, url, errorCode, errorMessage));

        init(errorCode, errorMessage, response);
    }


    private void init(int errorCode, String errorMessage, JSONObject response) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.response = response;
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public JSONObject getResponse() {
        return response;
    }
}
