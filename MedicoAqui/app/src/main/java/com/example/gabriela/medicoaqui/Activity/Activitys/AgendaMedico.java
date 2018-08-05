package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class AgendaMedico extends AppCompatActivity {

    private static final String TAG = "AgendaMedico";

    JSONObject jsonTT = new JSONObject();
    static JSONReader jsonReader = new JSONReader();
    static HttpConnections http = new HttpConnections();
    static ArrayList<Consulta> lista_consultas_entity;
    public static String consulta;

    public RecyclerView recyclerView;
    public TextView emptyView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_medico);

        try {
            carregaAgendaMedico(TelaLogin.medicoLogado);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Falha ao recuperar o cliente da sessão.");
        }

        try {
            while (lista_consultas_entity == null) {
                Thread.sleep(1000);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("Acompanhando", "Lista de consultas = " + lista_consultas_entity.toString());

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new NossoAdapterMedico(lista_consultas_entity, this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        emptyView = (TextView) findViewById(R.id.empty_view);

        if (lista_consultas_entity.isEmpty()) {
            Log.d("Acompanhando", "Exibindo mensagem de agenda vazia");
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(AgendaMedico.this, MenuPrincipalMedico.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(AgendaMedico.this, MenuPrincipalMedico.class);
                startActivity(it);

            }
        });
    }
    private void carregaAgendaMedico(Medico medico) throws JSONException {

        Log.d(TAG, "carregaAgendaMedico() called");

//        lista_consultas_entity.clear();
        final JSONObject jsonTT = new JSONObject();
        jsonTT.put("id", medico.getId());
        jsonTT.put("status", "A");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = null;
                try {
                    consultasBD = http.sendPost("https://medicoishere.herokuapp.com/consulta/consultaByIdMedicoAndStatus",jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }

                HashSet<Consulta> consultasEntity = jsonReader.getConsultasEntity(consultasBD);

                lista_consultas_entity = new ArrayList();
                lista_consultas_entity.addAll(consultasEntity);

            }
        }).start();
    }

    public void criaReciclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new NossoAdapterMedico(lista_consultas_entity, this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

    }

    public static Consulta getConsultaEntity() {

        Consulta consultaEntity = new Consulta(null, null, null, null, null, null, null,null, null);

        for (int i = 0; i < lista_consultas_entity.size(); i++) {
            String consultaConfere = lista_consultas_entity.get(i).getMedico() + " - " + lista_consultas_entity.get(i).getDataConsulta() + " - " + lista_consultas_entity.get(i).getHora();
            if (consultaConfere.equals(consulta) ) {
                consultaEntity = lista_consultas_entity.get(i);
            }
        }

        return consultaEntity;
    }


    /*public void desmarcarConsulta(String idConsulta) throws JSONException {

        //put /idconsulta

        Log.d(TAG, "desmarcarConsulta() called");

        final JSONObject jsonTT = new JSONObject();

        jsonTT.put("status", "C"); //Status Cancelado - Cliente
        jsonTT.put("cliente", null);

        final String url = "https://medicoishere.herokuapp.com/consulta/" + idConsulta;

        new Thread(new Runnable() {
            @Override
            public void run() {
            String consultasBD = null;
                try {
                    http.put(url, jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }*/

//    private static AlertDialog alerta;

    /*public  void dialogo_desmarcar(final String idConsulta) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desmarcar consulta");
        builder.setMessage("Você deseja confirmar o cancelamento da consulta agendada?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    desmarcarConsulta(idConsulta);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alerta.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }*/

}

