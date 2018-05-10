package com.example.gabriela.medicoaqui;

import android.support.v7.app.AlertDialog;
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

public class TelaCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] bankSexo = new String[] {"Masculino", "Feminino"};
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);


        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bankSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = findViewById(R.id.Input_Sexo);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        Toast.makeText(this, bankSexo[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
}
