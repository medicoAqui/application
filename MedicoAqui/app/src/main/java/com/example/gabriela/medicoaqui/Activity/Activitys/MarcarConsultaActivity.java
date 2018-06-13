package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.*;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;

import com.example.gabriela.medicoaqui.R;

import java.util.ArrayList;


public class MarcarConsultaActivity extends AppCompatActivity {

    ArrayList<String> lista_especialidades = new ArrayList<String>(){};
    String[] tratamento;
    String[] teste;

    String resposta;


    private static HttpConnections http = new HttpConnections();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);
        final Spinner spinEspecialidade = findViewById(R.id.spinner_especialidade);
        Button botaoConsulta = findViewById(R.id.button_consulta);
        resposta = getIntent().getExtras().getString("intent");

        tratamentoDeListaMedicos();

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_sexo, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_especialidades );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEspecialidade.setAdapter(dataAdapter);



        botaoConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                /*String res = lista_especialidades.toString();
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
                dialogo.show();*/

            }




        });
    }

    private void tratamentoDeListaMedicos() {
        tratamento = resposta.split(",");
        for (int i = 0; i < tratamento.length; i++) {
            if(tratamento[i].contains("especializacao")){
                String[] aux = tratamento[i].split(":");
                String[] aux2 = aux[1].split("");
                aux[1] = "";
                for (int j = 2; j < aux2.length-1; j++) {
                    aux[1] += aux2[j];

                }
                if(!(lista_especialidades.contains(aux[1]))){
                    lista_especialidades.add(aux[1]);
                }

            }

        }
    }
}
