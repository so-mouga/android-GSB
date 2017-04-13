package com.example.kevinmouga.gsb_n2f;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Activity extends AppCompatActivity {
    private Button btnFf;
    private Button btnFhf;
    private Button btnProfils;
    private Button btnSynthese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnFf = (Button) findViewById(R.id.frais_au_forfait);
        btnFhf = (Button) findViewById(R.id.frais_hors_forfait);
        btnProfils = (Button) findViewById(R.id.parametres);
        btnSynthese = (Button) findViewById(R.id.synthese);


        btnFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fraisForfait = new Intent(Menu_Activity.this, Frais_au_forfait_Activity.class);
                startActivity(fraisForfait);
            }
        });

        btnFhf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fraisFhf = new Intent(Menu_Activity.this,Frais_hors_forfait_Activity.class);
                startActivity(fraisFhf);
            }
        });

        btnProfils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profils = new Intent(Menu_Activity.this,Profils_Activity.class);
                startActivity(profils);
            }
        });

        btnSynthese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fraisFhf = new Intent(Menu_Activity.this,Synthese_Activity.class);
                startActivity(fraisFhf);

            }
        });



    }
}
