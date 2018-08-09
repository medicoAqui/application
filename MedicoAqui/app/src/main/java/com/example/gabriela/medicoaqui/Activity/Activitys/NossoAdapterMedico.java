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
import java.util.List;
import java.util.Objects;


public class NossoAdapterMedico extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Consulta> consultas;
    static Medico medico = new Medico(null, null, null, null, null, null, null, null, null);
    static JSONReader jsonReader = new JSONReader();
    static HttpConnections http = new HttpConnections();
    private static Cliente cliente;

    public NossoAdapterMedico(ArrayList<Consulta> consultas, Context context) {
        this.consultas = consultas;
        this.context = context;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
            clientePorId(consulta.getCliente());
            while (cliente == null) {
                Thread.sleep(1000);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Acompanhando", "consulta =" + consulta.toString());

        if(null != cliente && null != cliente.getNome()){
            holder.medico.setText(cliente.getNome());
        }else{
            holder.medico.setText("-");
        }
        if("D".equals(consulta.getStatus())){
            holder.crm.setText("Disponivel");
        }else if("A".equals(consulta.getStatus())){
            holder.crm.setText("Agendada");
        }else if("C".equals(consulta.getStatus())){
            holder.crm.setText("Cancelada");
        }

        holder.dataHora.setText(consulta.getDataConsulta() + " " + consulta.getHora());

        holder.desmarcar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    desmarcarConsulta(position);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //AgendaPaciente.
            }
        });

        medico = TelaLogin.medicoLogado;

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

        medico = TelaLogin.medicoLogado;

    }


    public void desmarcarConsultaBanco(final Consulta consulta) throws JSONException {

        final JSONObject jsonTT = new JSONObject();

        jsonTT.put("status", "C"); //Status Cancelado - Cliente
        jsonTT.put("cliente", null);

        final String url = "https://medicoishere.herokuapp.com/consulta/desmarcarConsulta/" + consulta.getId();

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

    private void clientePorId(String idCliente) throws JSONException {

        Log.d("Acompanhando", "clientePorId() called "+ idCliente);
//        lista_consultas_entity.clear();
        final JSONObject jsonTT = new JSONObject();
        jsonTT.put("_id", idCliente);


        new Thread(new Runnable() {
            @Override
            public void run() {
                String consultasBD = null;
                try {
                    consultasBD = http.sendPost("https://medicoishere.herokuapp.com/cliente/clientePor_id", jsonTT.toString());
                } catch (HttpConnections.MinhaException e) {
                    e.printStackTrace();
                }

                cliente = jsonReader.getClienteByEmail(consultasBD);

            }
        }).start();
    }

}