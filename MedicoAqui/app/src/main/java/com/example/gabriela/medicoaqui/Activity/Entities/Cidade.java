package com.example.gabriela.medicoaqui.Activity.Entities;

public class Cidade {

    public Integer id;
    public String nome;

    public Cidade(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
