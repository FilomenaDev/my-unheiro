package com.filomenadeveloper.myunheiro.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.filomenadeveloper.myunheiro.R;
import com.filomenadeveloper.myunheiro.ui.agendas.Pedidos;

import java.util.List;


public class DesenhoAdapter extends RecyclerView.Adapter<DesenhoAdapter.MyViewAdapter> {

    Context context;
    List<Integer> mSrc;

    public DesenhoAdapter(Context context, List<Integer> mSrc) {
        this.context = context;
        this.mSrc = mSrc;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iten_desenhos,parent,false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        holder.desenho.setImageResource(mSrc.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mSrc.size();
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {

        private ImageView desenho;
        private CardView cardView;
        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_desenho);
            desenho = itemView.findViewById(R.id.img_desenho);

        }
    }
}
