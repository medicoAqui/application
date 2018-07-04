package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;



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


public class MarcarConsultaActivity extends AppCompatActivity {

    ArrayList<String> lista_especialidades = new ArrayList<String>(){{add("Selecione");}};
    ArrayList<String> lista_horarios = new ArrayList<String>(){{add("Selecione");}};
    ArrayList<String> lista_medicos = new ArrayList<String>(){{add("Selecione");}};
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);
        final Spinner spinEspecialidade = findViewById(R.id.spinner_especialidade);
        final Spinner spinHorario = findViewById(R.id.spinner_horario);
        final Spinner spinMedicos = findViewById(R.id.spinner_medicos);
        final CalendarView dataConsulta = findViewById(R.id.data_calendar);
        final Date data = new Date();
        Button botaoConsulta = findViewById(R.id.button_consulta);

        dataAdapter(spinEspecialidade, lista_especialidades);
        dataAdapter(spinHorario, lista_horarios);
        dataAdapter(spinMedicos, lista_medicos);

        carregaEspecialidadesEmLista();



        dataConsulta.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {
                data.setDate(dayOfMonth);
                data.setMonth(month);
                data.setYear(year);
            }
        });


        spinEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id)
            {
                    String especializacao = spinEspecialidade.getSelectedItem().toString();
                    if (especializacao.equals("Selecione")){
                        spinMedicos.setEnabled(false);
                    } else {
                        spinMedicos.setEnabled(true);
                        limpaListaDeMedicos();
                        carregaMedicosEmLista(especializacao);

                    }
            }

            private void limpaListaDeMedicos() {
                for (int i = lista_medicos.size() - 1; i > 0; i--) {
                    lista_medicos.remove(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        botaoConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String dia = String.valueOf(data.getDate())+"/"+String.valueOf(data.getMonth())+"/"+String.valueOf(data.getYear());
                String res = spinEspecialidade.getSelectedItem().toString() +"-"+ spinMedicos.getSelectedItem().toString() +"-"+ dia;
                // preparando AlertDialog: instanciando e setando valores o objeto AlertDialog
                // Instância
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MarcarConsultaActivity.this);
                // setando título
                dialogo.setTitle("Resultado");
                // setando mensagem
                dialogo.setMessage(res);
                // setando botão
                dialogo.setNeutralButton("OK", null);
                // chamando o AlertDialog
                dialogo.show();

            }




        });
    }



    private void dataAdapter(Spinner spin, ArrayList<String> lista) {
        ArrayAdapter<String> dataAdapterHorarios = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        dataAdapterHorarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapterHorarios);
    }


    private void carregaEspecialidadesEmLista() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String especialidadesBD = http.get("https://medicoishere.herokuapp.com/especialidade/especialidades");

                HashSet<String> especialidades = jsonReader.getEspecialidadesMedicas(especialidadesBD);

                lista_especialidades.addAll(especialidades);
            }
        }).start();
    }


    private void carregaMedicosEmLista(String especialidade) {
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
                    HashSet<String> medicosByEspecialidade = jsonReader.getMedicosByEspecialidade(medicosBD);
                    lista_medicos.addAll(medicosByEspecialidade);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*private void carregaMedicosEmLista(String especialidade) {
        final JSONObject jsonTT = null;
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
                    HashSet<String> medicosByEspecialidade = jsonReader.getMedicosByEspecialidade(medicosBD);

                    lista_medicos.addAll(medicosByEspecialidade);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    } */
}
