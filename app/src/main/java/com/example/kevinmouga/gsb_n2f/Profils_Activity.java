package com.example.kevinmouga.gsb_n2f;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Profils_Activity extends AppCompatActivity {

    private EditText id;
    private EditText nom;
    private EditText prenom;
    private EditText password;
    private EditText email;
    private EditText adresse;
    private EditText ville;

    private Button bouton;

    private TextView viewId;
    private TextView viewNom;
    private TextView viewPrenom;


    private Utilisateur oUtilisateur;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profils);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        id = (EditText) findViewById(R.id.login);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        password = (EditText) findViewById(R.id.mdp);
        email = (EditText) findViewById(R.id.email);
        ville = (EditText) findViewById(R.id.ville);
        adresse = (EditText) findViewById(R.id.adresse);

        bouton = (Button) findViewById(R.id.button4);

        viewId = (TextView) findViewById(R.id.textViewLogin);
        viewNom = (TextView) findViewById(R.id.textViewnom);
        viewPrenom = (TextView) findViewById(R.id.textViewPrenom);

        dao = new Dao(this);
        Cursor resu = dao.selectUser();
        if( resu.moveToFirst()){

            id.setVisibility(View.GONE);
            viewId.setVisibility(View.VISIBLE);
            nom.setVisibility(View.GONE);
            viewNom.setVisibility(View.VISIBLE);
            prenom.setVisibility(View.GONE);
            viewPrenom.setVisibility(View.VISIBLE);

            bouton.setText("modifier");

            viewId.setText(resu.getString(0));
            viewNom.setText(resu.getString(1));
            viewPrenom.setText(resu.getString(2));
            password.setText(resu.getString(3));
            email.setText(resu.getString(4));


        }else {
            ville.setVisibility(View.GONE);
            adresse.setVisibility(View.GONE);
        }



    }


    public void sendData(View view){
        dao = new Dao(this);
        Cursor resu = dao.selectUser();



        if( resu.moveToFirst() == false){
            oUtilisateur = new Utilisateur(
                    id.getText().toString()
                    ,nom.getText().toString()
                    ,prenom.getText().toString()
                    ,password.getText().toString()
                    ,email.getText().toString());


            long resultat = dao.createUtilisateur(oUtilisateur);
            String resultatCreate =String.valueOf(resultat);

            if(!Objects.equals(resultatCreate, "-1")){
                Toast.makeText(Profils_Activity.this, "Votre profils à bien été créé", Toast.LENGTH_SHORT).show();
                Intent menuPrincipal = new Intent(Profils_Activity.this, MainActivity.class);
                startActivity(menuPrincipal);
            }else {
                Toast.makeText(Profils_Activity.this, "erreur", Toast.LENGTH_SHORT).show();
            }


        }else{
            oUtilisateur = new Utilisateur(viewId.getText().toString()
                    ,viewNom.getText().toString()
                    ,viewNom.getText().toString()
                    ,password.getText().toString()
                    ,email.getText().toString());
            long resulol = dao.updateUser(oUtilisateur);
            String resultatCreate =String.valueOf(resulol);

            if(!Objects.equals(resultatCreate, "-1")){
                Toast.makeText(Profils_Activity.this, "Votre profils à bien été changé", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }else {
                Toast.makeText(Profils_Activity.this, "erreur", Toast.LENGTH_SHORT).show();
            }


        }





    }
}
