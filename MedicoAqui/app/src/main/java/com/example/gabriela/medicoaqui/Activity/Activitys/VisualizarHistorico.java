package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class VisualizarHistorico extends AppCompatActivity {


    private static final String TAG = "VisualizarHistorico";

    JSONObject jsonTT = new JSONObject();
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();
    static ArrayList<Consulta> lista_minhas_consultas = new ArrayList<Consulta>();

    public static String medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_historico);

        carregaMinhaAgenda();
/*
        for (int i = 0; i < lista_minhas_consultas.size(); i++) {

            final TextView nomeMedico = findViewById(R.id.emailCliente);
            final TextView crmMedico = findViewById(R.id.cpfCliente);
            final TextView dataConsulta = findViewById(R.id.sexoCliente);
            final TextView hora = findViewById(R.id.telefoneCliente);
            final Button desmarcar = (Button) findViewById(R.id.button);

            nomeMedico.setText(lista_minhas_consultas.get(i).getMedico());
            crmMedico.setText(lista_minhas_consultas.get(i).getMedico());
            dataConsulta.setText(lista_minhas_consultas.get(i).getDataConsulta());
            hora.setText(lista_minhas_consultas.get(i).getHora());

            desmarcar.setText("Desmarcar consulta");



        };
*/

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Bem vindo ao Visualizar Histórico", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*
        button_localizacao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                cidade_uf.setNomeCidade(cidade);
                cidade_uf.setNomeEstado(estado);
                cidade_uf.setSiglaEstado(buscaSiglaEstado(estado));

                Intent it = new Intent(Localizacao.this, Especialidade.class);
                startActivity(it);

            }
        });
        */

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(VisualizarHistorico.this, MenuPrincipal.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(VisualizarHistorico.this, MenuPrincipal.class);
                startActivity(it);

            }
        });
    }

    private void carregaMinhaAgenda() {

        final JSONObject jsonTT = new JSONObject();

        try {
            jsonTT.put("cliente", TelaLogin.getClientePerfil().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String minhaAgendaBD = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultaByDateAndCpfCliente", jsonTT.toString());
                    HashSet<Consulta> minhaAgenda = jsonReader.getMinhaAgenda(minhaAgendaBD);
                    lista_minhas_consultas.addAll(minhaAgenda);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
