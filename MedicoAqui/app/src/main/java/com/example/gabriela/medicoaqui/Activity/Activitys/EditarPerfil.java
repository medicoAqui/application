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
import android.widget.Button;
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

    private JSONObject EditPerfilJsonTT = new JSONObject();
    private static HttpConnections http = new HttpConnections();

    public String nome, sobrenome, email, cpf, password, telefone, sexo;

    private static final String TAG = "EditarPerfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "Bem vindo ao Editar Perfil", Toast.LENGTH_SHORT).show();

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditarPerfil.this);

        final EditText nomeEdit = findViewById(R.id.nomeEdit);
        final EditText emailEdit = findViewById(R.id.emailEdit);
        final EditText sexoEdit = findViewById(R.id.sexoEdit);
        final EditText telefoneEdit = findViewById(R.id.telefoneEdit);

        nomeEdit.setText(TelaLogin.getClientePerfil().getNome());
        emailEdit.setText(TelaLogin.getClientePerfil().getEmail());
        sexoEdit.setText(TelaLogin.getClientePerfil().getGenero());
        telefoneEdit.setText(TelaLogin.getClientePerfil().getTelefone());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_edita_perfil);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = nomeEdit.getText().toString();
                email = emailEdit.getText().toString();
                sexo = sexoEdit.getText().toString();
                telefone = telefoneEdit.getText().toString();

                if (!(nome.isEmpty()) && !(telefone.isEmpty()) && !(email.isEmpty()) && !(sexo.isEmpty())) {

                    if (email.contains("@") && ((email.contains(".com") || email.contains(".br") || email.contains(".org")))) {

                        if (telefone.length() == 11 || telefone.length() == 10) {

                            try {
                                setValuesEditToJson();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            updateClienteBD(TelaLogin.getClientePerfil().getId());
                            updateClienteObj();

                        } else {
                            builder.setMessage("O número de telefone é inválido.");
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } else {
                        builder.setMessage("O e-mail é inválido.");
                        android.app.AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    builder.setMessage("Não é permitido campos em branco.");
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }

                Intent it = new Intent(EditarPerfil.this, VisualizarPerfil.class);
                startActivity(it);

            };

        });

    }


    public void updateClienteBD(String id) {

        Log.d(TAG, "updateClienteBD() called with: id = [" + id + "]");

        final String url = "http://medicoishere.herokuapp.com/cliente/" + id;
        new Thread(new Runnable() {
            @Override
            public void run () {
                try {
                    //ajustar para PUT - objeto http não encontra o método
                    String clienteBD = http.sendPost(url, EditPerfilJsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).

        start();

    }

    private void updateClienteObj(){

        TelaLogin.clientePerfil.setNome(nome);
        TelaLogin.clientePerfil.setTelefone(telefone);
        TelaLogin.clientePerfil.setEmail(email);
        TelaLogin.clientePerfil.setGenero(sexo);

    }

    private void setValuesEditToJson() throws JSONException {

        EditPerfilJsonTT.put("name", nome);
        EditPerfilJsonTT.put("email", email);
        EditPerfilJsonTT.put("sexo", sexo);
        EditPerfilJsonTT.put("telefone", telefone);

    }
}
