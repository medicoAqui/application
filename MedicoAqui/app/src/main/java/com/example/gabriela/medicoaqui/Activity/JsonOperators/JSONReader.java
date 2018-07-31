package com.example.gabriela.medicoaqui.Activity.JsonOperators;

/**
 * Created by henri on 14/06/2018.
 */

import android.util.JsonReader;
import android.util.Log;

import com.example.gabriela.medicoaqui.Activity.Entities.Cidade_UF;
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
            JSONArray nomesArrayExtJson = new JSONArray(jsonString);
            JSONArray nomesArrayIntJson;
            JSONObject jsonObjectNome;
            if (nomesArrayExtJson.length() > 0) {
                for (int i = 0; i < nomesArrayExtJson.length(); i++) {
                    nomesArrayIntJson = new JSONArray(nomesArrayExtJson.getString(i));
                    for (int j = 0; j < nomesArrayIntJson.length(); j++) {
                        jsonObjectNome = new JSONObject(nomesArrayIntJson.getString(j));
                        //Log.i("Nome: ", "nome=" + jsonObjectNome.getString("name"));
                        String nome = jsonObjectNome.getString("name");
                        String crm = jsonObjectNome.getString("crm");
                        Medico medico = new Medico(nome, null, null, null, null, null, null, null, crm);
                        medicos.add(medico);
                    }
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhum medico por esta especialidade");
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medicos;
    }

    public HashSet<String> getMedicosByEspecialidade(String jsonString) {
        HashSet<String> nomes = new HashSet<String>();

        try {
            JSONArray nomesArrayExtJson = new JSONArray(jsonString);
            JSONArray nomesArrayIntJson;
            JSONObject jsonObjectNome;

            if (nomesArrayExtJson.length()>0) {
                for (int i = 0; i < nomesArrayExtJson.length(); i++) {
                    nomesArrayIntJson = new JSONArray(nomesArrayExtJson.getString(i));
                    for (int j = 0; j < nomesArrayIntJson.length(); j++) {
                        jsonObjectNome = new JSONObject(nomesArrayIntJson.getString(j));
                        //Log.i("Nome: ", "nome=" + jsonObjectNome.getString("name"));
                        String nome = jsonObjectNome.getString("name");
                        nomes.add(nome.substring(0, 1).toUpperCase().concat(nome.substring(1)));
                   }
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhum nome medico por esta especialidade");
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
        if (medicosJson.length() >0) {
            for (int i = 0; i < medicosJson.length(); i++) {
                jsonObjectMedico = new JSONObject(medicosJson.getString(i));
                Log.i("MEDICO ENCONTRADO: ", "nome=" + jsonObjectMedico.getString("name"));
                String especialidade = jsonObjectMedico.getString("idEspecializacao");
                especialidades.add(especialidade.substring(0, 1).toUpperCase().concat(especialidade.substring(1)));
            }
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
                    //Log.i("Especialidade: ","especialidade=" + jsonObjectEspecialidade.getString("nomeEspecialidade"));
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


            if (consultasJson.length() > 0) {
                for (int i = 0; i < consultasJson.length(); i++) {
                    jsonObjectConsulta = new JSONObject(consultasJson.getString(i));
                    Log.i("Consulta: ", "parametro=" + jsonObjectConsulta.getString("hora"));

                    String observacao = null;
                    if (jsonObjectConsulta.has("observacao")) {
                        observacao = jsonObjectConsulta.getString("observacao");
                    }
                    String hora = jsonObjectConsulta.getString("hora");
                    String status = jsonObjectConsulta.getString("status");
                    String cliente = null;
                    if (jsonObjectConsulta.has("cliente")) {
                        cliente = jsonObjectConsulta.getString("cliente");
                    }
                    String medico = jsonObjectConsulta.getString("medico");
                    String idConsulta = jsonObjectConsulta.getString("idConsulta");
                    String dataConsulta = jsonObjectConsulta.getString("dataConsulta");
                    String id = jsonObjectConsulta.getString("_id");


                    // Monta AQUI um objeto consulta e adiciona na lista que deve ser retornada

                    Consulta consulta = new Consulta(observacao, hora, dataConsulta, status, cliente, medico, "", idConsulta, id);
                    consultas.add(consulta);
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhuma consulta por crm e data");
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return consultas;
    }


    public HashSet<String> getEstados(String jsonString) {
        HashSet<String> nomesEstados = new HashSet<String>();

        try {
            JSONArray nomesJson = new JSONArray(jsonString);
            JSONObject jsonObjectNome;

            for (int i = 0; i < nomesJson.length(); i++) {
                jsonObjectNome = new JSONObject(nomesJson.getString(i));
                //Log.i("Nome: ","nome=" + jsonObjectNome.getString("nome"));
                String nome = jsonObjectNome.getString("nome");
                nomesEstados.add(nome); //.substring(0,1).toUpperCase().concat(nome.substring(1)));
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return nomesEstados;
    }

    public HashSet<Estado> getEstadosEntity(String jsonString) {
        HashSet<Estado> estados = new HashSet<Estado>();

        try {
            JSONArray estadosJson = new JSONArray(jsonString);
            JSONObject jsonObjectEstado;

            for (int i = 0; i < estadosJson.length(); i++) {
                jsonObjectEstado = new JSONObject(estadosJson.getString(i));
                //Log.i("Nome: ","nome=" + jsonObjectEstado.getString("nome"));
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

    public HashSet<String> getCidades(String jsonString) {
        HashSet<String> nomesCidades = new HashSet<String>();

        try {
            JSONArray cidadesJson = new JSONArray(jsonString);
            JSONObject jsonObjectCidades;

            for (int i = 0; i < cidadesJson.length(); i++) {
                jsonObjectCidades = new JSONObject(cidadesJson.getString(i));
                //Log.i("Nome: ","nome=" + jsonObjectCidades.getString("nome"));
                //Integer id = jsonObjectCidades.getInt("id");
                String nome = jsonObjectCidades.getString("nome");
                nomesCidades.add(nome.substring(0,1).toUpperCase().concat(nome.substring(1)));
                //Cidade cidade = new Cidade(id, nome);
                //cidades.add(cidade);
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return nomesCidades;
    }

    public HashSet<Consulta> getMinhaAgenda(String jsonString) {
        HashSet<Consulta> minhaAgenda = new HashSet<Consulta>();

        try {
            JSONArray minhaAgendaJson = new JSONArray(jsonString);
            JSONObject jsonObjectMinhaAgenda;
            if (minhaAgendaJson.length() >0 ) {
                for (int i = 0; i < minhaAgendaJson.length(); i++) {
                    jsonObjectMinhaAgenda = new JSONObject(minhaAgendaJson.getString(i));
                    String cliente = null;
                    if (jsonObjectMinhaAgenda.has("cliente")) {
                        cliente = jsonObjectMinhaAgenda.getString("cliente");
                    }
                    String medico = jsonObjectMinhaAgenda.getString("medico");
                    String hora = jsonObjectMinhaAgenda.getString("hora");
                    String observacao = null;
                    if (jsonObjectMinhaAgenda.has("observacao")) {
                        observacao = jsonObjectMinhaAgenda.getString("observacao");
                    }
                    String idConsulta = jsonObjectMinhaAgenda.getString("idConsulta");
                    String dataConsulta = jsonObjectMinhaAgenda.getString("dataConsulta");
                    String status = jsonObjectMinhaAgenda.getString("status");
                    String id = jsonObjectMinhaAgenda.getString("_id");
                    Consulta minhaConsulta = new Consulta(observacao, hora, dataConsulta, status, cliente, medico, "", idConsulta, id);
                    minhaAgenda.add(minhaConsulta);
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhuma registro de agenda");
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return minhaAgenda;
    }


    public HashSet<Consulta> getConsultasDisponíveis(String jsonString) {
        HashSet<Consulta> consultasDisponiveis = new HashSet<Consulta>();

        try {
            JSONArray consultasDispJson = new JSONArray(jsonString);
            JSONObject jsonObjectConsulytasDisp;
            if (consultasDispJson.length() > 0) {
                for (int i = 0; i < consultasDispJson.length(); i++) {
                    jsonObjectConsulytasDisp = new JSONObject(consultasDispJson.getString(i));
                    String cliente = null;
                    if (jsonObjectConsulytasDisp.has("cliente")) {
                        cliente = jsonObjectConsulytasDisp.getString("cliente");
                    }
                    String medico = jsonObjectConsulytasDisp.getString("medico");
                    String hora = jsonObjectConsulytasDisp.getString("hora");
                    String observacao = null;
                    if (jsonObjectConsulytasDisp.has("observacao")) {
                        observacao = jsonObjectConsulytasDisp.getString("observacao");
                    }
                    String idConsulta = jsonObjectConsulytasDisp.getString("idConsulta");
                    String dataConsulta = jsonObjectConsulytasDisp.getString("dataConsulta");
                    String status = jsonObjectConsulytasDisp.getString("status");
                    String id = jsonObjectConsulytasDisp.getString("_id");
                    Consulta consultaDisp = new Consulta(observacao, hora, dataConsulta, status, cliente, medico, "", idConsulta, id);

                    consultasDisponiveis.add(consultaDisp);
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhuma consultaDisponivel");
            }

        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return consultasDisponiveis;
    }

    public HashSet<String> getHorasDisponiveis(String jsonString) {
        HashSet<String> horasDisp = new HashSet<String>();

        try {
            JSONArray horasDispJson = new JSONArray(jsonString);
            JSONObject jsonObjectHorasDisp;
            if(horasDispJson.length() > 0) {
                for (int i = 0; i < horasDispJson.length(); i++) {
                    jsonObjectHorasDisp = new JSONObject(horasDispJson.getString(i));
                    String hora = jsonObjectHorasDisp.getString("hora");
                    horasDisp.add(hora); //.substring(0,1).toUpperCase().concat(nome.substring(1)));
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhum horario");
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return horasDisp;
    }

    public HashSet<Consulta> getMinhasConsultas(String jsonString) {

        HashSet<Consulta> consultas = new HashSet<Consulta>();

        try {
            JSONArray nomesJson = new JSONArray(jsonString);
            JSONObject jsonObjectNome;
            if(nomesJson.length() > 0) {
                for (int i = 0; i < nomesJson.length(); i++) {
                    jsonObjectNome = new JSONObject(nomesJson.getString(i));
                    String observacao = null;
                    if (jsonObjectNome.has("observacao")) {
                        observacao = jsonObjectNome.getString("observacao");
                    }
                    String hora = jsonObjectNome.getString("hora");
                    String status = jsonObjectNome.getString("status");
                    String cliente = null;
                    if (jsonObjectNome.has("cliente")) {
                        cliente = jsonObjectNome.getString("cliente");
                    }
                    String medico = jsonObjectNome.getString("medico");
                    String idConsulta = jsonObjectNome.getString("idConsulta");
                    String dataConsulta = jsonObjectNome.getString("dataconsulta");
                    String id = jsonObjectNome.getString("_id");
                    Consulta consulta = new Consulta(observacao, hora, dataConsulta, status, cliente, medico, "", idConsulta, id);
                    consultas.add(consulta);
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhuma consulta_");
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return consultas;
    }

    public HashSet<Consulta> getConsultasEntity(String jsonString) {
        HashSet<Consulta> consultas = new HashSet<Consulta>();

        try {
            JSONArray consultasJson = new JSONArray(jsonString);
            JSONObject jsonObjectConsulta;
            if (consultasJson.length() > 0) {
                for (int i = 0; i < consultasJson.length(); i++) {
                    jsonObjectConsulta = new JSONObject(consultasJson.getString(i));
                    //Log.i("Nome: ","nome=" + jsonObjectEstado.getString("nome"));
                    String observacao = null;
                    if (jsonObjectConsulta.has("observacao")) {
                        observacao = jsonObjectConsulta.getString("observacao");
                    }
                    String hora = jsonObjectConsulta.getString("hora");
                    String dataConsulta = jsonObjectConsulta.getString("dataConsulta");
                    String status = jsonObjectConsulta.getString("status");
                    String cliente = null;
                    if (jsonObjectConsulta.has("cliente")) {
                        cliente = jsonObjectConsulta.getString("cliente");
                    }
                    String medico = jsonObjectConsulta.getString("medico");
                    String idConsulta = jsonObjectConsulta.getString("idConsulta");
                    String id = jsonObjectConsulta.getString("_id");
                    //Consulta consulta = new Consulta(observacao, hora, dataConsulta, status, cliente, medico, "", idConsulta);
                    Consulta consulta = new Consulta(observacao, hora, dataConsulta, status, cliente, medico, "", idConsulta, id);
                    consultas.add(consulta);
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhuma consulta.");
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return consultas;
    }


    public HashSet<String> getConsultas(String jsonString) {
        HashSet<String> consultas = new HashSet<String>();

        try {
            JSONArray consultasJson = new JSONArray(jsonString);
            JSONObject jsonObjectConsulta;
            if (consultasJson.length() > 0) {
                for (int i = 0; i < consultasJson.length(); i++) {
                    jsonObjectConsulta = new JSONObject(consultasJson.getString(i));
                    //Log.i("Nome: ","nome=" + jsonObjectNome.getString("nome"));
                    String consulta = jsonObjectConsulta.getString("medico") + " - " + jsonObjectConsulta.getString("dataConsulta") + " - " + jsonObjectConsulta.getString("hora");
                    consultas.add(consulta); //.substring(0,1).toUpperCase().concat(nome.substring(1)));
                }
            }
            else {
                Log.d("Erro", "Nao foi encontrado nenhuma consulta");
            }
        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return consultas;
    }

    public Medico getMedicoByID(String jsonString) {

        Medico medico = new Medico();

        try {

            JSONObject jsonObjectMedico;
            jsonObjectMedico = new JSONObject(jsonString);

            String nome = jsonObjectMedico.getString("name");
            String crm = jsonObjectMedico.getString("crm");
            String id = jsonObjectMedico.getString("_id");
            //String email = jsonObjectMedico.getString("email");
            //String especialidade = jsonObjectMedico.getString("especialidade");
            //medico = new Medico(nome, String cpf, Date data, String genero, id, email, String telefone, String especialidade, crm);
            medico = new Medico(nome, null, null, null, id, null, null, null, crm);


        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }
        return medico;
    }

    public Boolean getDisponibilidadeMedico(String jsonString) {
        HashSet<Consulta> consultasDisponiveis = new HashSet<Consulta>();

        try {
            JSONArray consultasDispJson = new JSONArray(jsonString);
            JSONObject jsonObjectConsulytasDisp;
            if (consultasDispJson.length() > 0) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}