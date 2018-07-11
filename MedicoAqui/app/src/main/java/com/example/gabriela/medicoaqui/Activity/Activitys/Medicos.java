package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import android.widget.TextView;

import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

import static android.widget.AdapterView.*;

public class Medicos extends AppCompatActivity {

    private static final String TAG = "Medicos";

    JSONObject jsonTT = new JSONObject();
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();
    ArrayList<String> lista_medicos = new ArrayList<String>(){{add("Selecione");}};
    ArrayList<Medico> lista_medicos_entity = new ArrayList();
    public static String medico;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicos);

        final Spinner spinner_medicos = findViewById(R.id.spinner_medicos);
        final Button button_medicos = (Button) findViewById(R.id.button_medicos);
        final LinearLayout linearLayout_medicos = (LinearLayout) findViewById(R.id.linearLayout_medicos);
        final TextView nomeMedico = (TextView) findViewById(R.id.nomeMedico);
        final TextView crmMedico = (TextView) findViewById(R.id.crmMedico);
        final TextView especialidadeMedico = (TextView) findViewById(R.id.especialidadeMedico);
        final TextView telefoneMedico = (TextView) findViewById(R.id.telefoneMedico);
        final TextView emailMedico = (TextView) findViewById(R.id.emailMedico);

        ArrayAdapter<String> dataAdapterMedicos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_medicos);
        dataAdapterMedicos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_medicos.setAdapter(dataAdapterMedicos);

        carregaMedicos(Especialidade.getEspecialidade());

        spinner_medicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {

                medico = spinner_medicos.getSelectedItem().toString();
                if (medico.equals("Selecione")) {
                    button_medicos.setEnabled(false);
                    linearLayout_medicos.setEnabled(false);
                    nomeMedico.setEnabled(false);
                    crmMedico.setEnabled(false);
                    especialidadeMedico.setEnabled(false);
                    telefoneMedico.setEnabled(false);
                    emailMedico.setEnabled(false);
                } else {
                    button_medicos.setEnabled(true);
                    linearLayout_medicos.setEnabled(true);
                    nomeMedico.setEnabled(true);
                    crmMedico.setEnabled(true);
                    especialidadeMedico.setEnabled(true);
                    telefoneMedico.setEnabled(true);
                    emailMedico.setEnabled(true);

                    Medico medicoInfo = getMedicoConsulta();

                    nomeMedico.setText(medicoInfo.getNome());
                    crmMedico.setText(medicoInfo.getCrm());
                    especialidadeMedico.setText(medicoInfo.getEspecialidade());
                    telefoneMedico.setText(medicoInfo.getTelefone());
                    emailMedico.setText(medicoInfo.getEmail());

                    //spinner_cidade.setSelection(0);
                    //carregaCidades(buscaIDEstado(estado));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button_medicos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //cidade_uf.setNomeCidade(cidade);
                //cidade_uf.setNomeEstado(estado);


                Intent it = new Intent(Medicos.this, MarcarConsultaActivity.class);
                startActivity(it);

            }
        });

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Medicos.this, Especialidade.class);
                startActivity(it);

            }
        });

    }

    private void carregaMedicos(String especialidade) {

        Log.d(TAG, "carregaMedicos() called with: especialidade = [" + especialidade + "]");

        final JSONObject jsonTT = new JSONObject();

        try {
            jsonTT.put("nomeEspecialidade", especialidade);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String medicosBD = http.sendPost("http://medicoishere.herokuapp.com/medico/medicosByEspecialidade", jsonTT.toString());
                    HashSet<String> medicos = jsonReader.getMedicosByEspecialidade(medicosBD);
                    HashSet<Medico> medicosEntity = jsonReader.getMedicosByEspecialidadeEntity(medicosBD);
                    lista_medicos.addAll(medicos);
                    //Collections.sort(lista_medicos);
                    lista_medicos_entity.addAll(medicosEntity);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private String buscaCRMMedico(String nomeMedico) {

        String crm = null;

        for (int i = 0; i < lista_medicos_entity.size(); i++) {

            if (lista_medicos_entity.get(i).getNome().equals(nomeMedico)) {
                crm = lista_medicos_entity.get(i).getCrm();
            }
        }

        return crm;
    }

    public Medico getMedicoConsulta() {

        Medico medicoConsulta = new Medico(null, null, null, null, null, null,null,null);

        for (int i = 0; i < lista_medicos_entity.size(); i++) {
            if (lista_medicos_entity.get(i).getNome().equals(medico)) {
                medicoConsulta = lista_medicos_entity.get(i);
            }
        }

        return medicoConsulta;
    }


}


