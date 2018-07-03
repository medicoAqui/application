package com.example.gabriela.medicoaqui.Activity.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.gabriela.medicoaqui.Activity.Adapters.PerguntasFrequentesAdaptador;
import com.example.gabriela.medicoaqui.Activity.Entities.Resposta;
import com.example.gabriela.medicoaqui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerguntasFrequentesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perguntas_frequentes);

        ExpandableListView elvFaq = (ExpandableListView) findViewById(R.id.elvFaq);

        // cria os grupos
        List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Exemplo de pergunta 1?");
        lstGrupos.add("Exemplo de pergunta 2?");
        lstGrupos.add("Exemplo de pergunta 3?");

        // cria os itens de cada grupo
        List<Resposta> lstRespostaPrimeiraPergunta = new ArrayList<>();
        lstRespostaPrimeiraPergunta.add(new Resposta("Exemplo de resposta 1"));

        List<Resposta> lstRespostaSegundaPergunta = new ArrayList<>();
        lstRespostaSegundaPergunta.add(new Resposta("Exemplo de resposta 2"));

        List<Resposta> lstRespostaTerceiraPergunta = new ArrayList<>();
        lstRespostaTerceiraPergunta.add(new Resposta("Exemplo de resposta 3"));

        // cria o "relacionamento" dos grupos com seus itens
        HashMap<String, List<Resposta>> lstItensGrupo = new HashMap<>();
        lstItensGrupo.put(lstGrupos.get(0), lstRespostaPrimeiraPergunta);
        lstItensGrupo.put(lstGrupos.get(1), lstRespostaSegundaPergunta);
        lstItensGrupo.put(lstGrupos.get(2), lstRespostaTerceiraPergunta);

        // cria um adaptador (BaseExpandableListAdapter) com os dados acima
        PerguntasFrequentesAdaptador adaptador = new PerguntasFrequentesAdaptador(this, lstGrupos, lstItensGrupo);
        // define o apadtador do ExpandableListView
        elvFaq.setAdapter(adaptador);
    }

}