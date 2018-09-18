package com.example.gabriela.medicoaqui.Activity.Entities;

public class Consultorio {

    String nomeConsultorio, rua, referencia, bairro, cidade, uf, cep, numero;

    public Consultorio(String nomeConsultorio, String rua, String referencia, String bairro, String cidade, String uf, String cep, String numero){
        nomeConsultorio = this.nomeConsultorio;
        rua = this.rua;
        referencia = this.referencia;
        bairro = this.bairro;
        cidade = this.cidade;
        uf = this.uf;
        cep = this.cep;
        numero = this.numero;

    }

    public String getNomeConsultorio() {
        return nomeConsultorio;
    }

    public void setNomeConsultorio(String nomeConsultorio) {
        this.nomeConsultorio = nomeConsultorio;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
