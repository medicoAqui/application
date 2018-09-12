package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gabriela.medicoaqui.R;

public class SelecaoTelaCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_tela_cadastro);

        final ImageView button_cadastro_pac =  findViewById(R.id.button_cadastro_paciente);
        button_cadastro_pac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(SelecaoTelaCadastro.this, TelaCadastro.class);
                startActivity(it);

            }
        });

        final ImageView button_cadastro_med =  findViewById(R.id.button_cadastro_medico);
        button_cadastro_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(SelecaoTelaCadastro.this, TelaCadastroMedico.class);
                startActivity(it);

            }
        });
    }
}
