package heartbeatofindia.heartbeatofindia.servers;

/*
 * all method declare to access the api form backend side
 * */


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Apis {

    @FormUrlEncoded
    @POST(Constant.PATH + "getcategory")
    Call<String> getCategory(@Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST(Constant.PATH + "getNewsPost")
    Call<String> getNewsPost(@Field("category_id") String parent_id);

    @FormUrlEncoded
    @POST(Constant.PATH + "registerUser")
    Call<String> registerUser(@Field("fname") String fname,
                              @Field("lname") String lname,
                              @Field("email") String email,
                              @Field("phone") String phone,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST(Constant.PATH + "userLogin")
    Call<String> userLoin(
            @Field("email") String email,
            @Field("password") String password);

    @GET(Constant.PATH+"getAllCategory")
    Call<String> getAllCategory();

    @GET(Constant.PATH+"addCategory")
    Call<String> addCategory();

    @FormUrlEncoded
    @POST(Constant.PATH+"getadbycategory")
    Call<String> getAdByCategory(@Field("category_id") String categoryid);

    @Multipart
    @POST(Constant.PATH+"insert_ad")
    Call<String> inserAd(@Part("categoryid") RequestBody categoryid,
                         @Part("url") RequestBody url,
                         @Part MultipartBody.Part user_pic );


}
