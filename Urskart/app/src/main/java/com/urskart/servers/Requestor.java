package com.urskart.servers;



/**
 * Created by xyz on 03-01-2018.
 */

public class Requestor {
    private int code;
    ServerResponse serverResponse;
    public static Apis apis;

    public Requestor(int code, ServerResponse serverResponse) {
        this.code = code;
        this.serverResponse = serverResponse;
        this.apis =RetrofitClient.getApiClient();
    }




}
