package com.example.coach.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfilTest {

    private final Profil profilMaigre = new Profil (45, 170, 20, 0);
    private final Profil profilNormal = new Profil (70, 180, 40, 1);
    private final Profil profilGras = new Profil (67, 165, 35, 0);

    @Test
    public void getImg() {
        assertEquals(17.9, profilMaigre.getImg(), 0.2);
        assertEquals(32.2, profilGras.getImg(), 0.2);
        assertEquals(18.9, profilNormal.getImg(), 0.2);
    }

    @Test
    public void getMessage() {
        assertEquals("trop faible :(", profilMaigre.getMessage());
        assertEquals("trop élévé :(", profilGras.getMessage());
        assertEquals("normal :))", profilNormal.getMessage());
    }

    @Test
    public void getImage() {
        assertEquals("maigre", profilMaigre.getImage());
        assertEquals("graisse", profilGras.getImage());
        assertEquals("normal", profilNormal.getImage());
    }

    @Test
    public void normal() {
        assertFalse(profilMaigre.normal());
        assertFalse(profilGras.normal());
        assertTrue(profilNormal.normal());
    }
}