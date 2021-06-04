package com.filomenadeveloper.myunheiro.ui.agendas;



import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listItem = new ArrayList<>();

public ViewPagerAdapter(FragmentManager fm){
    super(fm);
}

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listItem.get(position);
    }

    public void AddFragment(Fragment fragment, String title){
    listFragment.add(fragment);
    listItem.add(title);
    }
}
