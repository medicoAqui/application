package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.gabriela.medicoaqui.Activity.Entities.Consultorio;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.json.JSONException;
import org.json.JSONObject;

public class TelaCadastroMedico extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private JSONObject jsonTT = new JSONObject();
    private static HttpConnections http = new HttpConnections();

    EditText input_nome, input_sobrenome, input_email, input_cpf, input_crm, input_password, input_telefone;
    String nome, sobrenome, email, cpf, password, telefone, sexo, crm, especialidade;
    //Consultorio consultorio = new Consultorio("Santa Angelica", "Cicero Jacinto", "Proximo ao açude"
    //, "Catolé", "Campina Grande", "PB", "58410276", "154");

    // Autenticacao Firebase - INICIO
    private static final String TAG = "EmailPasswordMedico";
    private FirebaseAuth mAuth;
    // Autenticacao Firebase - FIM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_medico);

        mAuth = FirebaseAuth.getInstance();

        final Spinner spinSexo = findViewById(R.id.spinner_cadastro_medico);
        final Spinner spinner_cadastro_especialidade = findViewById(R.id.spinner_cadastro_especialidade);
        Button botaoCadastrar2 = findViewById(R.id.button_tela_cadastro2);
        botaoCadastrar2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                findInputs();                                                    // Find inputs by id
                setStrings(spinSexo, spinner_cadastro_especialidade);                                            // Set inputs in Strings

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TelaCadastroMedico.this);
                builder.setTitle("Inserindo usuário");

                if(cadastroIsValid(nome, email, cpf, password, telefone, sexo, crm)) {

                    if(email.contains("@") && (email.contains(".com") || email.contains(".br") || email.contains(".org"))){
                        if(telefone.length() == 11 || telefone.length() == 10){

                            try {
                                setValuesToJson();                               // Fill JSON Object

                                // Autenticacao Firebase - INICIO
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(TelaCadastroMedico.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d(TAG, "createUserWithEmail:success");
                                                    FirebaseUser user = mAuth.getCurrentUser();

                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                    Toast.makeText(TelaCadastroMedico.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                // Autenticacao Firebase - FIM


                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            http.sendPost("http://medicoishere.herokuapp.com/medico/add", jsonTT.toString());
                                        } catch (HttpConnections.MinhaException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            builder.setMessage("O número de telefone é inválido.");
                            android.app.AlertDialog dialog = builder.create();
                            dialog.show();}
                    } else {
                        builder.setMessage("O e-mail é inválido.");
                        android.app.AlertDialog dialog = builder.create();
                        dialog.show(); }
                } else {
                    builder.setMessage("Não é permitido campos em branco.");
                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();}

                Intent it = new Intent(TelaCadastroMedico.this, TelaPrincipal.class);
                startActivity(it);

            }
        });


        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSexo.setAdapter(adapter);
        spinSexo.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.lista_especialidades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cadastro_especialidade.setAdapter(adapter2);
        spinner_cadastro_especialidade.setOnItemSelectedListener(this);
    }

    private void setValuesToJson() throws JSONException {
        jsonTT.put("name", nome);
        jsonTT.put("email", email);
        jsonTT.put("sexo", sexo);
        jsonTT.put("cpf", cpf);
        jsonTT.put("password", password);
        //jsonTT.put("telefone", telefone);
        jsonTT.put("crm", crm);
        jsonTT.put("especialidade",especialidade);
        jsonTT.put("consultorio", "5b9601fbb95c601ed80eaf24");
    }

    private void setStrings(Spinner spinSexo, Spinner spinner_cadastro_especialidade) {
        nome = input_nome.getText().toString();
        sobrenome = input_sobrenome.getText().toString();
        email = input_email.getText().toString();
        cpf = input_cpf.getText().toString();
        password = input_password.getText().toString();
        telefone = input_telefone.getText().toString();
        sexo = (String) spinSexo.getSelectedItem();
        crm = input_crm.getText().toString();
        especialidade = (String) spinner_cadastro_especialidade.getSelectedItem();
    }

    private void findInputs() {
        input_nome = findViewById(R.id.Text_Nome);
        input_sobrenome = findViewById(R.id.Text_Sobrenome);
        input_email = findViewById(R.id.Text_Email);
        input_cpf = findViewById(R.id.Text_CPF);
        input_password = findViewById(R.id.Text_Pass);
        input_telefone = findViewById(R.id.Text_Telefone);
        input_crm = findViewById(R.id.Text_CPF);
    }

    public boolean cadastroIsValid(String nome, String email, String cpf, String password, String telefone, String sexo, String crm){
        return (isNotEmpty(nome) && isNotEmpty(email) && isNotEmpty(cpf)
                && isNotEmpty(password) && isNotEmpty(telefone) && isNotEmpty(sexo) && isNotEmpty(crm));
    }

    public boolean isNotEmpty(String texto){
        return !(texto.isEmpty());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
