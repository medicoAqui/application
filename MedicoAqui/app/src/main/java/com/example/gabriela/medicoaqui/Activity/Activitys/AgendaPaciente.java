package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class AgendaPaciente  extends AppCompatActivity {

    private static final String TAG = "AgendaPaciente";

    JSONObject jsonTT = new JSONObject();
    static JSONReader jsonReader = new JSONReader();
    static HttpConnections http = new HttpConnections();
    static ArrayList<Consulta> lista_consultas_entity = new ArrayList();
    public static String consulta;

    public Boolean flagCarregaConsultasCalled;

    public RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_paciente);

        //carregaConsultas();
        try {
            flagCarregaConsultasCalled = false;
            carregaConsultasAgendasPorCliente(TelaLogin.getClientePerfil());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Falha ao recuperar o cliente da sessão.");
        }

        try {
            while ((lista_consultas_entity.size() == 0) && !flagCarregaConsultasCalled) {
                Thread.sleep(1000);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ((lista_consultas_entity.size() == 0) && flagCarregaConsultasCalled) {

            dialogo_agenda_vazia();

        } else {

            // Ordena lista de consultas por data/hora
            Collections.sort(lista_consultas_entity);

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

    }



    private void carregaConsultasAgendasPorCliente(Cliente cliente) throws JSONException {

        Log.d(TAG, "carregaConsultasAgendasPorCliente() called");

        lista_consultas_entity.clear();
        final JSONObject jsonTT = new JSONObject();
        jsonTT.put("cpf", cliente.getCpf()); //Status Cancelado - Cliente
        jsonTT.put("status", "A");

        Thread thr_carregaConsultasAgendaCliente = new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = null;
                try {
                    consultasBD = http.sendPost("https://medicoishere.herokuapp.com/consulta/consultasByCpfClienteAndStatus",jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
                HashSet<Consulta> consultasEntity = jsonReader.getConsultasEntity(consultasBD);
                lista_consultas_entity.addAll(consultasEntity);
                flagCarregaConsultasCalled = true;
            }
        });

        thr_carregaConsultasAgendaCliente.start();

    }


    private static AlertDialog alerta;

    public void dialogo_agenda_vazia() {

        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.activity_dialogo_agenda_vazia, null);

        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alerta.dismiss();
                Intent it = new Intent(AgendaPaciente.this, MenuPrincipal.class);
                startActivity(it);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agenda");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();

    }


}

