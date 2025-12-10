package com.example.coach.presenter;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Date;

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
     * Crée un profil en fonction des paramètres indiqués avec la date du jour. Rempli la Vue des résultats
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe){
        Profil profil = new Profil(poids, taille, age, sexe, new Date());
        vue.afficherResultat(profil.getImage(), profil.getImg(), profil.getMessage(), profil.normal());
    }

}
