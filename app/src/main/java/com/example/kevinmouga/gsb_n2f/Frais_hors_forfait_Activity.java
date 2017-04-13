package com.example.kevinmouga.gsb_n2f;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.drawable.radiobutton_off_background;
import static android.R.drawable.radiobutton_on_background;

public class Frais_hors_forfait_Activity extends AppCompatActivity {

    private ImageView imageBT;
    int year_x,month_x,day_x;
    static final int DILOG_ID = 0 ;
    String dateCompare;

    private EditText libelleFHF;
    private EditText montantFAF;
    private EditText dateFHF;
    private TableRow layouTitre;

    private Utilisateur oUtilisateur;
    private Frais oFrais;
    private FraisHorsForfait oFraisHorsForfaitForfait ;
    private Dao dao;

    private static Button envoyer;

    private static TextView value1;
    private static TextView value2;
    private static TextView value3;
    private static EditText edit1;
    private static Button modifierFHF;
    private static Button supprimerFHF;
    private static ImageView radio11;
    private static ImageView radio12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frais_hors_forfait);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogButtonOnclick();

        dao = new Dao(this);

        libelleFHF = (EditText) findViewById(R.id.libelleFHF);
        montantFAF = (EditText) findViewById(R.id.montantFHF);
        dateFHF = (EditText) findViewById(R.id.dateFHF);

        envoyer = (Button) findViewById(R.id.ajouterFhf);
        layouTitre = (TableRow) findViewById(R.id.layouTitre);


        envoyer.setOnClickListener(actionAjouter);


        //Creation d'un tableau qui contiendra toutes les listes FAF
        final TableLayout layoutFHF = (TableLayout) findViewById(R.id.layoutFHF);
        Cursor cursor = dao.getAllLigneFraisHorsForfait();
        int numberID = 0;
        if (cursor.moveToFirst()) {
            do {
                //creation TableRow
                final TableRow tableRow = new TableRow(this);
//                tableRow.setGravity(Gravity.CENTER);
                tableRow.setId(numberID);

                //creation des texte view qui contiendront les values de la BDD
                final TableRow.LayoutParams texte1Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte1Param.leftMargin=30;
                final TextView texte1 = new TextView(this);
                texte1.setText(cursor.getString(0));
                texte1.setPadding(10, 0, 10, 0);
                texte1.setTextSize(15);
                texte1.setLayoutParams(texte1Param);
                tableRow.addView(texte1);

                final TableRow.LayoutParams texte2Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte2Param.leftMargin=100;
                texte2Param.width=300;
                final TextView texte2 = new TextView(this);
                texte2.setText(cursor.getString(1));
                texte2.setPadding(10, 0, 10, 0);
                texte2.setTextSize(15);
                texte2.setLayoutParams(texte2Param);
                tableRow.addView(texte2);

                final TableRow.LayoutParams texte3Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte3Param.leftMargin=20;
                texte3Param.width=100;
                final TextView texte3 = new TextView(this);
                texte3.setText(cursor.getString(2));
                texte3.setPadding(10, 0, 10, 0);
                texte3.setTextSize(15);
                texte3.setLayoutParams(texte3Param);
                tableRow.addView(texte3);

                //ID
                final TextView textid = new TextView(this);
                textid.setText(cursor.getString(3));
                textid.setVisibility(View.GONE);

                //création d'un edite texte qui s'activera quand l'user clique sur le champs TextView(MONTANT)
                final TableRow.LayoutParams editParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                editParam.leftMargin=20;
                editParam.width=100;
                final EditText edit = new EditText(Frais_hors_forfait_Activity.this);
                edit.setText(cursor.getString(2));
                edit.setPadding(10, 0, 10, 0);
                edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                edit.setTextSize(15);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                edit.setLayoutParams(editParam);
                edit.setBackgroundResource(R.drawable.champ_white);
                edit.setVisibility(View.GONE);
                tableRow.addView(edit);

                final TableRow.LayoutParams texte4Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte4Param.leftMargin = 100;
                final ImageView radio1 = new ImageView(this);
                radio1.setImageResource(radiobutton_off_background);
                radio1.setLayoutParams(texte4Param);
                tableRow.addView(radio1);

                final TableRow.LayoutParams texte5Param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                texte5Param.leftMargin = 100;
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
                boutonSuppParam.height=95;
                boutonSuppParam.width=300;
                boutonSuppParam.setMargins(10,10,10,10);
                final Button supp = new Button(Frais_hors_forfait_Activity.this);
                supp.setBackground(getDrawable(R.drawable.button_red));
                supp.setLayoutParams(boutonSuppParam);
                supp.setTextSize(10);
                supp.setText("supprimer");
                supp.setTextColor(Color.WHITE);
                supp.getParent();
                supp.setVisibility(View.GONE);
                tableRowButton.addView(supp);


                final TableRow.LayoutParams boutonModifierParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                boutonModifierParam.height=95;
                boutonModifierParam.width=300;
                boutonModifierParam.setMargins(10,10,10,10);
                final Button modifier = new Button(Frais_hors_forfait_Activity.this);
                modifier.setBackground(getDrawable(R.drawable.button_orange));
                modifier.setText("modifier");
//                modifier.setLayoutParams(tableRowLayout);
                modifier.setTextSize(10);
                modifier.getParent();
                modifier.setLayoutParams(boutonModifierParam);
                modifier.setTextColor(Color.WHITE);
                modifier.setVisibility(View.GONE);
                tableRowButton.addView(modifier);
                if(numberID %2!=0) {
                    tableRow.setBackgroundColor(Color.parseColor("#f2f9ff"));
                    tableRowButton.setBackgroundColor(Color.parseColor("#f2f9ff"));
                };

                layoutFHF.addView(tableRow);
                layoutFHF.addView(tableRowButton);
                numberID++;

                //action qui se declenche au moment du clique affiche les boutons modifier supprimer
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //affiche les bouttons et le edittext
                        supp.setVisibility(View.VISIBLE);
                        modifier.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.VISIBLE);
                        texte3.setVisibility(View.GONE);
                        radio1.setVisibility(View.GONE);
                        radio2.setVisibility(View.VISIBLE);

                        //recupére les values pour les stockers
//                        if(value1 == null){
//                            value1 = texte1;
//                            value2 = texte2;
//                            value3 = texte3;
//                            edit1 = edit;
//                            modifierFHF = modifier;
//                            supprimerFHF = supp;
//                        }

                        //grace à cela on peut cacher les boutons quand l'user click sur un autre endroit
                        if( value1 != null) {
                            value1.setVisibility(View.VISIBLE);
                            value2.setVisibility(View.VISIBLE);
                            value3.setVisibility(View.VISIBLE);
                            edit1.setVisibility(View.GONE);
                            modifierFHF.setVisibility(View.GONE);
                            supprimerFHF.setVisibility(View.GONE);
                            radio11.setVisibility(View.VISIBLE);
                            radio12.setVisibility(View.GONE);
                        }
                        //recupére les values pour les stockers
                        value1 = texte1;
                        value2 = texte2;
                        value3 = texte3;
                        edit1 = edit;
                        modifierFHF = modifier;
                        supprimerFHF = supp;
                        radio11 = radio1;
                        radio12 = radio2;

                        supp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                oFraisHorsForfaitForfait = new FraisHorsForfait();
                                String valueid = textid.getText().toString();
                                oFraisHorsForfaitForfait.setIdFHF(valueid);
                                long resultat = dao.deleteFHF(oFraisHorsForfaitForfait);
                                String resuLong= Technique.reultatLong(String.valueOf(resultat));
                                Toast.makeText(Frais_hors_forfait_Activity.this, resuLong , Toast.LENGTH_SHORT).show();

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                            }
                        });

                        modifier.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                oFraisHorsForfaitForfait = new FraisHorsForfait();
                                String valueid = textid.getText().toString();
                                String valueMontant = edit1.getText().toString();

                                oFraisHorsForfaitForfait.setIdFHF(valueid);
                                oFraisHorsForfaitForfait.setMontant(Double.parseDouble(valueMontant));
                                long resultat = dao.updateFHF(oFraisHorsForfaitForfait);
                                String resuLong= Technique.reultatLong(String.valueOf(resultat));
                                Toast.makeText(Frais_hors_forfait_Activity.this, resuLong , Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                    }
                });
            } while (cursor.moveToNext());
        }


        ScrollView scroll = (ScrollView) findViewById(R.id.scrollFhf);

        scroll.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                Intent fraisForfait = new Intent(Frais_hors_forfait_Activity.this, Frais_au_forfait_Activity.class);
                startActivity(fraisForfait);
            }
            @Override
            public void onSwipeLeft() {
                Intent synthese = new Intent(Frais_hors_forfait_Activity.this, Synthese_Activity.class);
                startActivity(synthese);
            }
        });

    }

//    ---------------------------------methode-----------------------------------------------------------------


    //-------------CALENDAR--------------
    public void showDialogButtonOnclick(){
        imageBT = (ImageView) findViewById(R.id.calendarButton) ;
        imageBT.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        showDialog(DILOG_ID);
                    }
                }
        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DILOG_ID)
            return new DatePickerDialog(this,dpkListner ,year_x,month_x,day_x );
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpkListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            dateCompare = String.valueOf(day_x)+""+String.valueOf(month_x)+""+String.valueOf(year_x);
            dateFHF.setText(day_x+"/"+month_x+"/"+year_x);
        }
    };


    //    --------------- AJOUT FHF -------------------
    private View.OnClickListener  actionAjouter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(montantFAF.getText().toString().equals("") || libelleFHF.getText().toString().equals("") || dateFHF.getText().toString().equals("") || Integer.parseInt(montantFAF.getText().toString()) <=0) {
                Toast.makeText(Frais_hors_forfait_Activity.this,"Entrer une valeur correct", Toast.LENGTH_SHORT).show();
            }else {

                //On compare si la date selectionné est supperrieur à aujourd'hui
                try{

                    //On convertie la date user en format date
                    SimpleDateFormat formatter = new SimpleDateFormat("dMyyyy");
                    Date date1 = formatter.parse(dateCompare);

                    //On recupere la date now
                    SimpleDateFormat formatter2  = new SimpleDateFormat ("dMyyyy");
                    Date currentTime_1 = new Date();
                    String datePrestaNow = formatter.format(currentTime_1);
                    Date date2 = formatter.parse(datePrestaNow);

                    //On compare les 2 date avec la fonction compareDate
                    Boolean resu2= Technique.compareDate(date1,date2);

                    if( resu2 == false){
                        Cursor resu = dao.selectUser();

                        if(resu.moveToFirst()) {
                            oUtilisateur = new Utilisateur(
                                    resu.getString(0),
                                    resu.getString(1),
                                    resu.getString(2),
                                    resu.getString(3),
                                    resu.getString(4));
                            oFrais = new Frais(Technique.datenow(),oUtilisateur);
                            oFraisHorsForfaitForfait = new FraisHorsForfait(
                                    Technique.datenow(),
                                    oUtilisateur,
                                    libelleFHF.getText().toString(),
                                    dateFHF.getText().toString(),
                                    Double.parseDouble(montantFAF.getText().toString()));

                            long resultat = dao.createFraisHorsForfait(oFraisHorsForfaitForfait,oFrais);
                            Toast.makeText(Frais_hors_forfait_Activity.this,"La prestation a bien été ajouté", Toast.LENGTH_SHORT).show();
                            //on recharge la page une fois l'action fini
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }

                    }else {
                        Toast.makeText(Frais_hors_forfait_Activity.this,"Date supérieur à aujourd'hui", Toast.LENGTH_SHORT).show();
                    }

                }catch (ParseException e1){
                    e1.printStackTrace();
                }

            }
        }
    };

}