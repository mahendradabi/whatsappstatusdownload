package heartbeatofindia.heartbeatofindia.servers;

/*
 * all method declare to access the api form backend side
 * */


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Apis {

    @FormUrlEncoded
    @POST(Constant.PATH+"getcategory")
    Call<String> getCategory(@Field("parent_id") String parent_id);

    @FormUrlEncoded
    @POST(Constant.PATH+"getNewsPost")
    Call<String> getNewsPost(@Field("category_id") String parent_id);



}
