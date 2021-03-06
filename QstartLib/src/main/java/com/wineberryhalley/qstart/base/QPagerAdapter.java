package com.wineberryhalley.qstart.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public abstract class QPagerAdapter<T, L> extends PagerAdapter {
    private ArrayList<T> listing = new ArrayList<>();
    private Activity activity;
    public void config(Activity activity, ArrayList<T> kust, L mod){
        listing.addAll(kust);
        this.activity = activity;
this.madal = mod;
    }
private L madal;
    public abstract int resLayout();
    public abstract void binded(View base, T model, L mod, int position);

    @Override
    public int getCount() {
        return listing.size();
    }private QAdapter.onClickPos<L> clickPos;

    public void setClickPos(QAdapter.onClickPos<L> listener){
        this.clickPos = listener;
    }

    public QAdapter.onClickPos<L> getClickPos(){
        return clickPos;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View v = LayoutInflater.from(activity).inflate(resLayout(), container, false);
        container.addView(v);
        //Log.e(TAG, "instantiateItem: " );
        binded(v, listing.get(position), madal, position);
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
