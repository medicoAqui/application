package com.example.gabriela.medicoaqui.Activity.Entities;

import java.util.Date;

/**
 * Created by henri on 14/06/2018.
 */

public class Medico extends Pessoa {

    public String email;
    public String telefone;
    public String especialidade;
    public String crm;

    public Medico(String nome, String cpf, Date data, String genero, String email, String telefone, String especialidade, String crm) {
        super(nome, cpf, data, genero);
        this.email = email;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.crm = crm;
    }
    public Medico(){
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
}
