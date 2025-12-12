package com.example.coach.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperApi {
    private static final IRequestApi api = CoachAPI.getRetrofit().create(IRequestApi.class);

    public static IRequestApi getApi(){
        return api;
    }

    public static <T> void call(Call<ResponseAPI<T>> call, ICallbackApi<T> callback) {
        call.enqueue(new Callback<ResponseAPI<T>>() {
            @Override
            public void onResponse(Call<ResponseAPI<T>> call, Response<ResponseAPI<T>> response) {
                Log.d("API", "code : " + response.body().getCode() +
                        " message : " + response.body().getMessage() +
                        " result : " + response.body().getResult()
                );
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResult());
                } else {
                    callback.onError();
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<T>> call, Throwable throwable) {
                // traitements si pas ok
                Log.e("API", "Erreur API", throwable);
            }
        });
    }

}
