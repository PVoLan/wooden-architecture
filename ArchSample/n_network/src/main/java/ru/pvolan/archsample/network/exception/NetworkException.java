package ru.pvolan.archsample.network.exception;

import android.content.Context;
import ru.pvolan.archsample.network.R;

//Base class for all exceptions which can be thrown from this module
public class NetworkException extends Exception{

    public NetworkException(Context appContext, Throwable inner){
        super(appContext.getString(R.string.network_error, inner.getMessage()), inner);
    }

    public NetworkException(String message, Throwable inner){
        super(message, inner);
    }

    public NetworkException(String message){
        super(message, null);
    }

}