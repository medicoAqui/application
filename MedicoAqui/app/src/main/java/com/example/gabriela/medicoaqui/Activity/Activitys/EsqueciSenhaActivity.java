package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.gabriela.medicoaqui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AutoCompleteTextView etPassword;
    private AutoCompleteTextView etPasswordAgain;
    private AutoCompleteTextView etNewPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        etPassword = (AutoCompleteTextView) findViewById(R.id.etPassword);
        etPasswordAgain = (AutoCompleteTextView) findViewById(R.id.etPasswordAgain);
        etNewPassword = (AutoCompleteTextView) findViewById(R.id.etNewPassword);
    }


    public void reset( View view ){

    }
}
