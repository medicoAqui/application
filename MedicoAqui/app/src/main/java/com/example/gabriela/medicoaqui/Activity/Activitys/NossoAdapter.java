package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class NossoAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Consulta> consultas;
    static Medico medico = new Medico();
    static JSONReader jsonReader = new JSONReader();
    static HttpConnections http = new HttpConnections();


    public NossoAdapter(ArrayList<Consulta> consultas, Context context) {
        this.consultas = consultas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_minha_consulta, parent, false);
        NossoViewHolder holder = new NossoViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        NossoViewHolder holder = (NossoViewHolder) viewHolder;
        Consulta consulta = consultas.get(position);

        /*retornaMedicoByID(consulta.getMedico());
        holder.medico.setText(medico.getNome());
        holder.crm.setText(medico.getCrm());*/
        holder.medico.setText(consulta.getMedico());
        holder.dataHora.setText(consulta.getDataConsulta() + " " + consulta.getHora());

    }

    public static void retornaMedicoByID(String id_medico) {

        final JSONObject jsonTT = new JSONObject();
        final String url = "https://medicoishere.herokuapp.com/medico/" + id_medico;

        new Thread(new Runnable() {
            @Override
            public void run() {
                String medicoBD = http.get(url);
                medico = jsonReader.getMedicoByID(medicoBD);

            }
        }).start();

    }

    @Override
    public int getItemCount() {
        return consultas.size();
    }
}
