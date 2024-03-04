package ru.pvolan.archsample.network.tools;

import okhttp3.Response;

public class PResponse {

    //For future use and connection incapsulation

    private Response response;

    public PResponse(Response response) {
        this.response = response;
    }

    Response getResponse() {
        return response;
    }

    public String getRequestUrl(){
        return response.request().url().toString();
    }
}
