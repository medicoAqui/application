package com.example.gabriela.medicoaqui;

import java.io.Serializable;

public class Cadastro implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private String sexo;
    private String cpf;
    private String password;
    private String telefone;

    public Cadastro(){};

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setName(String name){
        this.name = name;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cadastro{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", sexo='" + sexo + '\'' +
                ", cpf='" + cpf + '\'' +
                ", password='" + password + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
