package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachAPI;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseAPI;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculPresenter {
    private ICalculView vue;

    /**
     * Initialise la vue
     * @param vue
     */
    public CalculPresenter(ICalculView vue){
        this.vue = vue;
    }

    /**
     * Crée et sauvegarde un profil en fonction des paramètres indiqués avec la date du jour. Rempli la Vue des résultats
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe){
        Profil profil = new Profil(poids, taille, age, sexe, new Date());
        sauvegarderProfil(profil);
    }

    /**
     * Sauvegarde le profil envoyé en paramètre en appelant l'API
     * @param profil
     */
    private void sauvegarderProfil(Profil profil) {
        String profilJson = CoachAPI.getGson().toJson(profil);
        IRequestApi api = CoachAPI.getRetrofit().create(IRequestApi.class);
        Call<ResponseAPI<Integer>> call = api.creerProfil(profilJson);
        call.enqueue(new Callback<ResponseAPI<Integer>>() {
            @Override
            public void onResponse(Call<ResponseAPI<Integer>> call, Response<ResponseAPI<Integer>> response) {
                Log.d("API", "code : " + response.body().getCode() +
                        " message : " + response.body().getMessage() +
                        " result : " + response.body().getResult()
                );
                if (response.isSuccessful() && response.body().getResult() == 1) {
                    vue.afficherResultat(profil.getImage(), profil.getImg(), profil.getMessage(), profil.normal());
                } else {
                    Log.e("API", "Erreur API : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<Integer>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });
    }

    /**
     * Appelle l'API puis envoi à la vue le dernier profil enregistré dans la BdD
     */
    public void chargerProfil() {
        IRequestApi api = CoachAPI.getRetrofit().create(IRequestApi.class);
        Call<ResponseAPI<List<Profil>>> call = api.getProfils();
        call.enqueue(new Callback<ResponseAPI<List<Profil>>>() {
            @Override
            public void onResponse(Call<ResponseAPI<List<Profil>>> call, Response<ResponseAPI<List<Profil>>> response) {
                List<Profil> profils = response.body().getResult();
                if (response.isSuccessful()) {
                    if (profils != null && !profils.isEmpty()) {
                        Profil profil = Collections.max(
                                profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );
                        vue.remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
                    }
                } else {
                    Log.e("API", "Erreur API : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI<List<Profil>>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });
    }

}
