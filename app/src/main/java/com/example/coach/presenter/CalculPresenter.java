package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachAPI;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
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
     *
     * @param vue
     */
    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
    }

    /**
     * Crée et sauvegarde un profil en fonction des paramètres indiqués avec la date du jour. Rempli la Vue des résultats
     *
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        Profil profil = new Profil(poids, taille, age, sexe, new Date());
        vue.afficherResultat(profil.getImage(), profil.getImg(), profil.getMessage(), profil.normal());
        sauvegarderProfil(profil);
    }

    /**
     * Sauvegarde le profil envoyé en paramètre en appelant l'API
     *
     * @param profil
     */
    private void sauvegarderProfil(Profil profil) {
        String profilJson = CoachAPI.getGson().toJson(profil);
        HelperApi.call(HelperApi.getApi().creerProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                if (result == 1) {
                    vue.afficherMessage("Profil sauvegardé");
                } else {
                    vue.afficherMessage("Echec de la sauvegarde du profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("Echec de la sauvegarde du profil");
            }
        });
    }

    /**
     * Appelle l'API puis envoi à la vue le dernier profil enregistré dans la BdD
     */
    public void chargerProfil() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> profils) {
                if (profils != null && !profils.isEmpty()) {
                    Profil profil = Collections.max(
                            profils,
                            (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                    );
                    vue.remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
                } else {
                    vue.afficherMessage("Echec de la récupération du dernier profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("Echec de la récupération du dernier profil");
            }
        });
    }
}
