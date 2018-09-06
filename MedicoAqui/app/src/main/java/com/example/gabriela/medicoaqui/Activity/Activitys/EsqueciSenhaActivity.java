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
    private AutoCompleteTextView email;
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
        email = (AutoCompleteTextView) findViewById(R.id.email);
    }


    public void reset( View view ){
        firebaseAuth
                .sendPasswordResetEmail( email.getText().toString() )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if( task.isSuccessful() ){
                            email.setText("");
                            Toast.makeText(
                                    EsqueciSenhaActivity.this,
                                    "Recuperação de acesso iniciada. Email enviado.",
                                    Toast.LENGTH_SHORT
                            ).show();

                            Intent it = new Intent(EsqueciSenhaActivity.this, TelaLogin.class);
                            startActivity(it);
                        }
                        else{
                            Toast.makeText(
                                    EsqueciSenhaActivity.this,
                                    "Falha ao enviar e-mail. Tente novamente",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }
}
