package com.example.gabriela.medicoaqui.Activity.Entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Consulta implements Comparable<Consulta>{


    public String observacao, hora, dataConsulta, status, cliente, medico, consultorio, idConsulta, id;


    public Consulta(String observacao, String hora, String dataConsulta, String status, String cliente, String medico, String consultorio, String idConsulta,String id) {
            this.observacao = observacao;
            this.hora = hora;
            this.dataConsulta = dataConsulta;
            this.status = status;
            this.cliente = cliente;
            this.medico = medico;
            this.consultorio = consultorio;
            this.idConsulta = idConsulta;
            this.id = id;

    }

    @Override
    public String toString(){
        return "Obs:" + observacao + ", Hora: " + hora + " data: " + dataConsulta + " status: " + status + " cliente: " + cliente + " medico: " + medico + " consultorio: " + consultorio;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(String idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //@Override
    public int compareTo(Consulta outraConsulta) {

        if (stringToDate(this.dataConsulta + " " + hora).before(stringToDate(outraConsulta.getDataConsulta() + " " + outraConsulta.getHora()))) {
            return -1;
        }
        if (stringToDate(this.dataConsulta + " " + hora).after(stringToDate(outraConsulta.getDataConsulta() + " " + outraConsulta.getHora()))) {
            return 1;
        }
        return 0;
    }

    public Date stringToDate(String dataStr) {

        dataStr.replaceAll("-", "/");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dataDt = new Date();

        try {
            dataDt = formato.parse(dataStr.replaceAll("-", "/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataDt;
    }

}
