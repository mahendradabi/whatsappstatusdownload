package com.money.app.servers;

/*
 * all method declare to access the api form backend side
 * */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Apis {
    @GET
    Call<String> getCurrencydata(@Url String url);


}
