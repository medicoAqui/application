package com.example.gabriela.medicoaqui.Activity.Entities;

/**
 * Created by henri on 14/06/2018.
 */

import java.util.Date;
public class Pessoa {
    public String nome;
    public String cpf;
    public Date data_nascimento;
    public String genero;
    public String id;

    public Pessoa(String nome, String cpf, Date data, String genero, String id) {
        this.nome = nome;
        this.cpf = cpf;
        this.data_nascimento = data;
        this.genero = genero;
        this.id= id;
    }

    public Pessoa(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
