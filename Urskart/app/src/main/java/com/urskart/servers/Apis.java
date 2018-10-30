package com.urskart.servers;

/*
 * all method declare to access the api form backend side
 * */

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apis {

    @FormUrlEncoded
    @POST(Constant.PATH + "register")
    Call<String> register(@Field("first_name") String first_name,
                          @Field("last_name") String last_name,
                          @Field("phone_number") String phone_number,
                          @Field("username") String username,
                          @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Constant.PATH + "login")
    Call<String> login(@Field("username") String first_name,
                       @Field("password") String password
    );

    @GET(Constant.PATH + "getBanners")
    Call<String> getBanners();


    @GET(Constant.PATH + "getCategory")
    Call<String> getCategory();


    @FormUrlEncoded
    @POST(Constant.PATH + "getProducts")
    Call<String> getProducts(@Field("category_id") String category_id);


    @FormUrlEncoded
    @POST(Constant.PATH + "addToFavourite")
    Call<String> addToFavorite(@Field("user_id") String user_id,
                               @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST(Constant.PATH + "getFavourite")
    Call<String> getFavorite(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(Constant.PATH + "getProductsById")
    Call<String> getProductDetails(@Field("product_id") String product_id);

    @FormUrlEncoded
    @POST(Constant.PATH + "addToCart")
    Call<String> addToCart(@Field("user_id") String user_id,
                               @Field("product_id") String product_id);





}
