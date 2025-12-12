package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachAPI;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseAPI;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoPresenter {
    private IHistoView vue;

    public HistoPresenter(IHistoView vue) {
        this.vue = vue;
    }

    public void chargerProfils() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> profils) {
                if (profils != null && !profils.isEmpty()) {
                    Collections.sort(profils, (p1, p2) -> p2.getDateMesure().compareTo(p1.getDateMesure()));
                    vue.afficherListe(profils);
                } else {
                    vue.afficherMessage("Echec du chargement des profils");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("Echec du chargement des profils");
            }
        });
    }

    public void supprProfil(Profil profil, ICallbackApi<Void> callback) {
        String profilJson = CoachAPI.getGson().toJson(profil);
        HelperApi.call(HelperApi.getApi().supprProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                if (result == 1) {
                    callback.onSuccess(null);
                    vue.afficherMessage("Profil supprimé");
                } else {
                    vue.afficherMessage("Echec de la suppression du profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("Echec de la suppression du profil");
            }
        });
    }

    public void transfertProfil(Profil profil){
        vue.transfertProfil(profil);
    }
}
