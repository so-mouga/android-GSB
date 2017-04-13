package com.example.kevinmouga.gsb_n2f;

/**
 * Created by kevinmouga on 05/02/2017.
 */

public class Frais {
    private String mois;
    private Utilisateur user;

    public Frais() {
    }

    public Frais(String mois, Utilisateur user) {
        this.mois = mois;
        this.user = user;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
}
