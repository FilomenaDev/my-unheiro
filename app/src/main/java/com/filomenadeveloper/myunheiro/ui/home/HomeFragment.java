package com.filomenadeveloper.myunheiro.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.filomenadeveloper.myunheiro.R;
import com.filomenadeveloper.myunheiro.ui.agendas.AgendaAdapter;
import com.filomenadeveloper.myunheiro.ui.agendas.Pedidos;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<Integer> mLista;
    private RecyclerView mRecyclerView;
    DesenhoAdapter agendaAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.getRootView().findViewById(R.id.recy_desenho);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);

        mLista = new ArrayList<>();
        mLista.add(R.drawable.manicure24);
        mLista.add(R.drawable.manicure22);
        mLista.add(R.drawable.noiva);
        mLista.add(R.drawable.manicure);
        mLista.add(R.drawable.gel);
        mLista.add(R.drawable.gelinho_pe);
        mLista.add(R.drawable.gel_pe);
        mLista.add(R.drawable.gelinho);
        mLista.add(R.drawable.gel_pe1);
        mLista.add(R.drawable.manicure2);
        mLista.add(R.drawable.manicure3);
        mLista.add(R.drawable.pedicure);
        mLista.add(R.drawable.pedicure1);
        mLista.add(R.drawable.manicure6);
        mLista.add(R.drawable.manicure8);
        mLista.add(R.drawable.pedicure2);
        mLista.add(R.drawable.pedicure3);
        mLista.add(R.drawable.manicure11);
        mLista.add(R.drawable.manicure13);
        mLista.add(R.drawable.pedicure4);
        mLista.add(R.drawable.pedicure6);
        mLista.add(R.drawable.manicure14);
        mLista.add(R.drawable.manicure15);
        mLista.add(R.drawable.pedicure7);
        mLista.add(R.drawable.pedicure8);
        mLista.add(R.drawable.manicure16);
        mLista.add(R.drawable.manicure17);
        mLista.add(R.drawable.pedicure9);
        mLista.add(R.drawable.pedicure10);
        mLista.add(R.drawable.manicure18);
        mLista.add(R.drawable.manicure19);
        mLista.add(R.drawable.pedicure11);
        mLista.add(R.drawable.pedicure12);
        mLista.add(R.drawable.manicure20);
        mLista.add(R.drawable.pedicure14);
        mLista.add(R.drawable.pedicure15);
        mLista.add(R.drawable.pedicure16);
        mLista.add(R.drawable.pedicure19);
        mLista.add(R.drawable.pedicure17);
        mLista.add(R.drawable.pedicure24);
        mLista.add(R.drawable.manicure23);
        mLista.add(R.drawable.pedicure23);
        mLista.add(R.drawable.pedicure20);

        agendaAdapter = new DesenhoAdapter(getContext(),mLista);
        mRecyclerView.setAdapter(agendaAdapter);
        return root;
    }
}