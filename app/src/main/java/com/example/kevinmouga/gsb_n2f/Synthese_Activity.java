package com.example.kevinmouga.gsb_n2f;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Synthese_Activity extends AppCompatActivity {

    private int numberID;
    private TableLayout tablleSynthesehiggen;
    private TableLayout tablleSyntheseFAF;

    private TableLayout tablleSyntheseFHF;
    private TableLayout tablleSynthesehiggenFHF;

    private TextView fraisAuForfait;
    private Spinner spinnerMois;
    private Button validerMois;

    private Frais frais;

    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthese);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dao = new Dao(this);

        tablleSyntheseFAF = (TableLayout) findViewById(R.id.layoutFAF);
        tablleSynthesehiggen = (TableLayout) findViewById(R.id.tablleSyntheseFAF);

        tablleSyntheseFHF = (TableLayout) findViewById(R.id.layoutFHF);
        tablleSynthesehiggenFHF = (TableLayout) findViewById(R.id.tablleSyntheseFHF);

        fraisAuForfait = (TextView) findViewById(R.id.faf);
        spinnerMois = (Spinner) findViewById(R.id.spinnerMois);


        //récuperation des mois ou il y a un forfait
        List<String> label = dao.getAllmois();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, label);
        // On definit une présentation du spinner quand il est déroulé
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerMois.setAdapter(dataAdapter);


        //fonction qui s'active au changement du spinner
        spinnerMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    tablleSyntheseFAF.removeAllViews();
                    tablleSyntheseFHF.removeAllViews();


                    frais = new Frais();
                    frais.setMois(spinnerMois.getSelectedItem().toString());

                    tablleSynthesehiggen.setVisibility(View.VISIBLE);
                    tablleSynthesehiggenFHF.setVisibility(View.VISIBLE);


                    Cursor cursorFAF = dao.getSynthesefaf(frais);
                    Cursor cursorFHF = dao.getSynthesefhf(frais);



                    final TableRow tableRowTitre = new TableRow(Synthese_Activity.this);
                    tableRowTitre.setId(numberID);
                    tableRowTitre.setGravity(Gravity.CENTER);

                    final TableRow.LayoutParams titrePara2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    final TableRow.LayoutParams titrePara = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    final TextView titre1 = new TextView(Synthese_Activity.this);
                    titrePara.leftMargin = 50;
                    titrePara2.bottomMargin = 1;

                    titre1.setText("Prestation");
                    titre1.setTextSize(15);
                    titre1.setLayoutParams(titrePara2);
                    titre1.setTypeface(Typeface.DEFAULT_BOLD);

                    tableRowTitre.addView(titre1);

                    final TextView titre2 = new TextView(Synthese_Activity.this);
                    titre2.setText("Quantite");
                    titre2.setTextSize(15);
                    titre2.setLayoutParams(titrePara2);
                    titre2.setLayoutParams(titrePara);
                    titre2.setTypeface(Typeface.DEFAULT_BOLD);
                    tableRowTitre.addView(titre2);
                    tablleSyntheseFAF.addView(tableRowTitre);


                    numberID = 0;
                    if (cursorFAF.moveToFirst() == true) {
                        do {
                            //creation TableRow
                            final TableRow.LayoutParams rowFAF = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            rowFAF.setMargins(10,10,10,10);
                            final TableRow tableRow = new TableRow(Synthese_Activity.this);
                            tableRow.setLayoutParams(rowFAF);
                            tableRow.setId(numberID);
                            tableRow.setGravity(Gravity.CENTER);

                            final TableRow.LayoutParams texte1Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            final TextView texte1 = new TextView(Synthese_Activity.this);
                            texte1Param.leftMargin = 20;
                            texte1.setText(cursorFAF.getString(1));
                            texte1.setPadding(10, 0, 10, 0);
                            texte1.setTextSize(15);
                            texte1.setLayoutParams(texte1Param);
                            tableRow.addView(texte1);

                            final TableRow.LayoutParams texte2Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            final TextView texte2 = new TextView(Synthese_Activity.this);
                            texte2Param.leftMargin = 100;
                            texte2.setText(cursorFAF.getString(2));
                            texte2.setPadding(10, 0, 10, 0);
                            texte2.setTextSize(15);
                            texte2.setLayoutParams(texte2Param);
                            tableRow.addView(texte2);


                            tablleSyntheseFAF.addView(tableRow);

                            numberID++;

                        } while (cursorFAF.moveToNext());
                    }


                    Cursor totalFAF = dao.getSynthesefafTotal(frais);
                    if(totalFAF.moveToFirst()){
                        final TextView textetotal = new TextView(Synthese_Activity.this);
                        textetotal.setTypeface(Typeface.DEFAULT_BOLD);
                        textetotal.setText("Total frais au forfait  : " + totalFAF.getString(0) + " euros");
                        textetotal.setPadding(10, 0, 10, 0);
                        textetotal.setTextSize(15);
                        tablleSyntheseFAF.addView(textetotal);

                    }


//            -------------- FRAIS HORS FORFAIT ------------------------



                    final TableRow tableRowTitreFHF = new TableRow(Synthese_Activity.this);
                    tableRowTitreFHF.setGravity(Gravity.CENTER);

                    final TextView titre1FHF = new TextView(Synthese_Activity.this);
                    titrePara.leftMargin = 50;
                    titrePara2.bottomMargin = 1;

                    titre1FHF.setText("Libelle");
                    titre1FHF.setTextSize(15);
                    titre1FHF.setLayoutParams(titrePara2);
                    titre1FHF.setTypeface(Typeface.DEFAULT_BOLD);
                    tableRowTitreFHF.addView(titre1FHF);

                    final TableRow.LayoutParams texte2ParamFHF = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    texte2ParamFHF.leftMargin=180;
                    final TextView titre2FHF = new TextView(Synthese_Activity.this);
                    titre2FHF.setText("Date");
                    titre2FHF.setTextSize(15);
                    titre2FHF.setTypeface(Typeface.DEFAULT_BOLD);
                    tableRowTitreFHF.addView(titre2FHF);
                    titre2FHF.setLayoutParams(texte2ParamFHF);


                    final TextView titre3FHF = new TextView(Synthese_Activity.this);
                    titre3FHF.setText("Montant");
                    titre3FHF.setTextSize(15);
                    titre3FHF.setTypeface(Typeface.DEFAULT_BOLD);
                    tableRowTitreFHF.addView(titre3FHF);


                    tablleSyntheseFHF.addView(tableRowTitreFHF);


                    if (cursorFHF.moveToFirst() == true ) {
                        do {
                            //creation TableRow
                            final TableRow.LayoutParams rowFHF = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            rowFHF.setMargins(10,10,10,10);
                            final TableRow tableRowFHF = new TableRow(Synthese_Activity.this);
                            tableRowFHF.setLayoutParams(rowFHF);
                            tableRowFHF.setId(numberID);
                            tableRowFHF.setGravity(Gravity.CENTER);

                            final TableRow.LayoutParams texte1ParamFHF = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            final TextView texte1FHF = new TextView(Synthese_Activity.this);
                            texte1ParamFHF.leftMargin = 20;
                            texte1FHF.setText(cursorFHF.getString(0));
                            texte1FHF.setPadding(10, 0, 10, 0);
                            texte1FHF.setTextSize(15);
                            texte1FHF.setLayoutParams(texte1ParamFHF);
                            tableRowFHF.addView(texte1FHF);

                            final TableRow.LayoutParams texte2Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            final TextView texte2 = new TextView(Synthese_Activity.this);
                            texte2Param.leftMargin = 150;
                            texte2.setText(cursorFHF.getString(2));
                            texte2.setPadding(10, 0, 10, 0);
                            texte2.setTextSize(15);
                            texte2.setLayoutParams(texte2Param);
                            tableRowFHF.addView(texte2);

                            final TableRow.LayoutParams texte3Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            final TextView texte3 = new TextView(Synthese_Activity.this);
                            texte3Param.leftMargin = 150;
                            texte3.setText(cursorFHF.getString(1));
                            texte3.setPadding(10, 0, 10, 0);
                            texte3.setTextSize(15);
                            texte3.setLayoutParams(texte2Param);
                            tableRowFHF.addView(texte3);


                            tablleSyntheseFHF.addView(tableRowFHF);

                        } while (cursorFHF.moveToNext());
                    }


                    Cursor totalFHF = dao.getSynthesefhfTotal(frais);
                    totalFHF.moveToFirst();
                    if(totalFAF.moveToFirst()  == true ){
                        final TextView textetotalFHF = new TextView(Synthese_Activity.this);
                        textetotalFHF.setTypeface(Typeface.DEFAULT_BOLD);
                        textetotalFHF.setText("Total frais hors forfait : " + totalFHF.getString(0) + " euros");
                        textetotalFHF.setPadding(10, 0, 10, 0);
                        textetotalFHF.setTextSize(15);
                        tablleSyntheseFHF.addView(textetotalFHF);
                    }

                    Cursor totalALL = dao.getSynthesefafANDfhfTotal(frais);
                    totalALL.moveToFirst();

                    if(totalALL.moveToFirst() == true){

                        final TextView textetotalALL = new TextView(Synthese_Activity.this);
                        textetotalALL.setTypeface(Typeface.DEFAULT_BOLD);
                        textetotalALL.setText("Montant total : " + totalALL.getString(0) + " euros");
                        textetotalALL.setPadding(10, 0, 10, 0);
                        textetotalALL.setTextSize(15);
                        textetotalALL.setGravity(Gravity.CENTER);
                        textetotalALL.setBackground(getDrawable(R.drawable.border));

                        tablleSyntheseFHF.addView(textetotalALL);
                    }




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                Toast.makeText(Synthese_Activity.this, "rien selectionné", Toast.LENGTH_SHORT).show();


            }
        });

    }






    }
