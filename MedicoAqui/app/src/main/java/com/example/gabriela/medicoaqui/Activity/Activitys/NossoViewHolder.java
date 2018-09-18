package com.example.gabriela.medicoaqui.Activity.Activitys;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.gabriela.medicoaqui.R;


public class NossoViewHolder extends RecyclerView.ViewHolder {

    final TextView medico;
    final TextView dataHora;
    final TextView crm;
    final TextView especialidade;
    final TextView consultorio;

    public Button desmarcar_consulta;
    public Button remarcar_consulta;


    public NossoViewHolder(View view) {

        super(view);
        medico = (TextView) view.findViewById(R.id.consulta_medico);
        dataHora = (TextView) view.findViewById(R.id.consulta_datahora);
        crm = (TextView) view.findViewById(R.id.consulta_crm);
        especialidade = (TextView) view.findViewById(R.id.consulta_especialidade);
        consultorio = (TextView) view.findViewById(R.id.consulta_consultorio);

        desmarcar_consulta = (Button) itemView.findViewById(R.id.button_desmarcar_consulta);
        remarcar_consulta = (Button) itemView.findViewById(R.id.button_remarcar_consulta);

    }

}
