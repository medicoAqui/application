package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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
    String statusCancelado = "C";
    String crm;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<String> lista_hora = new ArrayList<String>() {{
        add("Selecione");
    }};
    ArrayList<Consulta> lista_consultas_disponiveis = new ArrayList<Consulta>();

    Boolean retornoDispMedico;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datahora_consulta);

        final Spinner spinner_hora = findViewById(R.id.spinner_hora);
        final Button button_marcar_consulta = (Button) findViewById(R.id.button_marcar_consulta);
        final CalendarView calendario = (CalendarView) findViewById(R.id.calendario);
        spinner_hora.setEnabled(false);

        final ArrayAdapter<String> dataAdapterHora = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_hora);
        dataAdapterHora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hora.setAdapter(dataAdapterHora);

        //consulta se medico tem algum horario com status C ou D na agenda

        consultaDispHorarioMédico(Medicos.getMedicoSelecionado().getId());
        while (retornoDispMedico == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!retornoDispMedico) {
            dialogo_sem_horario();
        } else {


            calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                    dataStr = formataDoisDigitos(dayOfMonth) + "-" + formataDoisDigitos((month + 1)) + "-" + year;

                    carregaHoraDisponivel(dataStr);

                    spinner_hora.setSelection(dataAdapterHora.getPosition("Selecione"));
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
                    dialogo_marcar_consulta();
                    //Toast.makeText(DataHoraConsulta.this, "Solicitação de consulta enviada", Toast.LENGTH_SHORT).show();
                    //Intent it = new Intent(DataHoraConsulta.this, AgendaPaciente.class);
                    //startActivity(it);

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
        }  // else da verificação de horario disponivel

    }

    public void carregaHoraDisponivel(String dataStr) {

        Log.d(TAG, "carregaHoraDisponivel() called with: data = [" + dataStr + "]");

        final JSONObject jsonTT = new JSONObject();
        final JSONObject jsonTT2 = new JSONObject();

        try {

            jsonTT.put("dataConsulta", dataStr);
            jsonTT.put("status", statusDisponivel);
            jsonTT.put("crm", Medicos.getMedicoSelecionado().getCrm());
            jsonTT2.put("dataConsulta", dataStr);
            jsonTT2.put("status", statusCancelado);
            jsonTT2.put("crm", Medicos.getMedicoSelecionado().getCrm());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Thread thr_carregaHoraDisponivel = new Thread(new Runnable() {
                    @Override
                    public void run() {
            try {

                //Retorna horários com status D - Disponível
                String horasBD = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultasByDateCrmStatus", jsonTT.toString());
                HashSet<String> horas = jsonReader.getHorasDisponiveis(horasBD);
                HashSet<Consulta> consultasDisp = jsonReader.getConsultasDisponíveis(horasBD);
                //Retorna horários com status C - Cancelado
                String horasBD2 = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultasByDateCrmStatus", jsonTT2.toString());
                HashSet<String> horas2 = jsonReader.getHorasDisponiveis(horasBD2);
                HashSet<Consulta> consultasDisp2 = jsonReader.getConsultasDisponíveis(horasBD2);

                lista_hora.clear();
                lista_hora.addAll(horas); // Status D
                lista_hora.addAll(horas2); // Status C

                lista_consultas_disponiveis.addAll(consultasDisp); // Status D
                lista_consultas_disponiveis.addAll(consultasDisp2); // Status C

                Collections.sort(lista_hora);
                lista_hora.remove("Selecione");
                lista_hora.add(0, "Selecione");

            } catch (HttpConnections.MinhaException e) {
                e.printStackTrace();
            }

            }
        });

        thr_carregaHoraDisponivel.start();
        thr_carregaHoraDisponivel.interrupt();

    }


    public void marcarConsulta(String id_consulta) {

        Log.d(TAG, "marcarConsulta() called with: data = [" + dataStr + "], hora = [" + hora + "]");

        final JSONObject jsonTT = new JSONObject();
        // id_consulta é o _id
        final String url = "http://medicoishere.herokuapp.com/consulta/"+ id_consulta;

        try {
            jsonTT.put("status", statusAgendado);
            jsonTT.put("cliente", TelaLogin.getClientePerfil().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Thread thr_marcaConsulta = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    http.put(url, jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        });
        thr_marcaConsulta.start();


    }

    public String getIdConsulta(String hora) {

        String id = null;
        for (int i = 0; i < lista_consultas_disponiveis.size(); i++) {

            if (lista_consultas_disponiveis.get(i).getHora().equals(hora)) {
                id = lista_consultas_disponiveis.get(i).getId();
            }

        }
        return id;
    }

    public String formataDoisDigitos(int numero){
        String valorFormatado = ""+numero;
        if (numero < 10){
            valorFormatado = "0"+numero;
        }
        return valorFormatado;
    }

    private AlertDialog alerta;

    private void dialogo_marcar_consulta() {

        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.activity_dialogo_consulta_marcada, null);

        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alerta.dismiss();
                Intent it = new Intent(DataHoraConsulta.this, AgendaPaciente.class);
                startActivity(it);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Consulta marcada");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();

    }

    private void dialogo_sem_horario() {

        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.activity_dialogo_sem_horario, null);

        view.findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alerta.dismiss();
                Intent it = new Intent(DataHoraConsulta.this, Localizacao.class);
                startActivity(it);

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Disponibilidade");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();

    }


    public void consultaDispHorarioMédico(String idMedico) {
        //Verifica se o médico possui alguma data disponível

        Log.d(TAG, "consultaDispHorarioMédico() called");

        final JSONObject jsonTT = new JSONObject();
        final JSONObject jsonTT2 = new JSONObject();

        try {

            jsonTT.put("id", idMedico);
            jsonTT.put("status", statusDisponivel);
            jsonTT2.put("id", idMedico);
            jsonTT2.put("status", statusCancelado);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Thread thr_ConsultaHorarioDisponivel = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //Retorna horários com status D - Disponível
                    String diasBD = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultaByIdMedicoAndStatus", jsonTT.toString());
                    Boolean disponibilidade = jsonReader.getDisponibilidadeMedico(diasBD);
                    //Retorna horários com status C - Cancelado
                    String diasBD2 = http.sendPost("http://medicoishere.herokuapp.com/consulta/consultaByIdMedicoAndStatus", jsonTT2.toString());
                    Boolean disponibilidade2 = jsonReader.getDisponibilidadeMedico(diasBD2);

                    retornoDispMedico = disponibilidade || disponibilidade2;

                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }

            }
        });
        thr_ConsultaHorarioDisponivel.start();
        thr_ConsultaHorarioDisponivel.interrupt();

    }
}