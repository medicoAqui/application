package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class EditarPerfil extends AppCompatActivity {

    private JSONObject jsonTT = new JSONObject();
    private static HttpConnections http = new HttpConnections();

    public String nome, sobrenome, email, cpf, password, telefone, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Bem vindo ao Editar Perfil", Toast.LENGTH_SHORT).show();

        final EditText nomeEdit = findViewById(R.id.nomeEdit);
        final EditText emailEdit = findViewById(R.id.emailEdit);
        final EditText sexoEdit = findViewById(R.id.sexoEdit);
        final EditText telefoneEdit = findViewById(R.id.telefoneEdit);

        nomeEdit.setText(TelaLogin.getClientePerfil().getNome());
        emailEdit.setText(TelaLogin.getClientePerfil().getEmail());
        sexoEdit.setText(TelaLogin.getClientePerfil().getGenero());
        telefoneEdit.setText(TelaLogin.getClientePerfil().getTelefone());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edita_perfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void updateCliente(String json, String id) {

        final String url = "http://medicoishere.herokuapp.com/cliente/" + id;
        new Thread(new Runnable() {
        @Override
        public void run () {
            try {
                String clienteBD = http.sendPost(url, jsonTT.toString());
                //Cliente cliente = jsonReader.getClienteByEmail(clienteBD);
                //setClientePerfil(cliente);
            } catch (HttpConnections.MinhaException e) {
                e.printStackTrace();
            }
        }
    }).

    start();

}

    private void setValuesToJson() throws JSONException {
        jsonTT.put("name", nome);
        jsonTT.put("email", email);
        jsonTT.put("sexo", sexo);
        jsonTT.put("cpf", cpf);
        jsonTT.put("password", password);
        jsonTT.put("telefone", telefone);
    }
}
