package com.filomenadeveloper.myunheiro.ui.agendas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private List<Pedidos> mLista;
    private RecyclerView mRecyclerView;
    AgendaAdapter agendaAdapter;
    DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mRecyclerView = root.getRootView().findViewById(R.id.recy_agenda);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLista = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Agendado");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mLista.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Pedidos pedidos = snapshot.getValue(Pedidos.class);
                    assert pedidos!=null;
                    mLista.add(pedidos);
                }
                agendaAdapter = new AgendaAdapter(getContext(),mLista);
                mRecyclerView.setAdapter(agendaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    //    mLista.add(new Pedidos("Filomena","05/02/2021","00:28","","",""));
     //   mLista.add(new Pedidos("Cristina","05/02/2021","00:28","","",""));



        return root;
    }
}