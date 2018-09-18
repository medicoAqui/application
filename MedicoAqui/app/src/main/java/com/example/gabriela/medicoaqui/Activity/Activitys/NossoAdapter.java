package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;
import com.example.gabriela.medicoaqui.Activity.JsonOperators.JSONReader;
import com.example.gabriela.medicoaqui.Activity.Service.HttpConnections;
import com.example.gabriela.medicoaqui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class NossoAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Consulta> consultas;
    static Medico medico = new Medico(null, null, null, null, null, null, null, null, null);
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        NossoViewHolder holder = (NossoViewHolder) viewHolder;
        Consulta consulta = consultas.get(position);


        try {

            retornaMedicoByID(consulta.getMedico());
            while (medico.getCrm() == null) {
                Thread.sleep(500);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.medico.setText(medico.getNome());
        holder.crm.setText(medico.getCrm());

        holder.dataHora.setText(consulta.getDataConsulta() + " " + consulta.getHora());

        holder.desmarcar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    desmarcarConsulta(position);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        holder.remarcar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    desmarcarConsulta(position);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        medico = new Medico(null, null, null, null, null, null, null, null, null);

    }


    @Override
    public int getItemCount() {
        return consultas.size();
    }


    private void desmarcarConsulta(int position) throws InterruptedException {
        try {

            desmarcarConsultaBanco(consultas.get(position));

            consultas.get(position).setStatus("C");
            consultas.get(position).setCliente(null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        while (!(consultas.get(position).getStatus().equals("C"))) {
            Thread.sleep(1000);
        }
        consultas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, consultas.size());
    }


    public static void retornaMedicoByID(String id_medico) throws JSONException {

        final JSONObject jsonTT = new JSONObject();
        //final String url = "https://medicoishere.herokuapp.com/medico/" + id_medico;

        jsonTT.put("_id", id_medico);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String medicoBD = http.sendPost("http://medicoishere.herokuapp.com/medico/medicoBy_id", jsonTT.toString());
                    medico = jsonReader.getMedicoByID(medicoBD);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void retornaConsultorioByID(String id_consultorio) throws JSONException {

        final JSONObject jsonTT = new JSONObject();

        jsonTT.put("_id", id_consultorio);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //http://medicoishere.herokuapp.com/consultorio/enderecoByIdConsultorio
                    String consultorioBD = http.sendPost("http://medicoishere.herokuapp.com/consultorio/enderecoByIdConsultorio", jsonTT.toString());
                    //consultorio = jsonReader.getMedicoByID(consultorioBD);
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }




    public void desmarcarConsultaBanco(final Consulta consulta) throws JSONException {

        final JSONObject jsonTT = new JSONObject();

        jsonTT.put("status", "C"); //Status Cancelado - Cliente
        jsonTT.put("cliente", null);

        final String url = "https://medicoishere.herokuapp.com/consulta/" + consulta.getId();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = null;
                try {
                    http.put(url, jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}