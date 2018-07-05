package com.example.gabriela.medicoaqui.Activity.Entities;

import java.util.Date;

/**
 * Created by henri on 14/06/2018.
 */

public class Cliente extends Pessoa {
    public String email;
    public String telefone;
    public String id;

    public Cliente(String nome, String cpf, Date data, String genero, String email, String telefone, String id) {
        super(nome, cpf, data, genero);
        this.email = email;
        this.telefone = telefone;
    }
    public Cliente(){
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
