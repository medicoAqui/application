package com.example.gabriela.medicoaqui.Activity.Activities;

import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.content.Intent;
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
import org.json.*;

public class TelaCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private JSONObject jsonTT = new JSONObject();
    private static HttpConnections http = new HttpConnections();

    // Declaring names of variables
    EditText input_nome, input_sobrenome, input_email, input_cpf, input_password, input_telefone;
    String nome, sobrenome, email, cpf, password, telefone, sexo;

    // Henrique Autenticacao - 24/05 - INICIO
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    // Henrique Autenticacao - 24/05 - FIM

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        // Henrique Autenticacao - 24/05 - INICIO
        mAuth = FirebaseAuth.getInstance();
        // Henrique Autenticacao - 24/05 - FIM

        final Spinner spinSexo = findViewById(R.id.spinner);                     // Getting the instance of Spinner
        Button botaoCadastrar = findViewById(R.id.button_tela_cadastro);         // Declaring Button Cadastro
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                findInputs();                                                    // Find inputs by id
                setStrings(spinSexo);                                            // Set inputs in Strings

                if(cadastroIsValid(nome, email, cpf, password, telefone, sexo)) {
                    if(email.contains("@") && (email.contains(".com") || email.contains(".br") || email.contains(".org"))){
                        if(telefone.length() == 11 || telefone.length() == 10){

                            try {
                                setValuesToJson();                               // Fill JSON Object


                                // Henrique Autenticacao - 24/05 - INICIO
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(TelaCadastro.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d(TAG, "createUserWithEmail:success");
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                    Toast.makeText(TelaCadastro.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                // Henrique Autenticacao - 24/05 - FIM

                                new Thread(new Runnable(){
                                    @Override
                                    public void run() {
                                        try {
                                            http.sendPost("http://medicoishere.herokuapp.com/cliente/add", jsonTT.toString());
                                        } catch (HttpConnections.MinhaException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            showMessage("Cadastro efetuado com sucesso!");
                            Intent it = new Intent(TelaCadastro.this, TelaPrincipal.class);
                            startActivity(it);

                        } else { showMessage("O número de telefone é inválido."); }
                    } else { showMessage("O e-mail é inválido."); }
                } else { showMessage("Não é permitido campos em branco."); }
            }
        });

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSexo.setAdapter(adapter);
        spinSexo.setOnItemSelectedListener(this);
    }

    private void setValuesToJson() throws JSONException {
        jsonTT.put("name", nome);
        jsonTT.put("email", email);
        jsonTT.put("sexo", sexo);
        jsonTT.put("cpf", cpf);
        jsonTT.put("password", password);
        jsonTT.put("telefone", telefone);
    }

    private void setStrings(Spinner spinSexo) {
        nome = input_nome.getText().toString();
        sobrenome = input_sobrenome.getText().toString();
        email = input_email.getText().toString();
        cpf = input_cpf.getText().toString();
        password = input_password.getText().toString();
        telefone = input_telefone.getText().toString();
        sexo = (String) spinSexo.getSelectedItem();
    }

    private void findInputs() {
        input_nome = findViewById(R.id.Text_Nome);
        input_sobrenome = findViewById(R.id.Text_Sobrenome);
        input_email = findViewById(R.id.Text_Email);
        input_cpf = findViewById(R.id.Text_CPF);
        input_password = findViewById(R.id.Text_Pass);
        input_telefone = findViewById(R.id.Text_Telefone);
    }

    private void showMessage(String frase) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(TelaCadastro.this);
        dialogo.setTitle("Atenção!");                              // Setando título
        dialogo.setMessage(frase);                                 // Setando mensagem
        dialogo.setNeutralButton("OK", null);         // Setando botão
        dialogo.show();                                            // Chamando o AlertDialog
    }

    public boolean cadastroIsValid(String nome, String email, String cpf, String password, String telefone, String sexo){
        return (isNotEmpty(nome) && isNotEmpty(email) && isNotEmpty(cpf)
                && isNotEmpty(password) && isNotEmpty(telefone) && isNotEmpty(sexo));
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    public boolean isNotEmpty(String texto){
        return !(texto.isEmpty());
    }

}
