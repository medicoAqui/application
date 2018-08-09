package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class DisponibilizaConsulta extends AppCompatActivity {

    private String[] dias = new String[]{"Selecione","01", "02", "03", "04", "05", "06", "07", "08","09","10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    private String[] horariosConsulta = new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00","17:00", "18:00", "19:00", "20:00"};

    private String[] dias_da_semana = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};


    int dia;

    ArrayList<String> anos = new ArrayList<>();

    Medico medicoAtual = TelaLogin.medicoLogado;
    static HttpConnections http = new HttpConnections();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibiliza_consulta);
        final Spinner spinner_dias = findViewById(R.id.spinner_dia);
        final Spinner spinner_mes = findViewById(R.id.spinner_mes);
        final Spinner spinner_ano = findViewById(R.id.spinner_ano);
        final Spinner spinner_horario_inicio = findViewById(R.id.spinner_horaInicial);
        final Date data = new Date();
        Button botaoDisponibiliza = findViewById(R.id.botao_disponibi);


        String dataString = data.toString();
        String aux = (String) dataString.subSequence(dataString.length()-4,dataString.length());
        int aux2 = Integer.parseInt(aux);

        for (int i = 0; i < 3; i++) {
            int aux4 = aux2+i;
            String aux3 = String.valueOf(aux4);
            anos.add(aux3);
        }

        String[] mes = Arrays.copyOfRange(dias,data.getMonth()+1,13);


        dataAdapter(spinner_horario_inicio, horariosConsulta);
        dataAdapter(spinner_ano, anos);
        dataAdapter(spinner_mes, mes);
        spinner_ano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int ano = Integer.parseInt(String.valueOf(spinner_ano.getSelectedItem()));
                if(ano % 4 == 0){
                    dia = 30;
                } else {
                    dia = 29;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String numberSelected = spinner_mes.getSelectedItem().toString();
                int diaInicio;
                if((data.getMonth()+1) == (Integer.parseInt(numberSelected))){
                    diaInicio = data.getMonth()+2;

                } else {
                    diaInicio = 0;
                }
                if (numberSelected.equals("01") || numberSelected.equals("03")
                        || numberSelected.equals("05") || numberSelected.equals("07")
                        || numberSelected.equals("08") || numberSelected.equals("10") || numberSelected.equals("12")){

                    String[] aux = Arrays.copyOfRange(dias, diaInicio, 32);
                    dataAdapter(spinner_dias, aux);

                }

                else if (numberSelected.equals("02")){

                    String[] aux = Arrays.copyOfRange(dias, diaInicio, dia);
                    dataAdapter(spinner_dias, aux);

                }

                else {

                    String[] aux = Arrays.copyOfRange(dias, diaInicio, 31);
                    dataAdapter(spinner_dias, aux);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botaoDisponibiliza.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dia_selecionado = String.valueOf(spinner_dias.getSelectedItem());
                int dia_Selecionado_inteiro = Integer.parseInt(dia_selecionado);
                String mes_selecionado = String.valueOf(spinner_mes.getSelectedItem());
                int mes_selecionado_int = Integer.parseInt(mes_selecionado);
                String ano_selecionado = String.valueOf(spinner_ano.getSelectedItem());
                data.setDate(dia_Selecionado_inteiro);
                data.setMonth(mes_selecionado_int-1);


                String dataStr = data.toString();
                String dia_semana = dataStr.substring(0,3);
                String hora_da_consulta = String.valueOf(spinner_horario_inicio.getSelectedItem());


                if (verificaDiaDeSemana(dia_semana)){
                    String dataDaConsulta = dia_selecionado+"-"+mes_selecionado+"-"+ano_selecionado;
                    final JSONObject jsonTT = new JSONObject();

                    try {
                        jsonTT.put("dataConsulta",dataDaConsulta);
                        jsonTT.put("hora", hora_da_consulta);
                        jsonTT.put("status", "D");
                        jsonTT.put("medico",medicoAtual.id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                http.sendPost("https://medicoishere.herokuapp.com/consulta/add", jsonTT.toString());

                            } catch (HttpConnections.MinhaException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    String res = "Cadastro de consulta realizado com sucesso.";
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(DisponibilizaConsulta.this);
                    dialogo.setTitle("Resultado");
                    dialogo.setMessage(res);
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();

                    Intent it = new Intent(DisponibilizaConsulta.this, MenuPrincipalMedico.class);
                    startActivity(it);


                }
                else {
                    String res = "O dia escolhido não é um dia útil. Favor selecionar um dia entre Segunda e Sexta.";
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(DisponibilizaConsulta.this);
                    dialogo.setTitle("Resultado");
                    dialogo.setMessage(res);
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();
                }




            }




        });
    }






    private void dataAdapter(Spinner spin, String[] lista) {
        ArrayAdapter<String> dataAdapterHorarios = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        dataAdapterHorarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapterHorarios);
    }

    private void dataAdapter(Spinner spin, ArrayList<String> lista) {
        ArrayAdapter<String> dataAdapterHorarios = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        dataAdapterHorarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapterHorarios);
    }

    private boolean verificaDiaDeSemana(String dia_semana){
        boolean result = false;
        for (int i = 0; i < dias_da_semana.length; i++) {
            if (dia_semana.equals(dias_da_semana[i])){
                result = true;
            }
        }
        return result;
    }
}

