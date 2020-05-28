package com.patrickgatewood.runelitetouchbar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import lombok.extern.slf4j.Slf4j;
import retrofit2.http.POST;

import java.io.IOException;

@Slf4j
public class HTTPClient {
    // TODO: - Get the port from the macOS process when it launches
    private static final String BASE_URL = "http://localhost:8080/";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build();
    static final TouchBarAPI touchBarAPI = retrofit.create(TouchBarAPI.class);

    static void killTouchBar() {
        touchBarAPI.killTouchBar().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                log.info("killed the touch bar!");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log.info("failed to kill the touch bar: " + t.toString());
            }
        });
    }
}

interface TouchBarAPI {
    @POST("killtouchbar")
    Call<ResponseBody> killTouchBar();
}
