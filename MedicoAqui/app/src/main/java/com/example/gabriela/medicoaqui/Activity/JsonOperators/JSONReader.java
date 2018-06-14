package com.example.gabriela.medicoaqui.Activity.JsonOperators;

/**
 * Created by henri on 14/06/2018.
 */

import android.util.Log;

import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

    //Classes necessárias

    import java.io.FileWriter;
    import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONReader {

    @SuppressWarnings("unchecked")
    public JSONObject jsonObjectCliente(String nome, String cpf, Date data, String genero, String email, String telefone, String senha) throws JSONException {

        //Cria um Objeto JSON
        JSONObject jsonObject = new JSONObject();

        FileWriter writeFile = null;

        //Armazena dados em um Objeto JSON
        jsonObject.put("name", nome);
        jsonObject.put("email", email);
        jsonObject.put("sexo", genero);
        jsonObject.put("cpf", cpf);
        jsonObject.put("password", senha);
        jsonObject.put("telefone", telefone);

        //Imprimne na Tela o Objeto JSON para vizualização
        System.out.println(jsonObject);

        return jsonObject;
    }


    //Retorna uma lista de pessoas com as informações retornadas do JSON
    public List<Cliente> getClientes(String jsonString) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        try {
            JSONArray clientesJson = new JSONArray(jsonString);
            JSONObject jsonObjectCliente;

            for (int i = 0; i < clientesJson.length(); i++) {
                jsonObjectCliente = new JSONObject(clientesJson.getString(i));
                Log.i("CLIENTE ENCONTRADO: ","nome=" + jsonObjectCliente.getString("name"));

                Cliente objetoCliente = new Cliente();
                objetoCliente.setNome(jsonObjectCliente.getString("name"));
                objetoCliente.setCpf(jsonObjectCliente.getString("cpf"));
                clientes.add(objetoCliente);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return clientes;
    }

}