package ru.pvolan.archsample.network.exception;


import android.content.Context;

import ru.pvolan.archsample.network.R;


public class ConnectionFailedException extends NetworkException {

    public ConnectionFailedException(Context appContext, String message) {
        super(appContext.getString(R.string.connection_failed_exception_message)+message);
    }

    public ConnectionFailedException(Context appContext, Throwable throwable) {
        super(appContext.getString(R.string.connection_failed_exception_message)+throwable.getMessage(), throwable);
    }

}
