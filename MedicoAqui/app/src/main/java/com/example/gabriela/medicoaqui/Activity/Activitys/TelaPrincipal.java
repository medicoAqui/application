package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gabriela.medicoaqui.R;


public class TelaPrincipal extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        //getSupportActionBar().hide();

        Button botaoCadastrar = (Button) findViewById(R.id.button_cadastro);
        Button botaoEntrar = (Button) findViewById(R.id.button_login);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaPrincipal.this, TelaCadastro.class);
                startActivity(it);
            }

        });

        botaoEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaPrincipal.this, TelaLogin.class);
                startActivity(it);
            }

        });

    }
}



