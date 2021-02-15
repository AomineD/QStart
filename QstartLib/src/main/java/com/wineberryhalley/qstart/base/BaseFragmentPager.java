package com.wineberryhalley.qstart.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public abstract class BaseFragmentPager<T> extends FragmentStatePagerAdapter {

    public BaseFragmentPager(@NonNull FragmentManager fm, int behavior, ArrayList<T> a){
        super(fm, behavior);
        this.list = a;
    }
    ArrayList<T> list = new ArrayList<>();

    @NonNull
    @Override
    public BaseFragment getItem(int position) {
        return (BaseFragment) list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
