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
import java.util.Objects;


public class NossoAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Consulta> consultas;
    static Medico medico;// = new Medico();
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

        /*retornaMedicoByID(consulta.getMedico());

        try {
            while (medico.getCrm() == null) {
                Thread.sleep(1000);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        //retornaMedicoByID(consulta.getMedico());
        holder.medico.setText(medico.getNome());
        holder.crm.setText(medico.getCrm());
        */
        holder.medico.setText(consulta.getMedico());
        holder.dataHora.setText(consulta.getDataConsulta() + " " + consulta.getHora());
        //idConsulta = consulta.getIdConsulta();

        holder.desmarcar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    desmarcarConsulta(position);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Intent it = new Intent(DataHoraConsulta.this, Medicos.class);
                //startActivity(it);

            }
        });//view -> desmarcarConsulta(position));

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


    public static void retornaMedicoByID(String id_medico)  throws JSONException {

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


    public void desmarcarConsultaBanco(final Consulta consulta) throws JSONException {

        //put /idconsulta

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

    /*
    private static AlertDialog alerta;

    public  void dialogo_desmarcar(final String idConsulta) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desmarcar consulta");
        builder.setMessage("Você deseja confirmar o cancelamento da consulta agendada?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                try {
                    desmarcarConsulta(idConsulta);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alerta.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }*/
}
