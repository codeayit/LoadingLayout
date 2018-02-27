package com.ayti.myloadinglayoutdemo;

import android.app.Application;

import com.ayti.loadinglayout.BaseLoadingLayout;

/**
 * Created by lny on 2018/2/27.
 */

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        BaseLoadingLayout.setGlobalPages(GlobalErrorPage.class,null,null);
    }
}
