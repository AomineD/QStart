package com.wineberryhalley.qstart.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public abstract class QFragmentPager<T> extends FragmentStatePagerAdapter {

    public QFragmentPager(@NonNull FragmentManager fm, int behavior, ArrayList<T> a){
        super(fm, behavior);
        this.list = a;
    }
    ArrayList<T> list = new ArrayList<>();

    @NonNull
    @Override
    public QFragment getItem(int position) {
        return (QFragment) list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
