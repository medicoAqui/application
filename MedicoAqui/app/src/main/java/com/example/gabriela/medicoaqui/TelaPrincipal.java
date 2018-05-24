package com.example.gabriela.medicoaqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        Button botaoCadastrar = (Button) findViewById(R.id.button_cadastro);
        Button botaoEntrar = (Button) findViewById(R.id.button_login);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setContentView(R.layout.activity_tela_cadastro);
            }
        });

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_tela_login);
            }


        });
    }
}

