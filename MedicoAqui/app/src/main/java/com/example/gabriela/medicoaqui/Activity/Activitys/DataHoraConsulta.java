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
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.example.gabriela.medicoaqui.Activity.Activitys.Medicos;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

public class DataHoraConsulta   extends AppCompatActivity {

    private static final String TAG = "DataHoraConsulta";

    JSONObject jsonTT = new JSONObject();
    JSONReader jsonReader = new JSONReader();
    HttpConnections http = new HttpConnections();
    Date data;
    String dataStr;
    String hora;
    String id_consulta;
    String statusDisponivel = "D";
    String statusAgendado = "A";
    String crm;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<String> lista_hora = new ArrayList<String>() {{
        add("Selecione");
    }};
    ArrayList<Consulta> lista_consultas_disponiveis = new ArrayList<Consulta>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datahora_consulta);

        final Spinner spinner_hora = findViewById(R.id.spinner_hora);
        final Button button_marcar_consulta = (Button) findViewById(R.id.button_marcar_consulta);
        final CalendarView calendario = (CalendarView) findViewById(R.id.calendario);
        spinner_hora.setEnabled(false);

        ArrayAdapter<String> dataAdapterHora = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_hora);
        dataAdapterHora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hora.setAdapter(dataAdapterHora);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                data = new Date(calendario.getDate());
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                dataStr = df.format(data);
                //dataStr = "18/07/2018";

                carregaHoraDisponivel(dataStr);

                if (lista_hora.size() == 0) {
                    spinner_hora.setEnabled(false);
                    //exibir mensagem
                } else {
                    spinner_hora.setEnabled(true);
                }

            }

        });

        spinner_hora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int pos, long id) {

                hora = spinner_hora.getSelectedItem().toString();
                if (hora.equals("Selecione")) {
                    button_marcar_consulta.setEnabled(false);
                } else {
                    button_marcar_consulta.setEnabled(true);
                }
            }

            @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
        });


        button_marcar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                marcarConsulta(getIdConsulta(hora));

                Intent it = new Intent(DataHoraConsulta.this, VisualizarHistorico.class);
                startActivity(it);

            }
        });


        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(DataHoraConsulta.this, Medicos.class);
                startActivity(it);

            }
        });


        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(DataHoraConsulta.this, MenuPrincipal.class);
                startActivity(it);

            }
        });
    }

    public void carregaHoraDisponivel(String dataStr) {

        Log.d(TAG, "carregaHoraDisponivel() called with: data = [" + dataStr + "]");

        final JSONObject jsonTT = new JSONObject();

        try {

            jsonTT.put("date", dataStr);
            jsonTT.put("status", statusDisponivel);
            jsonTT.put("medico", Medicos.getMedicoSelecionado().getCrm());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
                    @Override
                    public void run() {
            try {
                String horasBD = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultasByDateCrmStatus", jsonTT.toString());
                HashSet<String> horas = jsonReader.getHorasDisponiveis(horasBD);
                HashSet<Consulta> consultasDisp = jsonReader.getConsultasDisponíveis(horasBD);
                lista_hora.addAll(horas);
                lista_consultas_disponiveis.addAll(consultasDisp);
                Collections.sort(lista_hora);
                lista_hora.remove("Selecione");
                lista_hora.add(0, "Selecione");
            } catch (HttpConnections.MinhaException e) {
                e.printStackTrace();
            }

            }
        }).start();
    }


    public void marcarConsulta(String id_consulta) {

        Log.d(TAG, "marcarConsulta() called with: data = [" + data + "], hora = [" + hora + "]");

        final JSONObject jsonTT = new JSONObject();
        final String url = "http://medicoishere.herokuapp.com/consulta/"+ id_consulta;

        try {
            jsonTT.put("status", statusAgendado);
            jsonTT.put("cliente", TelaLogin.getClientePerfil().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http.put(url, jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getIdConsulta(String hora) {

        String id = null;
        for (int i = 0; i < lista_consultas_disponiveis.size(); i++) {

            if (lista_consultas_disponiveis.get(i).getHora().equals(hora)) {
                id = lista_consultas_disponiveis.get(i).getIdConsulta();
            }

        }
        return id;
    }

}