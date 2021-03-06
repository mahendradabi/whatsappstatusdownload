package heartbeatofindia.heartbeatofindia.servers;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xyz on 03-01-2018.
 */

public class Requestor {
    private int code;
    private ServerResponse serverResponse;
    public static Apis apis;
    private Class<?> classOf;

    public void setClassOf(Class<?> classOf) {
        this.classOf = classOf;
    }

    public Requestor(int code, ServerResponse serverResponse) {
        this.code = code;
        this.serverResponse = serverResponse;
        if (this.apis == null)
            this.apis = RetrofitClient.getApiClient();
    }



    private void handleSuccessResult(Response response) {
        switch (response.code()) {
            case Constant.REQUEST_OK:
                if (classOf != null) {
                    try {
                        Gson gson = new Gson();
                        Log.d("response_result",response.body().toString());
                        Object o = gson.fromJson(response.body().toString(), classOf);
                        serverResponse.success(o, this.code);
                    } catch (Exception ex) {
                        serverResponse.error(ex.toString(), this.code);
                    }
                } else
                    serverResponse.success(response.body(), this.code);
                break;

            case Constant.REQEST_NO_CONTENT:
                serverResponse.error("No content found " + response.code(), this.code);
                break;

            case Constant.REQUEST_BAD:
                serverResponse.error("Bad Request " + response.code(), this.code);
                break;


            case Constant.REQUEST_FORBIDDEN:
                serverResponse.error("Request Forbidden " + response.code(), this.code);
                break;

            case Constant.REQUEST_INTERNAL_SERVER_ERROR:
                serverResponse.error("Internal serever error " + response.code(), this.code);
                break;

            case Constant.REQUEST_PAGE_NOT_FOUND:
                serverResponse.error("Request url not found " + response.code(), this.code);
                break;

            default:
                serverResponse.error(response.errorBody().toString(), this.code);
                break;


        }
    }

    private void handleErrorResult(Throwable t) {
        serverResponse.error(t.getMessage(), this.code);
    }

    public void requestSendToServer(Call<String> callbackToServer) {
        callbackToServer.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                handleSuccessResult(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                handleErrorResult(t);
            }
        });
    }


    @NonNull
    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }
    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file) {
        // create RequestBody instance from file
        if (file == null) return null;
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


}
