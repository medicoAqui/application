package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;


import org.json.JSONException;
import org.json.JSONObject;

public class EditarPerfilMedico extends AppCompatActivity {

    private JSONObject EditPerfilMedicoJsonTT = new JSONObject();
    private static HttpConnections http = new HttpConnections();

    public String nomeMedico, cpfMedico, telefoneMedico, sexoMedico;

    private static final String TAG = "EditarPerfilMedico";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_medico);

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditarPerfilMedico.this);

        final EditText nomeEditMedico = findViewById(R.id.nomeEditMedico);
        final EditText sexoEditMedico = findViewById(R.id.sexoEditMedico);
        final EditText telefoneEditMedico = findViewById(R.id.telefoneEditMedico);

        nomeEditMedico.setText(TelaLogin.getMedicoLogado().getNome());
        sexoEditMedico.setText(TelaLogin.getMedicoLogado().getGenero());
        telefoneEditMedico.setText(TelaLogin.getMedicoLogado().getTelefone());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_edita_perfil_medico);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nomeMedico = nomeEditMedico.getText().toString();
                sexoMedico = sexoEditMedico.getText().toString();
                telefoneMedico = telefoneEditMedico.getText().toString();

                if (!(nomeMedico.isEmpty()) && !(telefoneMedico.isEmpty()) && !(sexoMedico.isEmpty())) {


                    if (telefoneMedico.length() == 11 || telefoneMedico.length() == 10) {

                        try {
                            setValuesEditToJson();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        updateMedicoBD(TelaLogin.getMedicoLogado().getId());
                        updateMedicoObj();

                    } else {
                        builder.setMessage("O número de telefone é inválido.");
                        android.app.AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                } else {
                    builder.setMessage("Não é permitido campos em branco.");
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();
                }

                Intent it = new Intent(EditarPerfilMedico.this, VisualizarPerfilMedico.class);
                startActivity(it);

            };

        });

        final ImageButton button_voltar = (ImageButton) findViewById(R.id.button_voltar);
        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(EditarPerfilMedico.this, VisualizarPerfilMedico.class);
                startActivity(it);

            }
        });

        final ImageButton button_home = (ImageButton) findViewById(R.id.button_home);
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(EditarPerfilMedico.this, MenuPrincipalMedico.class);
                startActivity(it);

            }
        });
    }


    public void updateMedicoBD(String id) {

        Log.d(TAG, "updateMedicoBD() called with: id = [" + id + "]");

        final String url = "http://medicoishere.herokuapp.com/medico/" + id;
        new Thread(new Runnable() {
            @Override
            public void run () {
                try {
                    http.put(url, EditPerfilMedicoJsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).

                start();

    }

    private void updateMedicoObj(){

        TelaLogin.medicoLogado.setNome(nomeMedico);
        TelaLogin.medicoLogado.setTelefone(telefoneMedico);
        TelaLogin.medicoLogado.setGenero(sexoMedico);

    }

    private void setValuesEditToJson() throws JSONException {

        EditPerfilMedicoJsonTT.put("name", nomeMedico);
        EditPerfilMedicoJsonTT.put("sexo", sexoMedico);
        EditPerfilMedicoJsonTT.put("telefone", telefoneMedico);

    }
}
