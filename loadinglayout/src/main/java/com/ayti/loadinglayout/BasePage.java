package com.ayti.loadinglayout;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by lny on 2018/1/30.
 */

public abstract class BasePage {
    public Context context;
    private View pageView;

    public BasePage(Context context) {
        this.context = context;
        pageView = LayoutInflater.from(context).inflate(pageViewLayoutId(),null);
        initView(pageView);
    }

    public abstract void initView(View pageView);

    public abstract int pageViewLayoutId();


    public View getPageView(){
        return pageView;
    }
    public abstract View getOnReloadView();
}
