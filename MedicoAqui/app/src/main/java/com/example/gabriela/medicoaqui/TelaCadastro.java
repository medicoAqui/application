package com.example.gabriela.medicoaqui;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TelaCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button botaoCadastrar = (Button) findViewById(R.id.button_tela_cadastro);

    //Getting the instance of Spinner and applying OnItemSelectedListener on it
    Spinner spinSexo = findViewById(R.id.spinner);

    // Declaring names of variables
    EditText input_nome = findViewById(R.id.Text_Nome);
    EditText input_sobrenome = findViewById(R.id.Text_Sobrenome);
    EditText input_email = findViewById(R.id.Text_Email);
    EditText input_cpf = findViewById(R.id.Text_CPF);
    EditText input_password = findViewById(R.id.Text_Pass);
    EditText input_telefone = findViewById(R.id.Text_Telefone);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String nome = input_nome.getText().toString();
            String sobrenome = input_sobrenome.getText().toString();
            String email = input_email.getText().toString();
            String cpf = input_cpf.getText().toString();
            String password = input_password.getText().toString();
            String telefone = input_telefone.getText().toString();


            }

        });

    //Creating the ArrayAdapter instance having the bank name list
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lista_sexo,android.R.layout.simple_spinner_item);
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



}
