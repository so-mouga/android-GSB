package com.example.kevinmouga.gsb_n2f;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kevinmouga on 05/02/2017.
 */

public class Dao extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    //constructeur porte toujours le même nom que la classe

    public Dao(Context context) {
        super(context, ParamsDao.NOM_BDD, null, ParamsDao.VERSION_BDD);
    }

    //    creation de la BDD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ParamsDao.SQL_UTILISATEUR);
        db.execSQL(ParamsDao.SQL_FRAIS);
        db.execSQL(ParamsDao.SQL_FRAIS_FORFAIT);
        db.execSQL(ParamsDao.SQL_FRAIS_AU_FORFAIT);
        db.execSQL(ParamsDao.SQL_FRAIS_HORS_FORFAIT);

        db.execSQL(ParamsDao.VALUE_FRAIS_FORFAIT);
    }

    //    maj bdd
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + ParamsDao.TABLE_FRAIS + ";");
        db.execSQL( "DROP TABLE IF EXISTS " + ParamsDao.TABLE_FRAIS_FORFAIT + ";");
        db.execSQL( "DROP TABLE IF EXISTS " + ParamsDao.TABLE_FRAIS_HORS_FORFAIT + ";");
        db.execSQL( "DROP TABLE IF EXISTS " + ParamsDao.TABLE_UTILISATEUR + ";");
        db.execSQL( "DROP TABLE IF EXISTS " + ParamsDao.TABLE_FRAIS_AU_FORFAIT + ";");

        onCreate(db);
    }




//    -------------------------------------      CREATE        ----------------------------------------------


    //    -------------CREATE USER ----------------
    //    lond type entier mais est plus large que le INT
    public  long createUtilisateur(Utilisateur user){
        SQLiteDatabase db = this.getWritableDatabase();

//        ContentValues :tableau associatif = (clée=colonne ,valeur=donnée de la table)
        ContentValues tabUtilisateur = new ContentValues();

        tabUtilisateur.put(ParamsDao.COL_ID,user.getId());
        tabUtilisateur.put(ParamsDao.COL_NOM,user.getNom());
        tabUtilisateur.put(ParamsDao.COL_PRENOM,user.getPrenom());
        tabUtilisateur.put(ParamsDao.COL_PASSWORD,user.getPassword());
        tabUtilisateur.put(ParamsDao.COL_EMAIL,user.getEmail());

//        retourne renverra le numero de la ligne ajouté si elle c'est bien exécuté sinon -1
        return db.insert(ParamsDao.TABLE_UTILISATEUR,null,tabUtilisateur);
    }

    //----------------CREATE FAF------------------
    public  long createFraisAuForfait(FraisAuForfait ff,Frais frais){
        SQLiteDatabase db = this.getWritableDatabase();

//        ContentValues :tableau associatif = (clée=colonne ,valeur=donnée de la table)
//        contiendra les values pour les envoyer à la BDD
        ContentValues tabFrais = new ContentValues();

//        on crée dabort une ligne FRAIS pour recuperer son ID puis le transmettre à ligne FAF
        tabFrais.put(ParamsDao.COL_IDUSER,frais.getUser().getId());
        tabFrais.put(ParamsDao.COL_MOIS,ff.getMois());
        long value2 = db.insert(ParamsDao.TABLE_FRAIS,null,tabFrais);


        //on fois la ligne FRAIS crée on recupere ID max pour la passé a la table ligne FAF
        String selectQuery = "SELECT max(id) FROM "+ ParamsDao.TABLE_FRAIS;
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor cursor = dbb.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        ContentValues tabFraisForfait = new ContentValues();
        int maxID = Integer.parseInt(cursor.getString(0));
        tabFraisForfait.put(ParamsDao.COL_IDFAF,maxID);
        tabFraisForfait.put(ParamsDao.COL_IDFRAISFORFAIT,ff.getPresta());
        tabFraisForfait.put(ParamsDao.COL_QUANTITE,ff.getQt());

        return db.insert(ParamsDao.TABLE_FRAIS_AU_FORFAIT,null,tabFraisForfait);

    }



    //---------------CREATE FHF--------------------
    public  long createFraisHorsForfait(FraisHorsForfait fhf,Frais frais){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues tabFrais = new ContentValues();

//        on crée dabort une ligne FRAIS pour recuperer son ID puis le transmettre à ligne FAF
        tabFrais.put(ParamsDao.COL_IDUSER,frais.getUser().getId());
        tabFrais.put(ParamsDao.COL_MOIS,fhf.getMois());
        long value2 = db.insert(ParamsDao.TABLE_FRAIS,null,tabFrais);

        //on recupere ID max pour la passé a la table ligne FAF
        String selectQuery = "SELECT max(id) FROM "+ ParamsDao.TABLE_FRAIS;
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor cursor = dbb.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        ContentValues tabFraisHorsForfait = new ContentValues();
        int maxID = Integer.parseInt(cursor.getString(0));
        tabFraisHorsForfait.put(ParamsDao.COL_IDFHF,maxID);
        tabFraisHorsForfait.put(ParamsDao.COL_LIBELLEFRAIS,fhf.getLibelle());
        tabFraisHorsForfait.put(ParamsDao.COL_DATE,fhf.getDateFact());
        tabFraisHorsForfait.put(ParamsDao.COL_MONTANTFRAIS,fhf.getMontant());

        return db.insert(ParamsDao.TABLE_FRAIS_HORS_FORFAIT,null,tabFraisHorsForfait);


    }





//    ---------------------------------------UDPDATE-------------------------------------------------------

    //    ----------UPDATE USER---------------
    public long updateUser(Utilisateur user) {
        db = this.getWritableDatabase();

        long resuInsertion;

//        ContentValues : Tableau associatif (clé, valeur)
//        (clé = colone, valeur = donnée : de la table)
        ContentValues tabUtilisateur = new ContentValues();

        tabUtilisateur.put(ParamsDao.COL_ID, user.getId());
        tabUtilisateur.put(ParamsDao.COL_NOM, user.getNom());
        tabUtilisateur.put(ParamsDao.COL_PRENOM, user.getPrenom());
        tabUtilisateur.put(ParamsDao.COL_PASSWORD, user.getPassword());
        tabUtilisateur.put(ParamsDao.COL_EMAIL, user.getEmail());

        resuInsertion = db.update(ParamsDao.TABLE_UTILISATEUR, tabUtilisateur, null, null);

        return resuInsertion;
    }

    //---------------UPDATE FAF ------------------
    public long updateFAF(FraisAuForfait faf) {
        db = this.getWritableDatabase();
        long resuInsertion;

        ContentValues tabFraisHorsForfait = new ContentValues();
        tabFraisHorsForfait.put(ParamsDao.COL_QUANTITE,faf.getQt());

        resuInsertion = db.update(ParamsDao.TABLE_FRAIS_AU_FORFAIT, tabFraisHorsForfait, ParamsDao.COL_IDFAF+" = "+ faf.getIdFAF(), null);

        return resuInsertion;
    }

    //-----------------UPDATE FHF--------------------
    public long updateFHF(FraisHorsForfait fhf) {
        db = this.getWritableDatabase();
        long resuInsertion;

        ContentValues tabFraisHorsForfait = new ContentValues();
        tabFraisHorsForfait.put(ParamsDao.COL_MONTANTFRAIS,fhf.getMontant());

        resuInsertion = db.update(ParamsDao.TABLE_FRAIS_HORS_FORFAIT, tabFraisHorsForfait, ParamsDao.COL_IDFHF+" = "+ fhf.getIdFHF(), null);

        return resuInsertion;
    }




//    ------------------------------------------------DELET---------------------------------------------------------

    //    -------------DELETE FHF ------------
    public long deleteFHF(FraisHorsForfait fhf) {
        db = this.getWritableDatabase();
        long resuInsertion;

        ContentValues tabFraisHorsForfait = new ContentValues();
        resuInsertion = db.delete(ParamsDao.TABLE_FRAIS_HORS_FORFAIT, ParamsDao.COL_IDFHF+" = "+ fhf.getIdFHF(), null);
        resuInsertion = db.delete(ParamsDao.TABLE_FRAIS, ParamsDao.COL_ID+" = "+ fhf.getIdFHF(), null);


        return resuInsertion;
    }

    //    -------------DELETE FAF-------------
    public long deleteFAF(FraisAuForfait faf) {
        db = this.getWritableDatabase();
        long resuInsertion;

        ContentValues tabFraisHorsForfait = new ContentValues();
        resuInsertion = db.delete(ParamsDao.TABLE_FRAIS_AU_FORFAIT, ParamsDao.COL_IDFAF+" = "+ faf.getIdFAF(), null);
        resuInsertion = db.delete(ParamsDao.TABLE_FRAIS, ParamsDao.COL_ID+" = "+ faf.getIdFAF(), null);

        return resuInsertion;
    }




//    -----------------------------------------------------SELECT ------------------------------------------------------

    //    --------SELECT USER--------
//    Cursor est une classe natif android :représente le resultat d'une requéte ,Ce resultat peut contenir 0 ou plusieur lignes.
//    Cursor est comme un tableau il récupére des données de la requéte
    public Cursor selectUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelectUtilisateur = " SELECT * FROM " + ParamsDao.TABLE_UTILISATEUR;

        //        resultatRequete est un objet de type cursor
//        tableau representent le resultat de la requete sqlSelectUtilisateur

        Cursor resultatRequete = db.rawQuery(sqlSelectUtilisateur,null);

        return  resultatRequete;
    }

    public Cursor selectFraisForfait(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlSelectUtilisateur = " SELECT * FROM " + ParamsDao.TABLE_FRAIS_FORFAIT;

        //        resultatRequete est un objet de type cursor
//        tableau representent le resultat de la requete sqlSelectUtilisateur

        Cursor resultatRequete = db.rawQuery(sqlSelectUtilisateur,null);

        return  resultatRequete;

    }

    //---------SELECT TYPE FRAISFORFAIT------------
    public List<String> getAllFraisForfait(){

        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "  + ParamsDao.TABLE_FRAIS_FORFAIT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


//---------SELECT ALL FAF------------

    public Cursor getAllLigneFraisAuForfait2(){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "SELECT " +  ParamsDao.COL_IDFAF +" , "+
                ParamsDao.COL_IDFRAISFORFAIT +" , "+
                ParamsDao.COL_QUANTITE +
                " FROM " + ParamsDao.TABLE_FRAIS_AU_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ ParamsDao.COL_IDFAF + " = " + ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + Technique.datenow() ;

        //        resultatRequete est un objet de type cursor
//        tableau representent le resultat de la requete sqlSelectUtilisateur

        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }

    //    -----------SELECT ALL FAF POUR COMPARARER SI DEJA EXISTANTE----------
    public List<String> getAllLigneFraisAuForfaitComprason(){

        SimpleDateFormat formatter  = new SimpleDateFormat ("yyyyMM");
        Date currentTime_1 = new Date();
        String dateNow = formatter.format(currentTime_1);

        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery =  "SELECT " +  ParamsDao.COL_IDFRAISFORFAIT +
                " FROM " + ParamsDao.TABLE_FRAIS_AU_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ ParamsDao.COL_IDFAF + " = " + ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + dateNow ;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


    //---------SELECT FHF----------------
    public Cursor getAllLigneFraisHorsForfait(){
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery =  "SELECT "+ ParamsDao.COL_DATE +" , "+
                ParamsDao.COL_LIBELLEFRAIS +" , "+
                ParamsDao.COL_MONTANTFRAIS +" , "+
                ParamsDao.COL_IDFHF +
                " FROM "+ ParamsDao.TABLE_FRAIS_HORS_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ ParamsDao.COL_IDFHF + " = " + ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + Technique.datenow() ;

        //        resultatRequete est un objet de type cursor
//        tableau representent le resultat de la requete sqlSelectUtilisateur

        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }

    //---------SELECT FAF SYNTHESE ------------

    public Cursor getSynthesefaf(Frais ff){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "SELECT " +  ParamsDao.COL_IDFAF +" , "+
                ParamsDao.COL_IDFRAISFORFAIT +" , "+
                ParamsDao.COL_QUANTITE +
                " FROM " + ParamsDao.TABLE_FRAIS_AU_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ ParamsDao.COL_IDFAF + " = " + ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + ff.getMois() ;

        //        resultatRequete est un objet de type cursor
//        tableau representent le resultat de la requete sqlSelectUtilisateur

        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }


    //---------SELECT FHF  SYNTHESE----------------
    public Cursor getSynthesefhf(Frais ff){
        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery =  "SELECT "+ ParamsDao.COL_LIBELLEFRAIS +" , "+
                ParamsDao.COL_MONTANTFRAIS +" , "+
                ParamsDao.COL_DATE +" , "+
                ParamsDao.COL_IDFHF +
                " FROM "+ ParamsDao.TABLE_FRAIS_HORS_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ ParamsDao.COL_IDFHF + " = " + ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + ff.getMois() ;

        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }
    //    ----------------- SELECT SYNTHESE TOTAL FAF -----------------

    public Cursor getSynthesefhfTotal(Frais ff){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "select sum(montantFrais) as totalFHF " +
                " FROM " + ParamsDao.TABLE_FRAIS_HORS_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ParamsDao.COL_IDFHF + " = "+ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + ff.getMois() ;


        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }



//    ----------------- SELECT SYNTHESE TOTAL FAF -----------------

    public Cursor getSynthesefafTotal(Frais ff){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "select sum(montantFrais*quantite) as totalFAF " +
                " FROM " + ParamsDao.TABLE_FRAIS_FORFAIT + " as f "+
                " INNER JOIN " + ParamsDao.TABLE_FRAIS_AU_FORFAIT + " as faf" +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON f."+ParamsDao.COL_IDFRAISFORFAIT + " = faf."+ParamsDao.COL_IDFRAISFORFAIT +
                " AND "+ParamsDao.COL_IDFAF + " = "+ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + ff.getMois() ;


        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }

    //    ----------------- SELECT SYNTHESE TOTAL FAF ET FHF -----------------

    public Cursor getSynthesefafANDfhfTotal(Frais ff){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "select sum(total) " +
                " from ( select sum(montantFrais*quantite) as total " +
                " FROM " + ParamsDao.TABLE_FRAIS_FORFAIT + " as f "+
                " INNER JOIN " + ParamsDao.TABLE_FRAIS_AU_FORFAIT + " as faf" +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON f."+ParamsDao.COL_IDFRAISFORFAIT + " = faf."+ParamsDao.COL_IDFRAISFORFAIT +
                " AND "+ParamsDao.COL_IDFAF + " = "+ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + ff.getMois() +
                " UNION ALL" +
                " select sum(montantFrais) as total " +
                " FROM " + ParamsDao.TABLE_FRAIS_HORS_FORFAIT +
                " INNER JOIN " + ParamsDao.TABLE_FRAIS +
                " ON "+ParamsDao.COL_IDFHF + " = "+ParamsDao.COL_ID +
                " WHERE "+ParamsDao.COL_MOIS +" = " + ff.getMois() +" )" ;


        Cursor resultatRequete = db.rawQuery(selectQuery,null);

        return  resultatRequete;

    }

    //---------SELECT Mois ------------
    public List<String> getAllmois(){

        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT "+ ParamsDao.COL_MOIS+ " FROM "  + ParamsDao.TABLE_FRAIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }




}
