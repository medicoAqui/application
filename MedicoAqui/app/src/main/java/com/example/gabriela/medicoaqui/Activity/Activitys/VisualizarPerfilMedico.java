package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gabriela.medicoaqui.R;

public class VisualizarPerfilMedico extends AppCompatActivity {

    public String nome, sobrenome, email, cpf, password, telefone, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil_medico);
        
        final TextView nomeCliente = findViewById(R.id.nomeCliente);
        final TextView emailCliente = findViewById(R.id.emailCliente);
        final TextView cpfCliente = findViewById(R.id.cpfCliente);
        final TextView sexoCliente = findViewById(R.id.sexoCliente);
        final TextView telefoneCliente = findViewById(R.id.telefoneCliente);

        nomeCliente.setText(TelaLogin.medicoLogado.getNome());
        emailCliente.setText(TelaLogin.medicoLogado.getEmail());
        cpfCliente.setText(TelaLogin.medicoLogado.getCrm());
        sexoCliente.setText(TelaLogin.medicoLogado.getGenero());
        telefoneCliente.setText(TelaLogin.medicoLogado.getTelefone());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_perfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent it = new Intent(VisualizarPerfilMedico.this, EditarPerfil.class);
                startActivity(it);

            }
        });


        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(VisualizarPerfilMedico.this, MenuPrincipalMedico.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(VisualizarPerfilMedico.this, MenuPrincipalMedico.class);
                startActivity(it);

            }
        });

    }

    @Override
    public void onBackPressed()
    {

        Intent it = new Intent(VisualizarPerfilMedico.this, MenuPrincipalMedico.class);
        startActivity(it);

    }

}



