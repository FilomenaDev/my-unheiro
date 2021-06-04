package com.filomenadeveloper.myunheiro.ui.agendas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.filomenadeveloper.myunheiro.R;

import java.util.List;


public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewAdapter> {

    Context context;
    List<Pedidos> mPedidos;

    public AgendaAdapter(Context context, List<Pedidos> mPedidos) {
        this.context = context;
        this.mPedidos = mPedidos;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_agenda,parent,false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        holder.data.setText(mPedidos.get(position).getData());
        holder.time.setText(mPedidos.get(position).getHora());
        holder.nome.setText(mPedidos.get(position).getNome());
        if(mPedidos.get(position).getPes().equals("")){
            holder.pe.setVisibility(View.GONE);
        }
        if (mPedidos.get(position).getMaos().equals("")){
            holder.maos.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mPedidos.size();
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {

        private TextView nome,data, time;
        private ImageView maos,pe,atendimento;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.namecli);
            data = itemView.findViewById(R.id.data_agenda);
            time = itemView.findViewById(R.id.time_agenda);
            maos = itemView.findViewById(R.id.image_maos);
            pe = itemView.findViewById(R.id.image_pes);
            atendimento = itemView.findViewById(R.id.image_tipo);
        }
    }
}
