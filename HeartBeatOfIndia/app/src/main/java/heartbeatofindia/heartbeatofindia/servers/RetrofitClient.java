package heartbeatofindia.heartbeatofindia.servers;

import java.util.concurrent.TimeUnit;

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
          /*      OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

        httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "$2y$08$8dS/O4m9PF2f6pe4uaHE6eCMQnqxxF6gnIu4RxV3l.JlwUKeqEbf6").build();
                    return chain.proceed(request);
                }
            });*/
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constant.HOST_URL)
                  //  .client(httpClient)
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
