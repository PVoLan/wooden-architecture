package ru.pvolan.archsample.network.tools;

import okhttp3.Request;

public class PRequest {

    //For future use and connection incapsulation

    private Request request;

    public PRequest(Request request) {
        this.request = request;
    }


    Request getRequest() {
        return request;
    }
}
