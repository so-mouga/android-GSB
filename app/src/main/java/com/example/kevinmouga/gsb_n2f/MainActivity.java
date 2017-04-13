package com.example.kevinmouga.gsb_n2f;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mdp;
    private TextView textUser;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mdp = (EditText) findViewById(R.id.password);
        textUser = (TextView) findViewById(R.id.textUser);
        dao = new Dao(this);
        Cursor resu = dao.selectUser();

        if( resu.moveToFirst() == false){
            Intent menuPrincipal = new Intent(MainActivity.this, Profils_Activity.class);
            startActivity(menuPrincipal);
        }

        if(resu.moveToFirst()){
            textUser.setText(" Bonjour "+String.valueOf( resu.getString(2)));
        }
    }



    public void btmdp(View v){
        dao = new Dao(this);
        Cursor resu = dao.selectUser();

        if(resu.moveToFirst()){

            if(resu.getString(3).equals(mdp.getText().toString())){

                Intent menuPrincipal = new Intent(MainActivity.this, Menu_Activity.class);
                startActivity(menuPrincipal);

            } else{
                textUser.setText(String.valueOf("Mot de passe incorrect"));
                Toast.makeText(MainActivity.this,"Incorrect", Toast.LENGTH_SHORT).show();
            }
        }

    }





}