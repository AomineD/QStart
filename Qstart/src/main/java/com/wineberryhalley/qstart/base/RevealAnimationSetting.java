package com.wineberryhalley.qstart.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

//...
// Parcelable RevealAnimationSettings with AutoValue
@AutoValue
public abstract class RevealAnimationSetting implements Parcelable {
    public abstract int getCenterX();
    public abstract int getCenterY();
    public abstract int getWidth();
    public abstract int getHeight();

    public static RevealAnimationSetting with(int centerX, int centerY, int width, int height) {
        return new AutoValue_RevealAnimationSetting(centerX, centerY, width, height) {
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }

            @Override
            public int getCenterX() {
                return centerX;
            }

            @Override
            public int getCenterY() {
                return centerY;
            }

            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }
        };
    }

    private abstract static class AutoValue_RevealAnimationSetting extends RevealAnimationSetting {
        AutoValue_RevealAnimationSetting(int centerX, int centerY, int width, int height) {
            super();
        }
    }
}
