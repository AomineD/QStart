package com.wineberryhalley.qstart.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.BaseAdapter;
import com.wineberryhalley.qstart.base.PicassoUtils;
import com.wineberryhalley.qstart.utils.Country;

import java.util.ArrayList;

public class CountryAdapter extends BaseAdapter<CountryAdapter.CountryHolder, Country> {

    public CountryAdapter(Activity activity, ArrayList<Country> arrayList){
        config(activity, arrayList);
    }

    @Override
    protected int resLayout() {
        return R.layout.country_item;
    }

    @Override
    protected RecyclerView.ViewHolder viewHolderClass(View layout) {
        return new CountryHolder(layout);
    }

    @Override
    protected void bindHolder(CountryHolder holder, int position, Country model) {
        holder.setConfig(model);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//model.isSelected = true;
select(holder, position);
    }
});
holders.add(holder);
    }

    private ArrayList<CountryHolder> holders = new ArrayList<>();

    private void select(CountryHolder holder, int pos){
        for (int i = 0; i < getArray().size(); i++) {
            if(pos != i){
                getArray().get(i).isSelected = false;
            }
        }

        for (CountryHolder h:
             holders) {
            h.deselect();
        }

        getArray().get(pos).isSelected = true;
     //   notifyDataSetChanged();
      holder.select();
    }

    public class CountryHolder extends RecyclerView.ViewHolder{
private CardView cardView;
private TextView tt;
private ImageView img_country;
private LottieAnimationView lt;
        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            img_country = itemView.findViewById(R.id.img_country);
            tt = itemView.findViewById(R.id.text_country);
            cardView = (CardView) itemView;
            lt = itemView.findViewById(R.id.loading_anim);
        }

        public void setConfig(Country model){
            if(model.isSelected){
                tt.setText(model.getNameTranslated());
                cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.dark));
                tt.setTextColor(Color.WHITE);
                lt.setVisibility(View.VISIBLE);
                PicassoUtils.loadCountryFlag(model.getAlpha2Code(), img_country, lt);
            }else{
            //    Log.e("MAIN", "setConfig: "+model.getFlag());
                cardView.setCardBackgroundColor(Color.WHITE);
               tt.setTextColor(getContext().getResources().getColor(R.color.black2));
                tt.setText(model.getNameTranslated());
                lt.setVisibility(View.VISIBLE);
                PicassoUtils.loadCountryFlag(model.getAlpha2Code(), img_country, lt);
            }
        }

        public void select(){
            cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.dark));
            tt.setTextColor(Color.WHITE);
        }

        public void deselect(){
            cardView.setCardBackgroundColor(Color.WHITE);
            tt.setTextColor(getContext().getResources().getColor(R.color.black2));
        }
    }



}

