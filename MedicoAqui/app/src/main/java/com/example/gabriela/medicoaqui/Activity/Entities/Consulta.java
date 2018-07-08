package com.example.gabriela.medicoaqui.Activity.Entities;

public class Consulta {


    public String observacao, hora, status, cliente, medico, consultorio, idConsulta, id;


    public Consulta(String observacao, String hora, String status, String cliente, String medico, String consultorio, String idConsulta) {
            this.observacao = observacao;
            this.hora = hora;
            this.status = status;
            this.cliente = cliente;
            this.medico = medico;
            this.consultorio = consultorio;
            this.idConsulta = idConsulta;
            this.id = id;
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



}