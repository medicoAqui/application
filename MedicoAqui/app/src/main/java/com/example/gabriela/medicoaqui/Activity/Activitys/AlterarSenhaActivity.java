package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabriela.medicoaqui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.gabriela.medicoaqui.Activity.Activitys.TelaLogin.NOME_PREFERENCE;

public class AlterarSenhaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etPassword;
    private EditText etNewPasswordAgain;
    private EditText etNewPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

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
        etPassword = findViewById(R.id.etPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etNewPasswordAgain =  findViewById(R.id.etNewPasswordAgain);

    }


    public void change( View view ){
        final SharedPreferences.Editor editor = getSharedPreferences(NOME_PREFERENCE, MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("INFORMACOES_LOGIN_AUTOMATICO", MODE_PRIVATE);
        if (null != etPassword && null != etNewPasswordAgain && null != etNewPassword
                && null != etPassword.getText() && null != etNewPasswordAgain.getText() && null != etNewPassword.getText()
                && null != etNewPasswordAgain.getText().toString() && null != etPassword.getText().toString()
                && null != etNewPassword.getText().toString()) {
            if("".equals(etPassword.getText().toString()) || "".equals(etNewPasswordAgain.getText().toString())
                    || "".equals(etNewPassword.getText().toString())) {
                Toast.makeText(
                        AlterarSenhaActivity.this,
                        "Os campos não podem ser vazios",
                        Toast.LENGTH_SHORT
                ).show();
            }else if(!etNewPassword.getText().toString().equals(etNewPasswordAgain.getText().toString())){
                Toast.makeText(
                        AlterarSenhaActivity.this,
                        "Os campos Digite senha e Digite a senha novamente devem ser iguais.",
                        Toast.LENGTH_SHORT
                ).show();
            }else if(!etPassword.getText().toString().equals(prefs.getString("password", null))){
                Toast.makeText(
                        AlterarSenhaActivity.this,
                        "Sua senha atual esta incorreta",
                        Toast.LENGTH_SHORT
                ).show();
            }else{
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                final String newPassword = etNewPassword.getText().toString();
                //precisa reautenticar caso o usuario tenha perdido a sessao
                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), etPassword.getText().toString());
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("ChangePassword", "Usuario reautenticado");
                            }
                        });
                //depois de reautenticar, altera a senha
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("ChangePassword", "Troca de senha efetuada com sucesso");
                                    Toast.makeText(
                                            AlterarSenhaActivity.this,
                                            "Troca de senha efetuada com sucesso",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    editor.remove("email");
                                    editor.remove("password");
                                    editor.remove("perfil");
                                    editor.putString("email", user.getEmail());
                                    editor.putString("password", newPassword);
                                    editor.putString("perfil", "paciente");
                                    editor.commit();
                                }else{
                                    Toast.makeText(
                                            AlterarSenhaActivity.this,
                                            "Senha atual incorreta",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                        });
            }
        }else{
            Toast.makeText(
                    AlterarSenhaActivity.this,
                    "Erro ao trocar a senha",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
