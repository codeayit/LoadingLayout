package com.ayti.myloadinglayoutdemo;

import android.content.Context;
import android.view.View;

import com.ayti.loadinglayout.BasePage;

/**
 * Created by lny on 2018/2/27.
 */

public class ErrorPage extends BasePage {
    public ErrorPage(Context context) {
        super(context);
    }

    @Override
    public void initView(View pageView) {

    }

    @Override
    public int pageViewLayoutId() {
        return R.layout.layout_page_error;
    }

    @Override
    public View[] getOnReloadViews() {
        return new View[]{getPageView().findViewById(R.id.btn_relaod)};
    }
}
