package com.wineberryhalley.qstart.base;

import android.animation.Animator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public  abstract class BaseAdapter<T, L> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected abstract int resLayout();
    protected void config(Activity context, ArrayList<L> list){
        this.context = context;
        this.ls = (List<L>) list;
    }

    private View.OnClickListener onClickListener;

    public void setOnClickListenerAdapter(View.OnClickListener n){
        onClickListener = n;
    }

    public View.OnClickListener getOnClickListener(){
        return onClickListener;
    }



   public interface onClickPos<L>{
       void clickPos(int pos, L model);
   }

   private onClickPos<L> clickPos;

    public void setClickPos(onClickPos<L> listener){
        this.clickPos = listener;
    }

    public onClickPos<L> getClickPos(){
        return clickPos;
    }


    private List<L> ls = new ArrayList<>();
    private Activity context;
    public Activity getContext(){
        return context;
    }
    protected abstract  RecyclerView.ViewHolder viewHolderClass(View layout);
    protected abstract void bindHolder(T holder, int position, L model);

    public ArrayList<L> getArray(){
        return (ArrayList<L>) ls;
    }

    protected boolean animate = false;
    protected long timeanim = 800;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            throw new NullPointerException("Hey te faltó el contexto");
        }
        View v = LayoutInflater.from(context).inflate(resLayout(), parent, false);
        return viewHolderClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(animate) {
            if(holder.itemView != null){
                holder.itemView.setVisibility(View.GONE);
            }
            new Timer().schedule(timerTask(holder.itemView), 300);

        }
bindHolder((T) holder, position, ls.get(position));
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    protected TimerTask timerTask(View item) {
        return new TimerTask() {
            @Override
            public void run() {
                getContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.SlideInLeft)
                                .duration(timeanim)
                                .onStart(new YoYo.AnimatorCallback() {
                                    @Override
                                    public void call(Animator animator) {
                                        item.setVisibility(View.VISIBLE);
                                    }
                                })
                                .playOn(item);
                    }
                });
            }

        };
    }
}
