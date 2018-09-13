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

    public String nomeMedico, sobrenomeMedico, emailMedico, cpfMedico, passwordMedico, telefoneMedico, sexoMedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil_medico);
        
        final TextView nomeMedico = findViewById(R.id.nomeMedico);
        final TextView emailMedico = findViewById(R.id.emailMedico);
        final TextView cpfMedico = findViewById(R.id.cpfMedico);
        final TextView sexoMedico = findViewById(R.id.sexoMedico);
        final TextView telefoneMedico = findViewById(R.id.telefoneMedico);

        nomeMedico.setText(TelaLogin.getMedicoLogado().getNome());
        emailMedico.setText(TelaLogin.getMedicoLogado().getEmail());
        cpfMedico.setText(TelaLogin.getMedicoLogado().getCrm());
        sexoMedico.setText(TelaLogin.getMedicoLogado().getGenero());
        telefoneMedico.setText(TelaLogin.getMedicoLogado().getTelefone());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_perfil_medico);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent it = new Intent(VisualizarPerfilMedico.this, EditarPerfilMedico.class);
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



