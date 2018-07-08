package com.example.gabriela.medicoaqui.Activity.JsonOperators;

/**
 * Created by henri on 14/06/2018.
 */

import android.util.Log;

import com.example.gabriela.medicoaqui.Activity.Entities.Cidade;
import com.example.gabriela.medicoaqui.Activity.Entities.Cliente;
import com.example.gabriela.medicoaqui.Activity.Entities.Consulta;
import com.example.gabriela.medicoaqui.Activity.Entities.Estado;
import com.example.gabriela.medicoaqui.Activity.Entities.Medico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

//Classes necessárias

    import java.io.FileWriter;
    import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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


    //Retorna uma lista de Clientes com as informações retornadas do JSON
    public List<Cliente> getClientes(String jsonString) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        try {
            JSONArray clientesJson = new JSONArray(jsonString);
            JSONObject jsonObjectCliente;

            for (int i = 0; i < clientesJson.length(); i++) {
                jsonObjectCliente = new JSONObject(clientesJson.getString(i));
                Log.i("CLIENTE ENCONTRADO: ", "nome=" + jsonObjectCliente.getString("name"));

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


    //Retorna uma lista de Medicos com as informações retornadas do JSON
    public List<Medico> getMedicos(String jsonString) {
        List<Medico> medicos = new ArrayList<Medico>();
        try {
            JSONArray medicosJson = new JSONArray(jsonString);
            JSONObject jsonObjectMedico;

            for (int i = 0; i < medicosJson.length(); i++) {
                jsonObjectMedico = new JSONObject(medicosJson.getString(i));
                Log.w("MEDICO ENCONTRADO: ","nome=" + jsonObjectMedico.getString("name"));

                Medico objetoMedico = new Medico();
                objetoMedico.setNome(jsonObjectMedico.getString("name"));
                objetoMedico.setCpf(jsonObjectMedico.getString("cpf"));
                medicos.add(objetoMedico);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medicos;
    }

    //Retorna uma lista de Medicos com as informações retornadas do JSON
    public Medico getMedicoByCPF(String jsonString, String cpf) {
        Medico medico = new Medico();
        try {
            JSONArray medicosJson = new JSONArray(jsonString);
            JSONObject jsonObjectMedico;

            for (int i = 0; i < medicosJson.length(); i++) {
                jsonObjectMedico = new JSONObject(medicosJson.getString(i));
                Log.i("MEDICO ENCONTRADO: ","nome=" + jsonObjectMedico.getString("name"));
                if (cpf.equals(jsonObjectMedico.getString("cpf"))){

                    medico.setNome(jsonObjectMedico.getString("name"));
                    medico.setCpf(jsonObjectMedico.getString("cpf"));
                    medico.setEspecialidade((jsonObjectMedico.getString("especialidade")));
                    break;
                }
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medico;
    }

    //Retorna uma lista de Medicos com as informações retornadas do JSON
    public Medico getMedicoByCRM(String jsonString) {
        Medico medico = new Medico();
        try {
            JSONObject jsonObjectMedico = new JSONObject(jsonString);

            Log.i("MEDICO ENCONTRADO: ","nome=" + jsonObjectMedico.getString("name"));

            medico.setNome(jsonObjectMedico.getString("name"));
            medico.setCpf(jsonObjectMedico.getString("cpf"));
            medico.setEspecialidade((jsonObjectMedico.getString("especialidade")));


        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medico;
    }

    public HashSet<Medico> getMedicosByEspecialidadeEntity(String jsonString) {
        HashSet<Medico> medicos = new HashSet<Medico>();

        try {
            JSONArray nomesJson = new JSONArray(jsonString);
            JSONObject jsonObjectNome;

            for (int i = 0; i < nomesJson.length(); i++) {
                jsonObjectNome = new JSONObject(nomesJson.getString(i));
                Log.i("Nome: ","nome=" + jsonObjectNome.getString("name"));
                String nome = jsonObjectNome.getString("name");
                String crm = jsonObjectNome.getString("crm");
                Medico medico = new Medico(nome, null, null, null, null, null, null, crm);
                medicos.add(medico);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medicos;
    }

    public HashSet<String> getMedicosByEspecialidade(String jsonString) {
        HashSet<String> nomes = new HashSet<String>();

        try {
            JSONArray nomesJson = new JSONArray(jsonString);
            JSONObject jsonObjectNome;

            for (int i = 0; i < nomesJson.length(); i++) {
                jsonObjectNome = new JSONObject(nomesJson.getString(i));
                Log.i("Nome: ","nome=" + jsonObjectNome.getString("name"));
                String nome = jsonObjectNome.getString("name");
                nomes.add(nome.substring(0,1).toUpperCase().concat(nome.substring(1)));
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return nomes;
    }

    //Retorna uma lista de Especialidades dos medicos ja cadastrados com as informações retornadas do JSON
    public HashSet<String> getEspecialidadesMedicasByMedicos(String jsonString) {

        HashSet<String> especialidades = new HashSet<String>();

        try {
        JSONArray medicosJson = new JSONArray(jsonString);
        JSONObject jsonObjectMedico;

        for (int i = 0; i < medicosJson.length(); i++) {
            jsonObjectMedico = new JSONObject(medicosJson.getString(i));
            Log.i("MEDICO ENCONTRADO: ","nome=" + jsonObjectMedico.getString("name"));
            String especialidade = jsonObjectMedico.getString("idEspecializacao");
            especialidades.add(especialidade.substring(0,1).toUpperCase().concat(especialidade.substring(1)));
        }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return especialidades;
    }

    public HashSet<String> getEspecialidadesMedicas(String jsonString) {
        HashSet<String> especialidades = new HashSet<String>();

            try {
                JSONArray especialidadesJson = new JSONArray(jsonString);
                JSONObject jsonObjectEspecialidade;

                for (int i = 0; i < especialidadesJson.length(); i++) {
                    jsonObjectEspecialidade = new JSONObject(especialidadesJson.getString(i));
                    Log.i("Especialidade: ","especialidade=" + jsonObjectEspecialidade.getString("nomeEspecialidade"));
                    String especialidade = jsonObjectEspecialidade.getString("nomeEspecialidade");
                    especialidades.add(especialidade.substring(0,1).toUpperCase().concat(especialidade.substring(1)));
                }

            } catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
        return especialidades;
    }

    public Cliente getClienteByEmail(String jsonString) { //}, String email) {
        Cliente cliente = new Cliente();
        try {
            JSONObject jsonObjectCliente = new JSONObject(jsonString);

            Log.i("CLIENTE ENCONTRADO: ","nome=" + jsonObjectCliente.getString("name"));

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            cliente.setNome(jsonObjectCliente.getString("name"));
            cliente.setCpf(jsonObjectCliente.getString("cpf"));
            cliente.setGenero(jsonObjectCliente.getString("sexo"));
            cliente.setEmail(jsonObjectCliente.getString("email"));
            cliente.setTelefone(jsonObjectCliente.getString("telefone"));
            cliente.setId(jsonObjectCliente.getString("_id"));
            //cliente.setData_nascimento(formato.parse(jsonObjectCliente.getString("")));
            //tipo sanguíneo
            //alergias

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return cliente;
    }

    public HashSet<Consulta> getConsultasByDateAndCrm(String jsonString) {

        // Ao inves de um  HashSet<String> de string tem que criar uma lista de <Consulta>
        HashSet<Consulta> consultas = new HashSet<Consulta>();

        try {
            JSONArray consultasJson = new JSONArray(jsonString);
            JSONObject jsonObjectConsulta;

            for (int i = 0; i < consultasJson.length(); i++) {
                jsonObjectConsulta = new JSONObject(consultasJson.getString(i));
                Log.i("Consulta: ","parametro=" + jsonObjectConsulta.getString("hora"));

                String observacao = jsonObjectConsulta.getString("observacao");
                String hora = jsonObjectConsulta.getString("hora");
                String status = jsonObjectConsulta.getString("status");
                String cliente = jsonObjectConsulta.getString("cliente");
                String medico = jsonObjectConsulta.getString("medico");
                String consultorio = jsonObjectConsulta.getString("consultorio");
                String idConsulta = jsonObjectConsulta.getString("idConsulta");


                // Monta AQUI um objeto consulta e adiciona na lista que deve ser retornada

                Consulta consulta = new Consulta(observacao, hora, status, cliente, medico, consultorio, idConsulta);
                consultas.add(consulta);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return consultas;
    }


    public HashSet<String> getEstados(String jsonString) {
        HashSet<String> nomes = new HashSet<String>();

        try {
            JSONArray nomesJson = new JSONArray(jsonString);
            JSONObject jsonObjectNome;

            for (int i = 0; i < nomesJson.length(); i++) {
                jsonObjectNome = new JSONObject(nomesJson.getString(i));
                Log.i("Nome: ","nome=" + jsonObjectNome.getString("nome"));
                String nome = jsonObjectNome.getString("nome");
                nomes.add(nome.substring(0,1).toUpperCase().concat(nome.substring(1)));
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return nomes;
    }

    public HashSet<Estado> getEstadosEntity(String jsonString) {
        HashSet<Estado> estados = new HashSet<Estado>();

        try {
            JSONArray estadosJson = new JSONArray(jsonString);
            JSONObject jsonObjectEstado;

            for (int i = 0; i < estadosJson.length(); i++) {
                jsonObjectEstado = new JSONObject(estadosJson.getString(i));
                Log.i("Nome: ","nome=" + jsonObjectEstado.getString("name"));
                Integer id = jsonObjectEstado.getInt("id");
                String nome = jsonObjectEstado.getString("nome");
                String sigla = jsonObjectEstado.getString("sigla");
                Estado estado = new Estado(id, nome, sigla);
                estados.add(estado);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return estados;
    }

    public HashSet<Cidade> getCidades(String jsonString) {
        HashSet<Cidade> cidades = new HashSet<Cidade>();

        try {
            JSONArray cidadesJson = new JSONArray(jsonString);
            JSONObject jsonObjectCidades;

            for (int i = 0; i < cidadesJson.length(); i++) {
                jsonObjectCidades = new JSONObject(cidadesJson.getString(i));
                Log.i("Nome: ","nome=" + jsonObjectCidades.getString("name"));
                Integer id = jsonObjectCidades.getInt("id");
                String nome = jsonObjectCidades.getString("nome");
                Cidade cidade = new Cidade(id, nome);
                cidades.add(cidade);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return cidades;
    }

}