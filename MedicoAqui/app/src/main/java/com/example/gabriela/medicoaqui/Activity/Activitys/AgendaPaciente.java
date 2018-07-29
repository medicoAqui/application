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

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class AgendaPaciente  extends AppCompatActivity {

    private static final String TAG = "AgendaPaciente";

    JSONObject jsonTT = new JSONObject();
    static JSONReader jsonReader = new JSONReader();
    static HttpConnections http = new HttpConnections();
    static ArrayList<Consulta> lista_consultas_entity = new ArrayList();
    static ArrayList<String> lista_consultas = new ArrayList<String>(){{add("Selecione");}};
    public static String consulta;
    public static Consulta consultaInfo;
    public static String idConsulta;

    public RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_paciente);

        //carregaConsultas();
        try {
            carregaConsultasAgendasPorCliente(TelaLogin.getClientePerfil());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Falha ao recuperar o cliente da sessão.");
        }

        try {
            while (lista_consultas_entity.size() == 0) {
                Thread.sleep(1000);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new NossoAdapter(lista_consultas_entity, this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(AgendaPaciente.this, MenuPrincipal.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(AgendaPaciente.this, MenuPrincipal.class);
                startActivity(it);

            }
        });
    }

    private void carregaConsultas() throws JSONException {

        Log.d(TAG, "carregaConsultas() called");

        lista_consultas_entity.clear();
        final JSONObject jsonTT = new JSONObject();

        String cpf_cliente = TelaLogin.getClientePerfil().getCpf();
        jsonTT.put("cpf", cpf_cliente);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = null;
                try {
                    consultasBD = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultasByCpfCliente", jsonTT.toString());

                    //String consultasBD = http.get("https://medicoishere.herokuapp.com/consulta/consultas");
                    HashSet<Consulta> consultasEntity = jsonReader.getConsultasEntity(consultasBD);
                    //HashSet<String> consultasStr = jsonReader.getConsultas(consultasBD);
                    lista_consultas_entity.addAll(consultasEntity);
                    //lista_consultas.addAll(consultasStr);

                //criaReciclerView();
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void carregaConsultasAgendasPorCliente(Cliente cliente) throws JSONException {

        Log.d(TAG, "carregaConsultasAgendasPorCliente() called");

        lista_consultas_entity.clear();
        final JSONObject jsonTT = new JSONObject();
        jsonTT.put("cpf", cliente.getCpf()); //Status Cancelado - Cliente
        jsonTT.put("status", "A");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = null;
                try {
                    consultasBD = http.sendPost("https://medicoishere.herokuapp.com/consulta/consultasByCpfClienteAndStatus",jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
                HashSet<Consulta> consultasEntity = jsonReader.getConsultasEntity(consultasBD);
                //HashSet<String> consultasStr = jsonReader.getConsultas(consultasBD);
                lista_consultas_entity.addAll(consultasEntity);
                //lista_consultas.addAll(consultasStr);

                //criaReciclerView();

            }
        }).start();

    }


    public void criaReciclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new NossoAdapter(lista_consultas_entity, this));
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


    public void desmarcarConsulta(String idConsulta) throws JSONException {

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

    }

    private static AlertDialog alerta;

    public  void dialogo_desmarcar(final String idConsulta) {

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
    }

}

