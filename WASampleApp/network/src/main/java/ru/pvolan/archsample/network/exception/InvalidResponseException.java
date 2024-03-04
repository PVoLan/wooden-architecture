package ru.pvolan.archsample.network.exception;


import android.content.Context;

import ru.pvolan.archsample.network.R;


public class InvalidResponseException extends NetworkException {
    public InvalidResponseException(Context appContext, String url, String detailMessage) {
        super(appContext.getString(R.string.invalid_response_exception_message, url, detailMessage));
    }

    public InvalidResponseException(Context appContext, String url, Throwable throwable) {
        super(appContext.getString(R.string.invalid_response_exception_message, url, throwable.getMessage()), throwable);
    }

}
