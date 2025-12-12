package com.example.coach.model;

import java.io.Serializable;
import java.util.Date;

public class Profil implements Serializable {
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;

    private static final String[] MESSAGE = {"trop faible :(", "normal :))", "trop élévé :("};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    private Date dateMesure;
    private final Integer poids;
    private final Integer taille;
    private final Integer age;
    private final Integer sexe;

    private transient double img;
    private transient int indice;

    /**
     * Constructeur. Crée le Profil avec les éléments indiqués et lance les calculs
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe, Date dateMesure) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = dateMesure;
        img = calculImg();
        calculIndice();
    }

    /**
     * Calcul l'IMG en fonction de la taille (convertie en mètre), de l'age, du poid et du sexe
     * @return double
     */
    private double calculImg() {
        double tailleM = taille / 100.0;
        return img = (1.2 * poids/(tailleM * tailleM)) + (0.23 * age) - (10.83 * sexe) - 5.4;
    }

    /**
     * Vérifie si l'indice de masse grasse est dans la moyenne ou non.
     * Est par défaut configuré pour une femme de poid normal.
     */
    private void calculIndice() {
        double min = MIN_FEMME;
        double max = MAX_FEMME;
        indice = 1;

        if (sexe == 1) {
            min = MIN_HOMME;
            max = MAX_HOMME;
        }

        if (img > max){
           indice = 2;
        } else if (img < min){
            indice = 0;
        }
    }

    /**
     * Retourne l'indice de masse grasse
     * @return img
     */
    public double getImg() {
        return calculImg();
    }

    /**
     * Retourne le message correspond à l'indice
     * @return MESSAGE[indice]
     */
    public String getMessage(){
        return MESSAGE[indice];
    }

    /**
     * Retourne le nom de l'image correspond à l'indice
     * @return IMAGE[INDICE]
     */
    public String getImage(){
        return IMAGE[indice];
    }

    /**
     * Si l'indice est positionné sur "normal" (1), retourne TRUE. Si non, retourne FALSE
     * @return boolean
     */
    public boolean normal() {
        return indice == 1;
    }

    /**
     * Retourne le poids
     * @return Integer
     */
    public Integer getPoids() {
        return poids;
    }

    /**
     * Retourne la taille
     * @return Integer
     */
    public Integer getTaille() {
        return taille;
    }

    /**
     * Retourne l'age
     * @return Integer
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Retourne le sexe
     * @return Integer
     */
    public Integer getSexe() {
        return sexe;
    }

    /**
     * Retourne la date
     * @return Date
     */
    public Date getDateMesure() {
        return dateMesure;
    }
}
