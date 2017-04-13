package com.example.kevinmouga.gsb_n2f;

/**
 * Created by kevinmouga on 05/02/2017.
 */

public class FraisHorsForfait extends Frais {
    private String idFHF;
    private String libelle;
    private String dateFact;
    private double montant;

    public FraisHorsForfait(String mois, Utilisateur user, String libelle, String dateFact, double montant) {
        super(mois, user);
//        garnit cette class grace au paramétre reçu
        this.libelle = libelle;
        this.dateFact = dateFact;
        this.montant = montant;
    }

    public FraisHorsForfait() {
    }


    public String getIdFHF() {
        return idFHF;
    }

    public void setIdFHF(String idFHF) {
        this.idFHF = idFHF;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDateFact() {
        return dateFact;
    }

    public void setDateFact(String dateFact) {
        this.dateFact = dateFact;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
