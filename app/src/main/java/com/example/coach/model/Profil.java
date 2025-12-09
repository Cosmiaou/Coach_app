package com.example.coach.model;

public class Profil {
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;

    private static final String[] MESSAGE = {"trop faible :(", "normal :))", "trop élévé :("};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    private final Integer poids;
    private final Integer taille;
    private final Integer age;
    private final Integer sexe;

    private double img;

    private int indice;

    public Profil(Integer poids, Integer taille, Integer age, Integer sexe) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        img = calculImg();
        calculIndice();
    }

    private double calculImg() {
        double tailleM = taille / 100.0;
        return img = (1.2 * poids/(tailleM * tailleM)) + (0.23 * age) - (10.83 * sexe) - 5.4;
    }

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

    public double getImg() {
        return img;
    }

    public String getMessage(){
        return MESSAGE[indice];
    }

    public String getImage(){
        return IMAGE[indice];
    }

    public boolean normal() {
        return indice == 1;
    }
}
