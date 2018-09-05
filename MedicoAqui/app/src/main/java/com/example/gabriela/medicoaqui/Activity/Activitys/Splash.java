package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.R;

public class Splash extends Activity {
    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 3000;

    public static String login;
    public static String senha;
    public static String perfil;
    public static boolean dadosUsuarioEmCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal

                SharedPreferences prefs = getSharedPreferences("INFORMACOES_LOGIN_AUTOMATICO", MODE_PRIVATE);
                login= prefs.getString("email", null);
                senha= prefs.getString("password", null);
                perfil= prefs.getString("perfil", null);
                dadosUsuarioEmCache = false;
                if ((login!= null && !"".equals(login)) && (perfil!= null && !"".equals(perfil)) && (senha!= null && !"".equals(senha))) {
                    dadosUsuarioEmCache = true;
                    Log.d("OPA ENC LOGIN", (login + senha));
                    Intent i = new Intent(Splash.this, TelaLogin.class);
                    startActivity(i);
                } else {
                    dadosUsuarioEmCache = false;
                    Log.d("NUM ACHIMO", "XIIIII");
                    Intent i = new Intent(Splash.this, TelaPrincipal.class);
                    startActivity(i);
                }

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
