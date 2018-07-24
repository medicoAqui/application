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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class AgendaPaciente  extends AppCompatActivity {


    private static final String TAG = "AgendaPaciente";

    JSONObject jsonTT = new JSONObject();
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();
    static ArrayList<Consulta> lista_consultas_entity = new ArrayList();
    static ArrayList<String> lista_consultas = new ArrayList<String>(){{add("Selecione");}};
    public static String consulta;
    public static Consulta consultaInfo;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_paciente);

        final LinearLayout linearLayout_consultas = (LinearLayout) findViewById(R.id.linearLayout_consultas);
        final Button button_desmarcar = (Button) findViewById(R.id.button_desmarcar);
        final Spinner spinner_consulta = findViewById(R.id.spinner_consultas);
        final TextView hora = (TextView) findViewById(R.id.horaConsulta);
        final TextView dataConsulta = (TextView) findViewById(R.id.dataConsulta);
        final TextView status = (TextView) findViewById(R.id.statusConsulta);
        final TextView medico = (TextView) findViewById(R.id.medicoConsulta);
        final TextView consultorio = (TextView) findViewById(R.id.consultorioConsulta);

        ArrayAdapter<String> dataAdapterConsultas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_consultas);
        dataAdapterConsultas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_consulta.setAdapter(dataAdapterConsultas);

        carregaConsultas();

        spinner_consulta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {

                consulta = spinner_consulta.getSelectedItem().toString();
                if (consulta.equals("Selecione")) {
                    button_desmarcar.setEnabled(false);
                    linearLayout_consultas.setEnabled(false);
                    hora.setEnabled(false);
                    dataConsulta.setEnabled(false);
                    status.setEnabled(false);
                    medico.setEnabled(false);
                    consultorio.setEnabled(false);
                } else {
                    button_desmarcar.setEnabled(true);
                    linearLayout_consultas.setEnabled(true);
                    hora.setEnabled(true);
                    dataConsulta.setEnabled(true);
                    status.setEnabled(true);
                    medico.setEnabled(true);
                    consultorio.setEnabled(true);

                    consultaInfo = getConsultaEntity();

                    hora.setText(consultaInfo.getHora());
                    dataConsulta.setText(consultaInfo.getDataConsulta());
                    status.setText(consultaInfo.getStatus());
                    medico.setText(consultaInfo.getMedico());
                    consultorio.setText(consultaInfo.getConsultorio());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_desmarcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(AgendaPaciente.this, MenuPrincipal.class);
                startActivity(it);

            }
        });

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(AgendaPaciente.this, Especialidade.class);
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

    private void carregaConsultas() {

        Log.d(TAG, "carregaConsultas() called");

        final JSONObject jsonTT = new JSONObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = http.get("https://medicoishere.herokuapp.com/consulta/consultas");
                HashSet<Consulta> consultasEntity = jsonReader.getConsultasEntity(consultasBD);
                HashSet<String> consultasStr = jsonReader.getConsultas(consultasBD);
                lista_consultas_entity.addAll(consultasEntity);
                lista_consultas.addAll(consultasStr);
            }
        }).start();

    }

    public static Consulta getConsultaEntity() {

        Consulta consultaEntity = new Consulta(null, null, null, null, null, null, null,null);

        for (int i = 0; i < lista_consultas_entity.size(); i++) {
            String consultaConfere = lista_consultas_entity.get(i).getMedico() + " - " + lista_consultas_entity.get(i).getDataConsulta() + " - " + lista_consultas_entity.get(i).getHora();
            if (consultaConfere.equals(consulta) ) {
                consultaEntity = lista_consultas_entity.get(i);
            }
        }

        return consultaEntity;
    }

}

