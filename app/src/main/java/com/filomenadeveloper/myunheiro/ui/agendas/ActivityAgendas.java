package com.filomenadeveloper.myunheiro.ui.agendas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.filomenadeveloper.myunheiro.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ActivityAgendas extends Fragment {
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_agendados, container, false);

        TabLayout mTabLayout = root.findViewById(R.id.tabs);

        mViewPager = root.findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.AddFragment(new DashboardFragment(), "HOJE");
        adapter.AddFragment(new DashboardFragment(),"SEMANA");
        adapter.AddFragment(new DashboardFragment(),"TODOS");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);


        return root;
    }
}
