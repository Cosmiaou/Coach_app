package com.example.coach.api;

import com.example.coach.model.Profil;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRequestApi {
    @GET("profil")
    Call<ResponseAPI<List<Profil>>> getProfils();

    @FormUrlEncoded
    @POST("profil")
    Call<ResponseAPI<Integer>> creerProfil(@Field("champs") String profilJson);

    @DELETE("profil/{champs}")
    Call<ResponseAPI<Integer>> supprProfil(@Path(value = "champs", encoded = true) String profilJson);
}
