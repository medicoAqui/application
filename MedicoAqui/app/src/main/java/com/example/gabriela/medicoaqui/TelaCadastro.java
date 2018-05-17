package com.example.gabriela.medicoaqui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TelaCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    // Declaring names of variables
    EditText input_nome;
    EditText input_sobrenome;
    EditText input_email;
    EditText input_cpf;
    EditText input_password;
    EditText input_telefone;
    Cadastro cadastro = new Cadastro();
    ProgressDialog progress;
    Button botaoCadastrar;
    Spinner spinSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        // Getting the instance of Spinner0
        // Declaring Button Cadastro

        spinSexo = findViewById(R.id.spinner);
        botaoCadastrar = (Button) findViewById(R.id.button_tela_cadastro);
        listenersButtons();
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                input_nome = findViewById(R.id.Text_Nome);
                input_sobrenome = findViewById(R.id.Text_Sobrenome);
                input_email = findViewById(R.id.Text_Email);
                input_cpf = findViewById(R.id.Text_CPF);
                input_password = findViewById(R.id.Text_Pass);
                input_telefone = findViewById(R.id.Text_Telefone);

                String nome = input_nome.getText().toString();
                String sobrenome = input_sobrenome.getText().toString();
                String email = input_email.getText().toString();
                String cpf = input_cpf.getText().toString();
                String password = input_password.getText().toString();
                String telefone = input_telefone.getText().toString();
                String sexo = (String) spinSexo.getSelectedItem();

            }

        });

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSexo.setAdapter(adapter);
        spinSexo.setOnItemSelectedListener(this);
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

    /**
     * Chama os listeners para os botões
     */
    public void listenersButtons() {
        botaoCadastrar = (Button) findViewById(R.id.button_tela_cadastro);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input_nome = findViewById(R.id.Text_Nome);
                input_sobrenome = findViewById(R.id.Text_Sobrenome);
                input_email = findViewById(R.id.Text_Email);
                input_cpf = findViewById(R.id.Text_CPF);
                input_password = findViewById(R.id.Text_Pass);
                input_telefone = findViewById(R.id.Text_Telefone);
                spinSexo = findViewById(R.id.spinner);

                String nome = input_nome.getText().toString();
                String sobrenome = input_sobrenome.getText().toString();
                String email = input_email.getText().toString();
                String cpf = input_cpf.getText().toString();
                String password = input_password.getText().toString();
                String telefone = input_telefone.getText().toString();
                String sexo = (String) spinSexo.getSelectedItem();


                //chama o retrofit para fazer a requisição no webservice
                retrofitConverter(nome, email, cpf, password, telefone, sexo);

            }
        });
    }


    public void retrofitConverter(String nome, String email, String cpf, String password, String telefone, String sexo) {

        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        Call<Cadastro> call = service.converterUnidade(nome, email, cpf, password, telefone, sexo);

        call.enqueue(new Callback<Cadastro>() {
            @Override
            public void onResponse(Call<Cadastro> call, Response<Cadastro> response) {

                if (response.isSuccessful()) {

                    Cadastro Cadastro = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (Cadastro != null) {

                        if((Cadastro.getName() != null) && (Cadastro.getEmail() != null)
                            && (Cadastro.getCpf() != null) && (Cadastro.getPassword() != null)
                             && (Cadastro.getTelefone() != null) && (Cadastro.getSexo() != null)) {

                            cadastro.setName(Cadastro.getName());
                            cadastro.setEmail(Cadastro.getEmail());
                            cadastro.setCpf(Cadastro.getCpf());
                            cadastro.setPassword(Cadastro.getPassword());
                            cadastro.setTelefone(Cadastro.getTelefone());
                            cadastro.setSexo(Cadastro.getSexo());

                            progress.dismiss();

                        } else{

                            Toast.makeText(getApplicationContext(),"Insira unidade e valores válidos", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(),"Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Cadastro> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

/*    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new TelaLogin.UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    } */


