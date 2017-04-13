package com.example.kevinmouga.gsb_n2f;

/**
 * Created by kevinmouga on 05/02/2017.
 */

// FraisAuForfait hérite de la classe Frais c'est une classe fille
public class FraisAuForfait extends Frais {

    private String presta;
    private int qt;
    private String idFAF;

    //    les parametres FraisAuForfait contiennent les parametres de la classe MERE + FILLE
    public FraisAuForfait(String mois, Utilisateur user, String presta, int qt) {
//        super va récuperer les infos de la classe mere
        super(mois, user);
        this.presta = presta;
        this.qt = qt;
    }



    public FraisAuForfait() {
    }

    public String getIdFAF() {
        return idFAF;
    }

    public void setIdFAF(String idFAF) {
        this.idFAF = idFAF;
    }

    public String getPresta() {
        return presta;
    }

    public void setPresta(String presta) {
        this.presta = presta;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }
}
