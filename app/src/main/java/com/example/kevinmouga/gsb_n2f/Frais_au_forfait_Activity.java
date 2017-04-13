package com.example.kevinmouga.gsb_n2f;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.drawable.radiobutton_off_background;
import static android.R.drawable.radiobutton_on_background;

public class Frais_au_forfait_Activity extends AppCompatActivity {

    private Utilisateur oUtilisateur;
    private Frais oFrais;
    private FraisAuForfait oFraisforfait;
    private Dao dao;

    private Spinner spinnerType;
    private Button envoyer;
    private EditText qt;
    private int numberID;

    private static TextView value1;
    private static TextView value2;
    private static TextView value3;
    private static EditText edit1;
    private static Button modifierFAF;
    private static Button supprimerFAF;
    private static ImageView radio11;
    private static ImageView radio12;
    private static Boolean test = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frais_au_forfait);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dao = new Dao(this);

        final TableLayout tableLayoutFAF = (TableLayout) findViewById(R.id.layoutFAF);
        qt = (EditText) findViewById(R.id.quantite);
        envoyer = (Button) findViewById(R.id.ajouter);

        envoyer.setOnClickListener(actionAjouter2);

        spinnerType = (Spinner) findViewById(R.id.listeType);
        //Recuperation NUI REP ETC de la table fraisforfait de la BDD .... dans un spinner
        List<String> label = dao.getAllFraisForfait();
        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
           un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
           Avec la liste des elements */
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, label);
        // On definit une présentation du spinner quand il est déroulé
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerType.setAdapter(dataAdapter);


        //Creation d'un tableau qui contiendra toutes les listes FHF
        numberID = 0;
        Cursor cursor = dao.getAllLigneFraisAuForfait2();
        if (cursor.moveToFirst()) {
            do {
                //creation TableRow
                final TableRow tableRow = new TableRow(this);
                tableRow.setId(numberID);
                final TableRow.LayoutParams texte1Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte1Param.leftMargin = 120;
                final TextView texte1 = new TextView(this);
                texte1.setText(cursor.getString(0));
                texte1.setPadding(10, 0, 10, 0);
                texte1.setTextSize(15);
                texte1.setLayoutParams(texte1Param);
                tableRow.addView(texte1);

                final TableRow.LayoutParams texte2Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte2Param.leftMargin = 30;
                final TextView texte2 = new TextView(this);
                texte2.setText(cursor.getString(1));
                texte2.setPadding(10, 0, 10, 0);
                texte2.setTextSize(15);
                texte2.setLayoutParams(texte2Param);
                tableRow.addView(texte2);

                final TableRow.LayoutParams texte3Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte3Param.leftMargin = 100;
                texte3Param.width = 100;
                final TextView texte3 = new TextView(this);
                texte3.setText(cursor.getString(2));
                texte3.setPadding(10, 0, 10, 0);
                texte3.setTextSize(15);
                texte3.setLayoutParams(texte3Param);
                tableRow.addView(texte3);

                //création d'un edite texte qui s'activera quand l'user clique sur le champs TextView(MONTANT)
                final TableRow.LayoutParams editParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                editParam.leftMargin = 100;
                editParam.width = 100;
                final EditText edit = new EditText(Frais_au_forfait_Activity.this);
                edit.setText(cursor.getString(2));
                edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                edit.setPadding(10, 0, 10, 0);
                edit.setTextSize(15);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                edit.setLayoutParams(editParam);
                edit.setBackgroundResource(R.drawable.champ_white);
                edit.setVisibility(View.GONE);
                tableRow.addView(edit);

//Debut test
//                RadioGroup radioGroup = new RadioGroup(this);
//
//                int i = 0;
//                String radioButton = "radioButton" + i;
//
//                final TableRow.LayoutParams texte4Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                texte4Param.leftMargin = 90;
//                final RadioButton radioButton1 = new RadioButton(this);
//                radioButton1.setTransitionName(radioButton);
//                radioButton1.setId(numberID);
////                radioGroup.addView(radioButton1, i);
//                tableRow.addView(radioGroup);
//                i++;
//fin test

                final TableRow.LayoutParams texte4Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte4Param.leftMargin = 90;
                final ImageView radio1 = new ImageView(this);
                radio1.setImageResource(radiobutton_off_background);
                radio1.setLayoutParams(texte4Param);
                tableRow.addView(radio1);

                final TableRow.LayoutParams texte5Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte5Param.leftMargin = 170;
                final ImageView radio2 = new ImageView(this);
                radio2.setImageResource(radiobutton_on_background);
                radio2.setLayoutParams(texte5Param);
                radio2.setVisibility(View.GONE);
                tableRow.addView(radio2);

                //tableRow qui contien les bouton modifier ,supprimer ils s'afficheront quand l'user clique sur le champs TextView(MONTANT)
                final LinearLayout tableRowButton = new LinearLayout(this);
                tableRowButton.setId(numberID);
                tableRowButton.setPadding(10, 10, 10, 10);
                tableRowButton.setGravity(Gravity.CENTER);

                final TableRow.LayoutParams boutonSuppParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                boutonSuppParam.height = 95;
                boutonSuppParam.width = 300;
                boutonSuppParam.setMargins(10, 10, 10, 10);
                final Button supp = new Button(Frais_au_forfait_Activity.this);
                supp.setBackground(getDrawable(R.drawable.button_red));
                supp.setText("supprimer");
                supp.setTextSize(10);
                supp.getParent();
                supp.setLayoutParams(boutonSuppParam);
                supp.setTextColor(Color.WHITE);
                supp.setVisibility(View.GONE);
                tableRowButton.addView(supp);


                final TableRow.LayoutParams boutonModifierParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                boutonModifierParam.height = 95;
                boutonModifierParam.width = 300;
                boutonModifierParam.setMargins(10, 10, 10, 10);
                final Button modifier = new Button(Frais_au_forfait_Activity.this);
                modifier.setBackground(getDrawable(R.drawable.button_orange));
                modifier.setText("modifier");
                modifier.setTextSize(10);
                modifier.getParent();
                modifier.setLayoutParams(boutonModifierParam);
                modifier.setTextColor(Color.WHITE);
                modifier.setVisibility(View.GONE);
                tableRowButton.addView(modifier);
                if (tableRow.getId() % 2 != 0) {
                    tableRow.setBackgroundColor(Color.parseColor("#f2f9ff"));
                    tableRowButton.setBackgroundColor(Color.parseColor("#f2f9ff"));
                }
                ;

                tableLayoutFAF.addView(tableRow);
                tableLayoutFAF.addView(tableRowButton);

                numberID++;

                //action qui se declenche au moment du clique sur la ligne de frais
                // tablerow
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // test
//                        if (radioButton1.isChecked()) {
//                            modifier.setVisibility(View.VISIBLE);
//                        }

                        //affiche les bouttons et le edittext
                        supp.setVisibility(View.VISIBLE);
                        modifier.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.VISIBLE);
                        texte3.setVisibility(View.GONE);
                        radio1.setVisibility(View.GONE);
                        radio2.setVisibility(View.VISIBLE);

//                        recupére les values pour les stockers

//                        grace à cela on peut cacher les boutons quand l'user click sur un autre endroit
                        if (value1 != null) {
                            value1.setVisibility(View.VISIBLE);
                            value2.setVisibility(View.VISIBLE);
                            value3.setVisibility(View.VISIBLE);
                            edit1.setVisibility(View.GONE);
                            modifierFAF.setVisibility(View.GONE);
                            supprimerFAF.setVisibility(View.GONE);
                            radio11.setVisibility(View.VISIBLE);
                            radio12.setVisibility(View.GONE);
                        }

//                        recupére les values pour les stockers
                        value1 = texte1;
                        value2 = texte2;
                        value3 = texte3;
                        edit1 = edit;
                        modifierFAF = modifier;
                        supprimerFAF = supp;
                        radio11 = radio1;
                        radio12 = radio2;


                        supp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                oFraisforfait = new FraisAuForfait();
                                String valueid = value1.getText().toString();

                                oFraisforfait.setIdFAF(valueid);
                                long resultat = dao.deleteFAF(oFraisforfait);
                                String resuLong = Technique.reultatLong(String.valueOf(resultat));
                                Toast.makeText(Frais_au_forfait_Activity.this, "Votre prestation " + value2.getText().toString() + " a bien été supprimé.", Toast.LENGTH_SHORT).show();

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                            }
                        });


                        modifier.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                oFraisforfait = new FraisAuForfait();
                                String valueid = value1.getText().toString();
                                String quantite = edit1.getText().toString();

                                oFraisforfait.setIdFAF(valueid);
                                oFraisforfait.setQt(Integer.parseInt(quantite));
                                long resultat = dao.updateFAF(oFraisforfait);
                                String resuLong = Technique.reultatLong(String.valueOf(resultat));
                                Toast.makeText(Frais_au_forfait_Activity.this, "Prestation " + value2.getText().toString() + " modifié.", Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                            }
                        });
                    }
                });
            } while (cursor.moveToNext());
        }


        ScrollView scroll = (ScrollView) findViewById(R.id.scrollFf);

        scroll.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                Intent Menu = new Intent(Frais_au_forfait_Activity.this, Menu_Activity.class);
                startActivity(Menu);
            }

            @Override
            public void onSwipeLeft() {
                Intent fraisHorsForfait = new Intent(Frais_au_forfait_Activity.this, Frais_hors_forfait_Activity.class);
                startActivity(fraisHorsForfait);
            }
        });

    }


//---------------------- METHODE ---------------------------------


//    ------- AJOUT FAF -------------

    private View.OnClickListener actionAjouter2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //on exécute la fonction que si le user a rentrez une donnée
            if (qt.getText().toString().equals("") || Integer.parseInt(qt.getText().toString()) <= 0) {
                Toast.makeText(Frais_au_forfait_Activity.this, "veuillez rentrer une valeur correct", Toast.LENGTH_SHORT).show();
            } else {
                Cursor resu = dao.selectUser();
                if (resu.moveToFirst()) {
                    //Recuperation des objets pour effectué la fonction createFraisAuForfait
                    oUtilisateur = new Utilisateur(
                            resu.getString(0),
                            resu.getString(1),
                            resu.getString(2),
                            resu.getString(3),
                            resu.getString(4));
                    oFrais = new Frais(Technique.datenow(), oUtilisateur);
                    oFraisforfait = new FraisAuForfait(
                            oFrais.getMois()
                            , oUtilisateur,
                            spinnerType.getSelectedItem().toString(),
                            Integer.parseInt(qt.getText().toString()));
                }

                //Verification pour empecher que  le même frais s'ajoute 2 fois  dans le mois en cours
                List<String> label = dao.getAllLigneFraisAuForfaitComprason();
                //on recupére toutes les prestations de la BDD pour les insérer dans une variable
                String sentenceFAF = "";
                for (int i = 0; i < label.size(); i++) {
                    sentenceFAF = String.valueOf(label.get(i)) + " " + sentenceFAF;
                }
                //On compare la variable sentenceFAF avec la valeur selectionné par l'user
                int resuCompare = sentenceFAF.indexOf(spinnerType.getSelectedItem().toString());
                //(.indexOf) compare les 2 variable pour chercher le mot si il ne trouve pas le mot dans la phrase resuCompare= -1
                if (resuCompare == -1) {
                    long resultat = dao.createFraisAuForfait(oFraisforfait, oFrais);
                    Toast.makeText(Frais_au_forfait_Activity.this, "Prestation ajouté", Toast.LENGTH_SHORT).show();
                    //on recharge la page une fois l'action fini
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(Frais_au_forfait_Activity.this, "Cette prestation à déjà été ajouté", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
}