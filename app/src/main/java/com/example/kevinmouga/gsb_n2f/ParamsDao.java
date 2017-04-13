package com.example.kevinmouga.gsb_n2f;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kevinmouga on 05/02/2017.
 */

public class ParamsDao {

    //    Les attributs
//    SQLiteDatabase sert a executer les requetes
    private SQLiteDatabase db;

    public static final int VERSION_BDD = 5;
    public static final String NOM_BDD = "gsb_n2f";

    //    creation de table BDD
    public static final String TABLE_UTILISATEUR = "utilisateur";
    public static final String TABLE_FRAIS_AU_FORFAIT = "lignefraisforfait";
    public static final String TABLE_FRAIS_HORS_FORFAIT = "lignefraishorsforfait";
    public static final String TABLE_FRAIS_FORFAIT = "fraisforfait";
    public static final String TABLE_FRAIS = "frais";



    //    colonne de la table utilisateur
    public static final String COL_ID="id";
    public static final String COL_NOM="nom";
    public static final String COL_PRENOM="prenom";
    public static final String COL_PASSWORD="password";
    public static final String COL_EMAIL="email";

    //    colonne de la table ligne frais au forfait
    public static final String COL_MOIS="mois";
    public static final String COL_PRESTA="presta";
    public static final String COL_QUANTITE="quantite";
    public static final String COL_IDFAF="idFAF";



    //    colonne de la table ligne frais Hors forfait
    public static final String COL_LIBELLEFRAIS="libelleFrais";
    public static final String COL_MONTANTFRAIS="montantFrais";
    public static final String COL_DATE="dateFact";
    public static final String COL_IDUSER="idUser";
    public static final String COL_IDFHF="idFHF";
    //    colonne de la table ligne fraisforfait
    public static final String COL_IDFRAISFORFAIT="idFraisForfait";




    //    Creation de la table utilisateur
    public static  final String SQL_UTILISATEUR = "CREATE TABLE " + TABLE_UTILISATEUR + "("
            + COL_ID + " TEXT PRIMARY KEY , "+
            COL_NOM + " TEXT , "+
            COL_PRENOM + " TEXT , "+
            COL_PASSWORD + " TEXT , "+
            COL_EMAIL + " TEXT ) ";


    //    Creation de la table ligne frais
    public static  final String SQL_FRAIS = "CREATE TABLE " + TABLE_FRAIS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            COL_MOIS + " TEXT , " +
            COL_IDUSER + " TEXT , "+
            " FOREIGN KEY ("+COL_IDUSER+") REFERENCES "+TABLE_UTILISATEUR+"("+COL_ID+"))";


    //    Creation de la table ligne fraisforfait
    public static  final String SQL_FRAIS_FORFAIT ="CREATE TABLE " + TABLE_FRAIS_FORFAIT + "("
            +COL_IDFRAISFORFAIT + " TEXT PRIMARY KEY , "+
            COL_LIBELLEFRAIS + " TEXT , " +
            COL_MONTANTFRAIS + " REEL )";

    //    Creation de la table ligne frais au forfait
    public static  final String SQL_FRAIS_AU_FORFAIT = "CREATE TABLE " + TABLE_FRAIS_AU_FORFAIT + "("
            + COL_IDFAF + " INTEGER PRIMARY KEY, "+
            COL_IDFRAISFORFAIT + " TEXT , " +
            COL_QUANTITE + " INTEGER , " +
            " FOREIGN KEY ("+COL_IDFRAISFORFAIT+") REFERENCES "+TABLE_FRAIS_FORFAIT+"("+COL_IDFRAISFORFAIT+"),"+
            " FOREIGN KEY ("+COL_IDFAF+") REFERENCES "+TABLE_FRAIS+"("+COL_ID+"))";

    //    Creation de la table ligne frais hors forfait
    public static  final String SQL_FRAIS_HORS_FORFAIT = "CREATE TABLE " + TABLE_FRAIS_HORS_FORFAIT + "("
            + COL_IDFHF + " INTEGER PRIMARY KEY, "+
            COL_LIBELLEFRAIS + " TEXT , " +
            COL_DATE + " TEXT , " +
            COL_MONTANTFRAIS + " REEL ," +
            " FOREIGN KEY ("+COL_IDFHF+") REFERENCES "+TABLE_FRAIS+"("+COL_ID+"))";


    public static final String VALUE_FRAIS_FORFAIT = "INSERT INTO " + TABLE_FRAIS_FORFAIT + " Values " +
            "        ('ETP', 'Forfait Etape', 110.00),\n" +
            "        ('KM', 'Frais Kilométrique', NULL),\n" +
            "        ('NUI', 'Nuitée Hôtel', 80.00),\n" +
            "        ('REP', 'Repas Restaurant', 25.00);\n" +
            "        ;";




}
