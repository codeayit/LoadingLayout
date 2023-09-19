package com.ayti.loadinglayout;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * create by ayit
 * on date 2018-01-30
 * TODO
 */

public class Utils {


    public static Drawable getDrawble(Context conetxt, @DrawableRes int id){
        return ContextCompat.getDrawable(conetxt,id);
    }

    public static int getColor(Context conetxt, @ColorRes int id){
        return  ContextCompat.getColor(conetxt,id);
    }

    public static String getString(Context conetxt, @StringRes int id){
        return  conetxt.getResources().getString(id);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics()
                .scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dp2px(Context context, int dip) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static <T extends View> T findViewById(View v, int id) {


        return (T) v.findViewById(id);
    }

}