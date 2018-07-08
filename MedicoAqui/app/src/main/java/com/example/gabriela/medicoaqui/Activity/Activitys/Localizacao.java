package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.widget.Spinner;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;


import com.example.gabriela.medicoaqui.Activity.Entities.Cidade;
import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.Entities.Estado;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import static android.widget.AdapterView.*;

public class Localizacao  extends AppCompatActivity {

    JSONObject jsonTT = new JSONObject();
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();
    ArrayList<String> lista_estados = new ArrayList<String>(){{add("Selecione");}};
    ArrayList<Estado> lista_estados_entity = new ArrayList();
    ArrayList<String> lista_cidades = new ArrayList<String>(){{add("Selecione");}};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);

        final Spinner spinner_estado =  findViewById(R.id.spinner_estado);
        final Spinner spinner_cidade =  findViewById(R.id.spinner_cidade);

        ArrayAdapter<String> dataAdapterEstado = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_estados);
        dataAdapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado.setAdapter(dataAdapterEstado);

        ArrayAdapter<String> dataAdapterCidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_cidades);
        dataAdapterCidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cidade.setAdapter(dataAdapterCidade);

        carregaEstados();

        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id){

                String estado = spinner_estado.getSelectedItem().toString();
                if (estado.equals("Selecione")){
                    spinner_cidade.setEnabled(false);
                } else {
                    spinner_cidade.setEnabled(true);
                    spinner_cidade.setSelection(0);
                    //carregaCidades()
                    /*limpaListaDeMedicos();
                                                                limpaLista(lista_entity_medico);
                                                                carregaMedicosEmLista(especializacao);
                                                                carregaMedicosEmListaEntity(especializacao);*/

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id){

                String cidade = spinner_cidade.getSelectedItem().toString();
                //if (cidade.equals("Selecione")){
                 //   spinner_cidade.setEnabled(false);
                //} else {
                    //spinner_cidade.setEnabled(true);
                    spinner_cidade.setSelection(0);
                    //carregaCidades()
                    /*limpaListaDeMedicos();
                                                                limpaLista(lista_entity_medico);
                                                                carregaMedicosEmLista(especializacao);
                                                                carregaMedicosEmListaEntity(especializacao);*/

                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void carregaEstados() {
        final JSONObject jsonTT = new JSONObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String estadosBD = http.get("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
                HashSet<String> estados = jsonReader.getEstados(estadosBD);
                lista_estados.addAll(estados);
            }
        }).start();

    }


    private void carregaCidades(Integer idEstado) {
        final JSONObject jsonTT = new JSONObject();
        final String urlCidade = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + idEstado + "/municipios";

        new Thread(new Runnable() {
            @Override
            public void run() {
                //try {
                    String cidadesBD = http.get(urlCidade);
                    HashSet<Cidade> cidades = jsonReader.getCidades(cidadesBD);
                    //lista_cidades.addAll(cidades);
               // } catch (HttpConnections.MinhaException e) {
                //    e.printStackTrace();
                //}
            }
        }).start();
    }


}

