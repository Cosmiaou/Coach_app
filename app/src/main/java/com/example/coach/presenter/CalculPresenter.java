package com.example.coach.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.google.gson.Gson;

import java.util.Date;

public class CalculPresenter {
    private ICalculView vue;
    private static final String NOM_FICHIER = "coach_fichier";
    private static final String PROFIL_CLE = "profil_json";
    private Gson gson;
    private SharedPreferences prefs;

    /**
     * Initialise la vue et prépare la sauvegarde du profil
     * @param vue
     */
    public CalculPresenter(ICalculView vue, Context context){
        this.vue = vue;
        this.prefs = context.getSharedPreferences(NOM_FICHIER, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /**
     * Crée un profil en fonction des paramètres indiqués avec la date du jour. Rempli la Vue des résultats
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe){
        Profil profil = new Profil(poids, taille, age, sexe, new Date());
        vue.afficherResultat(profil.getImage(), profil.getImg(), profil.getMessage(), profil.normal());
        sauvegarderProfil(profil);
    }

    /**
     * Sauvegarde le profil envoyé dans un fichier Json
     * @param profil
     */
    private void sauvegarderProfil(Profil profil) {
        String json = gson.toJson(profil);
        prefs.edit().putString(PROFIL_CLE, json).apply();
    }

    public void chargerProfil() {
        String json = prefs.getString(PROFIL_CLE, null);
        if (json != null) {
            Profil profil = gson.fromJson(json, Profil.class);
            vue.remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        }
    }

}
