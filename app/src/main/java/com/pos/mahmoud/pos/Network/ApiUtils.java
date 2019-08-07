package com.pos.mahmoud.pos.Network;


import android.content.Context;

public class ApiUtils {
    private ApiUtils() {}

  // public static final String  URL= "http://192.168.1.100:8080/";

    public static APIService getAPIService(String BASE_URL, Context c) {

        return RetrofitClient.getClient(BASE_URL,c).create(APIService.class);
    }

}
