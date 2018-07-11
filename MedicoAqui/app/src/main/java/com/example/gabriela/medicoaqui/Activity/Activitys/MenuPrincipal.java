package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuPrincipal extends AppCompatActivity {

    String resposta = "Init";
    private static HttpConnections http = new HttpConnections();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        final Button button_consulta_menu = (Button) findViewById(R.id.button_consulta_menu);
        button_consulta_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(MenuPrincipal.this, Localizacao.class);
                startActivity(it);

            }
        });


        final Button button_agenda_menu = (Button) findViewById(R.id.button_agenda_menu);
        button_agenda_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(MenuPrincipal.this, VisualizarHistorico.class);
                startActivity(it);

            }
        });

        final Button button_perfil_menu = (Button) findViewById(R.id.button_perfil_menu);
        button_perfil_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(MenuPrincipal.this, VisualizarPerfil.class);
                startActivity(it);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intentFazerLogout = new Intent(getApplicationContext(), TelaLogin.class);
                startActivity(intentFazerLogout);
                finish();
                mAuth.signOut();
                Log.d("Logout", "saindo da aplicação");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

}
