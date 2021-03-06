package com.wineberryhalley.qstart.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.QPicassoUtils;

public class QImageLoadingView extends RelativeLayout {

    public QImageLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public QImageLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public QImageLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void showLoading(){
        if(loading_root != null){
            loading_root.setVisibility(VISIBLE);
        }
        if(img != null){
            img.setVisibility(GONE);
        }
    }

    public void hideLoading(){
        if(loading_root != null){
            loading_root.setVisibility(GONE);
        }
        if(img != null){
            img.setVisibility(VISIBLE);
        }
    }

    private String assetName;
    private LottieAnimationView loading_root;
    private ImageView img;
    private void init(AttributeSet attributeSet) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.RoundButton, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            assetName = a.getString(R.styleable.ImageLoadingView_assetName);
        } finally {
            a.recycle();
        }

        img = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.img_root_qs, (ViewGroup) getRootView(), false);

        this.addView(img);

        loading_root = (LottieAnimationView) LayoutInflater.from(getContext()).inflate(R.layout.loading_root_qs, (ViewGroup) getRootView(), false);
if(assetName != null && !assetName.isEmpty()){
    loading_root.setAnimation(assetName);
}
        this.addView(loading_root);


    }

    public void loadImg(String url){
        if(!url.isEmpty() && img != null && loading_root != null)
        QPicassoUtils.loadImageWithLoading(url, img, loading_root);
    }

    public void loadImg(int rawFile){
        if(rawFile != 0 && img != null && loading_root != null)
            QPicassoUtils.loadImageWithLoading(rawFile, img, loading_root);
    }


    public void loadImgRound(String url, int rounded){
        if(!url.isEmpty() && img != null && loading_root != null)
            QPicassoUtils.loadImageWithLoading(url, rounded, img, loading_root);
    }


    public void loadImgRound(int rawFile, int rounded){
        if(rawFile != 0 && img != null && loading_root != null)
            QPicassoUtils.loadImageWithLoading(rawFile, rounded, img, loading_root);
    }

    public void playAnim(){
        if(loading_root != null){
            loading_root.playAnimation();
        }
    }


    public void loadImgCircle(int rawFile){
        if(rawFile != 0 && img != null && loading_root != null)
            QPicassoUtils.loadCircleImgWithLoading(rawFile, img, loading_root);
    }
    public void loadImgCircle(String url){
        if(!url.isEmpty() && img != null && loading_root != null)
            QPicassoUtils.loadCircleImgWithLoading(url, img, loading_root);
    }


}
