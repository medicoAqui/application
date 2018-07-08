package com.example.gabriela.medicoaqui.Activity.Entities;

public class Estado {

    public Integer id;
    public String nome;
    public String sigla;

    public Estado(Integer id, String nome, String sigla){

        this.id = id;
        this.nome = nome;
        this.sigla = sigla;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return this.sigla;
    }

}
