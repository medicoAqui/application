package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.gabriela.medicoaqui.R;

public class MedicoAqui extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_consultas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
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
}
