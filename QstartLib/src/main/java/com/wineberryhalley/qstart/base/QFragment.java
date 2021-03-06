package com.wineberryhalley.qstart.base;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

import com.wineberryhalley.qstart.R;

import java.util.Random;

import static androidx.core.content.ContextCompat.getColor;

public abstract class QFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract int layoutId();

    public boolean wasInitialized() {
        return initialized;
    }
    public abstract Fragment lastFragment();
    public QFragment homeFragment;



    private View posReveal;
private int tabpos = 2;

    /**
     * Posin: es para determinar en que posicion iniciar de la altura de s (View)
     * Ejemplo: si es 2 la mitad de su altura entonces sera en la mitad del fragment
     * @param s
     * @param posin
     */
    public void setPosRevealInDevice(View s, int posin){
        this.posReveal = s;
        this.tabpos = posin;
    }



    private boolean initialized = false;
    private View containerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(layoutId(), container, false);
        containerView = container;

        if (getContext() == null) {
            Log.e("MAIN", "onCreateView: ay" );
            return v;
        }
   //     Log.e("MAIN", "reveal is null: "+(posReveal == null) );
        if (posReveal != null) {

        int colorReveal = 0;
        int pos = new Random().nextInt(2);
        switch (pos) {
            case 0:
                colorReveal = getColor(getContext(), R.color.primary_qs);
                break;
            case 1:
                colorReveal = getColor(getContext(), R.color.dark_qs);
                break;
            case 2:
                colorReveal = getColor(getContext(), R.color.primary_qs);
                break;
            default:
                colorReveal = getColor(getContext(), R.color.dark_qs);
        }

        int x =  (int) (posReveal.getX() + posReveal.getWidth() / tabpos);
        int y = (int) (posReveal.getY() + posReveal.getHeight() / tabpos);
            Log.e("MAIN", "onCreateView: "+x+" "+y);
            RevealAnimationSetting revealAnimationSettings = RevealAnimationSetting.with(
                   x,
                    y,
                    container.getWidth(),
                    container.getHeight());
           registerCircularRevealAnimation(getContext(), v, revealAnimationSettings, colorReveal, getColor(getContext(), R.color.white_qs));
        }
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

Awake();

initialized = true;
    }

    public abstract void Awake();

    public<T> T find(int id){
        if(containerView != null)
        return (T) containerView.findViewById(id);
        else
            return null;
    }

    public View findLikeView(int id){
        if(containerView != null)
            return containerView.findViewById(id);
        else
            return null;
    }

    public View getRoot(){
        return containerView;
    }

    public void loadFragmentAdd(int id, Fragment f){
    if(getFragmentManager() != null){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(id, f).commitAllowingStateLoss();

    }else{
        Log.e("MAIN", "loadFragmentAdd: null manager" );
    }
    }

    public void backToLastFragment(int id){
        if(getFragmentManager() != null){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
if( lastFragment() != null)
            fragmentTransaction.replace(id, lastFragment()).commitAllowingStateLoss();
else
    fragmentTransaction.replace(id, homeFragment).commitAllowingStateLoss();

        }else{
            Log.e("MAIN", "loadFragmentAdd: null manager" );
        }
    }


    protected boolean isLoading;
    protected int pageCount = 1;
    protected interface LoadListener{
        void initData(int page);
    }
   // protected int maxCount = 10;
    public void configScrollRecycler(RecyclerView recyclerView, LoadListener loadListener){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {

                    pageCount=pageCount+1;
                    isLoading = true;

                    // progressBar.setVisibility(View.VISIBLE);

                    loadListener.initData(pageCount);
                }
            }
        });
    }


    public static void registerCircularRevealAnimation(final Context context, final View view, final RevealAnimationSetting revealSettings, final int startColor, final int endColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    int cx = revealSettings.getCenterX();
                    int cy = revealSettings.getCenterY();
                    int width = revealSettings.getWidth();
                    int height = revealSettings.getHeight();
                    int duration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

                    //Simply use the diagonal of the view
                    float finalRadius = (float) Math.sqrt(width * width + height * height);
                    Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius).setDuration(duration);
                    anim.setInterpolator(new FastOutSlowInInterpolator());
                    anim.start();
                    startColorAnimation(view, startColor, endColor, duration);
                }
            });
        }
    }


    static void startColorAnimation(final View view, final int startColor, final int endColor, int duration) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(startColor, endColor);
        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

}