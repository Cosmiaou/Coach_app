package com.example.coach.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoachAPI {
    private static final String API_URL = "http://10.0.2.2/rest_coach/";
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * Constructeur. Vérifie si retrofit est toujours égal à null. Si oui, le crée en fonction des éléments predéfinis
     * Dans tous les cas, retourne retrofit
     * @return Retrofit
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     * Retourne gson
     * @return Gson
     */
    public static Gson getGson() {
        return gson;
    }
}
