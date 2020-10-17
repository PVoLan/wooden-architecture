package ru.pvolan.archsample.network.tools;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.pvolan.archsample.network.R;
import ru.pvolan.archsample.network.exception.ConnectionFailedException;
import ru.pvolan.archsample.network.exception.NetworkException;
import ru.pvolan.tools.log.ALog;


public class ExecuteHelper {

    private OkHttpClient okHttpClient;
    private Context appContext;

    public ExecuteHelper(Context appContext) {
        this.appContext = appContext;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(@NonNull String message) {
                ALog.log(message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }


    public PResponse executeSync(PRequest request) throws NetworkException {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!isOnline ()) throw new NetworkException ( appContext.getString (R.string.no_internet_connection) );

        try {
            Response response = okHttpClient.newCall(request.getRequest()).execute();
            return new PResponse(response);
        } catch (IOException e) {
            throw new ConnectionFailedException(appContext, e);
        }
    }


    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}


