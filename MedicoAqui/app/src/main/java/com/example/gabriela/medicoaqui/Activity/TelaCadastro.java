package com.example.gabriela.medicoaqui.Activity;

import com.example.gabriela.medicoaqui.R;

import android.support.v7.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.*;


public class TelaCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private WebClient jsonT = new WebClient();
    private JSONObject jsonTT = new JSONObject();
    // Declaring names of variables
    EditText input_nome;
    EditText input_sobrenome;
    EditText input_email;
    EditText input_cpf;
    EditText input_password;
    EditText input_telefone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);


        final Spinner spinSexo = findViewById(R.id.spinner);                                  // Getting the instance of Spinner
        Button botaoCadastrar = /*(Button)*/ findViewById(R.id.button_tela_cadastro);         // Declaring Button Cadastro
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

                nome.isEmpty();

                if(cadastroIsValid(nome, email, cpf, password, telefone, sexo)) {
                    if(email.contains("@")){
                        if(telefone.length() == 11 || telefone.length() == 10){

                            Intent it = new Intent(TelaCadastro.this, TelaPrincipal.class);
                            startActivity(it);

                        } else {
                            showErrorMessage("O número de telefone é inválido.");
                        }
                    } else {
                        showErrorMessage("O e-mail é inválido.");
                    }
                } else {
                    showErrorMessage("Não é permitido campos em branco.");
                }



                /*try {
                    jsonTT.put("nome", nome);
                    jsonTT.put("email", email);
                    jsonTT.put("cpf", cpf);
                    jsonTT.put("password", password);
                    jsonTT.put("telefone", telefone);
                    jsonTT.put("sexo", sexo);

                    jsonT.post(jsonTT.toString());
                    Log.d("Json:", jsonTT.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } */

            }

        });

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSexo.setAdapter(adapter);
        spinSexo.setOnItemSelectedListener(this);
    }

    private void showErrorMessage(String frase) {
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
