package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.gabriela.medicoaqui.Activity.Entities.Cidade_UF;
import com.example.gabriela.medicoaqui.Activity.Entities.Estado;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashSet;

import static android.widget.AdapterView.*;

public class Localizacao  extends AppCompatActivity {

    private static final String TAG = "Localizacao";

    JSONObject jsonTT = new JSONObject();
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();
    ArrayList<String> lista_estados = new ArrayList<String>(){{add("Selecione");}};
    ArrayList<Estado> lista_estados_entity = new ArrayList();
    ArrayList<String> lista_cidades = new ArrayList<String>(){{add("Selecione");}};
    public Cidade_UF cidade_uf = new Cidade_UF(null, null, null);
    public String cidade;
    public String estado;
    public String sigla;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao);

        final Spinner spinner_estado = findViewById(R.id.spinner_estado);
        final Spinner spinner_cidade = findViewById(R.id.spinner_cidade);
        final Button button_localizacao = (Button) findViewById(R.id.button_localizacao);

        ArrayAdapter<String> dataAdapterEstado = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_estados);
        dataAdapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_estado.setAdapter(dataAdapterEstado);

        ArrayAdapter<String> dataAdapterCidade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_cidades);
        dataAdapterCidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cidade.setAdapter(dataAdapterCidade);

        carregaEstados();

        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {

                estado = spinner_estado.getSelectedItem().toString();
                if (estado.equals("Selecione")) {
                    spinner_cidade.setEnabled(false);
                } else {
                    spinner_cidade.setEnabled(true);
                    spinner_cidade.setSelection(0);
                    carregaCidades(buscaIDEstado(estado));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {

                cidade = spinner_cidade.getSelectedItem().toString();
                if (cidade.equals("Selecione")) {
                    button_localizacao.setEnabled(false);
                } else {
                    button_localizacao.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Localizacao.this, MenuPrincipal.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Localizacao.this, MenuPrincipal.class);
                startActivity(it);

            }
        });

    }

    private void carregaEstados() {

        Log.d(TAG, "carregaEstados() called");

        final JSONObject jsonTT = new JSONObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String estadosBD = http.get("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
                HashSet<String> estados = jsonReader.getEstados(estadosBD);
                HashSet<Estado> estadosEntity = jsonReader.getEstadosEntity(estadosBD);
                lista_estados.addAll(estados);
                Collections.sort(lista_estados);
                lista_estados_entity.addAll(estadosEntity);
                lista_estados.remove("Selecione");
                lista_estados.add(0,"Selecione");
            }
        }).start();

    }


    private void carregaCidades(Integer idEstado) {
        Log.d(TAG, "carregaCidades() called with: idEstado = [" + idEstado + "]");

        final JSONObject jsonTT = new JSONObject();
        final String urlCidade = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + idEstado + "/municipios";

        new Thread(new Runnable() {
            @Override
            public void run() {
                //try {
                    lista_cidades.clear();
                    String cidadesBD = http.get(urlCidade);
                    HashSet<String> cidades = jsonReader.getCidades(cidadesBD);
                    lista_cidades.addAll(cidades);
                    Collections.sort(lista_cidades);
                    lista_cidades.remove("Selecione");
                    lista_cidades.add(0,"Selecione");
               // } catch (HttpConnections.MinhaException e) {
                //    e.printStackTrace();
                //}
            }
        }).start();
    }

    private Integer buscaIDEstado(String estado) {

        Log.d(TAG, "buscaIDEstado() called with: estado = [" + estado + "]");
        Integer id = null;

        for (int i = 0; i < lista_estados_entity.size(); i++) {

            if (lista_estados_entity.get(i).getNome().equals(estado)) {
                id = lista_estados_entity.get(i).getId();
            }
        }

        Log.d(TAG, "buscaIDEstado() returned: " + id);
        return id;
    }

    private String buscaSiglaEstado(String estado) {

        String sigla = null;

        for (int i = 0; i < lista_estados_entity.size(); i++) {

            if (lista_estados_entity.get(i).getNome().equals(estado)) {
                sigla = lista_estados_entity.get(i).getSigla();
            }
        }

        Log.d(TAG, "buscaIDEstado() called with: estado = [" + estado + "]. Returned: " + sigla);
        return sigla;
    }

}


