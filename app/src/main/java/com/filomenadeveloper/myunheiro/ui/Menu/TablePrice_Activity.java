package com.filomenadeveloper.myunheiro.ui.Menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.filomenadeveloper.myunheiro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TablePrice_Activity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private AdapteTabela adapteTabela;
    List<Price> mPrices;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabela_preco);
        mRecycler = findViewById(R.id.recycler_tabela);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mPrices = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("TabelaPreco");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Price price = snapshot.getValue(Price.class);
                    assert price !=null;
                    mPrices.add(price);
                }
                adapteTabela = new AdapteTabela(getApplicationContext(),mPrices);
                mRecycler.setAdapter(adapteTabela);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    public class AdapteTabela extends RecyclerView.Adapter<TabelaViewHolder> {
        Context mContext;
        List<Price> prices;

        public AdapteTabela(Context mContext, List<Price> prices) {
            this.mContext = mContext;
            this.prices = prices;
        }

        @NonNull
        @Override
        public TabelaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.tabela_preco_item,parent,false);
            return new TabelaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TabelaViewHolder holder, int position) {
            holder.nome_produto.setText(prices.get(position).getProduto());
            holder.price_produto.setText(String.valueOf(prices.get(position).getPrice()));
        }

        @Override
        public int getItemCount() {
            return prices.size();
        }
    }

    private class TabelaViewHolder extends RecyclerView.ViewHolder {
        TextView nome_produto, price_produto;
        public TabelaViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_produto = itemView.findViewById(R.id.textView);
            price_produto = itemView.findViewById(R.id.textView1);
        }
    }
}
