package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Especialidade  extends AppCompatActivity {

    ArrayList<String> lista_especialidades = new ArrayList<String>() {{
        add("Selecione");
    }};

    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();

    public static String especialidade;

    private static final String TAG = "Especialidade";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidade);
        final Spinner spinner_especialidade = findViewById(R.id.spinner_especialidade);

        final Button button_especialidade = findViewById(R.id.button_especialidade);

        ArrayAdapter<String> dataAdapterespecialidades = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_especialidades);
        dataAdapterespecialidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_especialidade.setAdapter(dataAdapterespecialidades);

        carregaEspecialidadesEmLista();

        spinner_especialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {

                especialidade = spinner_especialidade.getSelectedItem().toString();
                if (especialidade.equals("Selecione")) {
                    button_especialidade.setEnabled(false);
                } else {
                    button_especialidade.setEnabled(true);
                    //carregaCidades(buscaIDEstado(estado));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_especialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Especialidade.this, Medicos.class);
                startActivity(it);

            }
        });

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Especialidade.this, Localizacao.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Especialidade.this, MenuPrincipal.class);
                startActivity(it);

            }
        });
    }


    private void carregaEspecialidadesEmLista() {

        Log.d(TAG, "carregaEspecialidadesEmLista() called");

        Thread thr_CarregaEspecialidade = new Thread(new Runnable() {
            @Override
            public void run() {
            String especialidadesBD = http.get("https://medicoishere.herokuapp.com/especialidade/especialidades");
            HashSet<String> especialidades = jsonReader.getEspecialidadesMedicas(especialidadesBD);
            lista_especialidades.addAll(especialidades);
            Collections.sort(lista_especialidades);
            lista_especialidades.remove("Selecione");
            lista_especialidades.add(0,"Selecione");
            }
        });
        thr_CarregaEspecialidade.start();

    }


    public static String getEspecialidade() {
        return especialidade;
    }
}