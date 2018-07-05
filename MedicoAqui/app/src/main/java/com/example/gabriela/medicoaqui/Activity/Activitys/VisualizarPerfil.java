package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.example.gabriela.medicoaqui.Activity.Activitys.TelaLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class VisualizarPerfil extends AppCompatActivity {

    private JSONObject jsonTT = new JSONObject();
    private static HttpConnections http = new HttpConnections();
    private JSONReader jsonReader = new JSONReader();
    private Cliente clientePerfil;

    public String nome, sobrenome, email, cpf, password, telefone, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Bem vindo ao Visualizar Perfil", Toast.LENGTH_SHORT).show();

        //carregaClienteEmail(TelaLogin.getEmailCliente());
        //Cliente cliente = retornaDadosCliente();
        //texto = (TextView) findViewById(R.id.tx_aqui);
        //texto.setText(db.somarCategoria());
        //emailCliente.setText();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_perfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
/*
    private void carregaClienteEmail(String email) {
        final JSONObject jsonTT = new JSONObject();
        //Cliente cliente;
        try {
            jsonTT.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String clienteBD = http.sendPost("http://medicoishere.herokuapp.com/cliente/clientePorEmail", jsonTT.toString());
                    Cliente cliente = jsonReader.getClienteByEmail(clienteBD);
                    setClientePerfil(cliente);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void setClientePerfil(Cliente cliente) {
        clientePerfil = cliente;
        Log.w("AUTH", "Cliente carregado com sucesso");

        carregarTexto();
        //final TextView nomeCliente = findViewById(R.id.nomeUsuario);
        //final TextView emailCliente = findViewById(R.id.emailUsuario);
        //nomeCliente.setText(clientePerfil.getNome());

    }

    private void carregarTexto() {

        Intent i = getIntent(); // intent da activity anterior
        Bundle extras = getIntent().getExtras();
        String novoTexto = (String) extras.get("algumaString"); // pega o valor digitado na activity anterior
        TextView nomeCliente = (TextView) findViewById(R.id.nomeUsuario);
        nomeCliente.setText(clientePerfil.getNome());

    }*/

}



