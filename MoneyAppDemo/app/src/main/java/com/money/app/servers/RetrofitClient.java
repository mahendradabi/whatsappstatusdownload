package com.money.app.servers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xyz on 11-12-2017.
 */

public class RetrofitClient {
    public static Retrofit retrofit = null;
    public static Apis apis;


    public static Retrofit getMyClient() {
        if (retrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            //Create a new Interceptor.
            Interceptor headerAuthorizationInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    okhttp3.Request request = chain.request();
                    Headers headers = request.headers().newBuilder().add("Authorization", "Bearer 12fa84c4d3cc2b50ddc8d76ed3781b07-4f0cd7a9ccaef0a0515d8732d0531380").build();
                    request = request.newBuilder().headers(headers).build();
                    return chain.proceed(request);
                }
            };
            //Add the interceptor to the client builder.
            clientBuilder.addInterceptor(headerAuthorizationInterceptor);
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.HOST_URL)
                   .client(clientBuilder.build())
                    .build();
        }
        return retrofit;
    }

    public static Apis getApiClient() {
        if (apis == null)
            apis = RetrofitClient.getMyClient().create(Apis.class);
        return apis;
    }


}
