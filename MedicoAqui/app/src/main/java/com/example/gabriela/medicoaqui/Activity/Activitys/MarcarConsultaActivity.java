package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.gabriela.medicoaqui.R;

public class MarcarConsultaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);
    }

    public void clickConsultarDisponibilidade(View v) {
        Log.i("arthur", "click consultar disponibilidade.");
    }
}
