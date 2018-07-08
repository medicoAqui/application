package com.example.gabriela.medicoaqui.Activity.Entities;

public class Cidade_UF {

    public String nomeCidade;
    public String nomeEstado;
    public String siglaEstado;

    public Cidade_UF(String nomeCidade, String nomeEstado, String siglaEstado) {
        this.nomeCidade = nomeCidade;
        this.nomeEstado = nomeEstado;
        this.siglaEstado = siglaEstado;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }
}
