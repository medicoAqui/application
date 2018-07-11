package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.google.firebase.auth.FirebaseAuth;

public class MedicoAqui extends AppCompatActivity {

    String resposta = "Init";
    private static HttpConnections http = new HttpConnections();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_consultas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    resposta =  http.get("http://medicoishere.herokuapp.com/medico/medicos");

                } finally {
                    // Não faz nada.
                }
            }
        }).start();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intentFazerLogout = new Intent(getApplicationContext(), TelaLogin.class);
                startActivity(intentFazerLogout);
                finish();
                mAuth.signOut();
                Log.d("Logout", "saindo da aplicação");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void clickVisualizarPerfil(View v) {
        Intent sendIntentPerfil = new Intent(this, VisualizarPerfil.class);
        startActivity(sendIntentPerfil);
    }

    public void clickEditarPerfil(View v) {
        Intent sendIntentEdPerfil = new Intent(this, EditarPerfil.class);
        startActivity(sendIntentEdPerfil);
    }

    public void clickMarcarConsulta(View v) {
        Intent sendIntent = new Intent(this, MarcarConsultaActivity.class);
        sendIntent.putExtra("intent", resposta);
        startActivity(sendIntent);
    }

    public void clickHistoricoConsulta(View v) {
        Intent sendIntent = new Intent(this, VisualizarHistorico.class);
        startActivity(sendIntent);
    }

    public void clickPerguntasFrequentes(View v) {
        Intent sendIntent = new Intent(this, PerguntasFrequentesActivity.class);
        startActivity(sendIntent);
    }

    public void clickLocalizacao(View v) {
        Intent sendIntent = new Intent(this, Localizacao.class);
        startActivity(sendIntent);
    }


    public void clickMenuPrincipal(View v) {
        Intent sendIntent = new Intent(this, MenuPrincipal.class);
        startActivity(sendIntent);
    }

}
