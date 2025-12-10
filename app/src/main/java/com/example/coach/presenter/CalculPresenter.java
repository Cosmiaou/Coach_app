package com.example.coach.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coach.contract.ICalculView;
import com.example.coach.data.ProfilDAO;
import com.example.coach.model.Profil;
import com.google.gson.Gson;

import java.util.Date;

public class CalculPresenter {
    private final ICalculView vue;
    private final ProfilDAO profilDao;

    /**
     * Initialise la vue et la classe d'accès aux données
     * @param vue
     * @param context
     */
    public CalculPresenter(ICalculView vue, Context context){
        this.vue = vue;
        this.profilDao = new ProfilDAO(context);
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
        vue.afficherResultat(profil.getImage(), profil.getImg(), profil.getMessage(), profil.normal());
        profilDao.insertProfil(profil);
    }

    /**
     * Demande à la vue de charger le dernière profil enregistré
     */
    public void chargerProfil() {
        Profil profil = profilDao.getLastProfil();
        if (profil != null) {
            vue.remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        }
    }

}
