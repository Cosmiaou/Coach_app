package com.example.coach.contract;

public interface ICalculView {
    /**
     * Méthode abstraite d'affichage des résultats
     * @param image
     * @param img
     * @param message
     * @param normal
     */
    void afficherResultat(String image, double img, String message, boolean normal);

    /**
     * Méthode abstraite pour transférer les informations
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);

}
