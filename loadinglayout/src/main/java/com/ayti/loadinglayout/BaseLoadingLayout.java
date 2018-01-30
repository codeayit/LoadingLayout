package com.ayti.loadinglayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lny on 2018/1/30.
 */

public class BaseLoadingLayout extends FrameLayout {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    public final static int No_Network = 3;
    public final static int Loading = 4;
    private int state;


    private Context mContext;
    private View loadingPage;
    private BasePage errorPage;
    private BasePage emptyPage;
    private BasePage networkPage;
    private BasePage defineLoadingPage;

    private View contentView;
    private OnReloadListener onReloadListener;
    private boolean isFirstVisible; //是否一开始显示contentview，默认不显示
    private int pageBackground;
    private static LoadingLayout.Config mConfig = new LoadingLayout.Config();   //配置

    public BaseLoadingLayout(@NonNull Context context) {
        super(context);
    }

    public BaseLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.LoadingLayout_isFirstVisible, false);
        pageBackground = a.getColor(R.styleable.LoadingLayout_pageBackground, Utils.getColor(mContext, R.color
                .base_loading_background));
        a.recycle();
    }

    public BaseLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }
        contentView = this.getChildAt(0);
        if (!isFirstVisible) {
            contentView.setVisibility(View.GONE);
        }
        build();
    }


    private void build() {

        if (mConfig.loadingView == null) {
            loadingPage = LayoutInflater.from(mContext).inflate(mConfig.loadingLayoutId, null);
        } else {
            loadingPage = mConfig.loadingView;
        }
//        errorPage = LayoutInflater.from(mContext).inflate(R.layout.widget_error_page, null);
        errorPage = new DefaultErrorPage(mContext);
//        emptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_empty_page, null);
        emptyPage = new DefaultEmptyPage(mContext);
//        networkPage = LayoutInflater.from(mContext).inflate(R.layout.widget_nonetwork_page, null);
        networkPage = new DefaultNoNetWorkPage(mContext);
        defineLoadingPage = null;

        loadingPage.setBackgroundColor(pageBackground);
        errorPage.getPageView().setBackgroundColor(pageBackground);
        emptyPage.getPageView().setBackgroundColor(pageBackground);
        networkPage.getPageView().setBackgroundColor(pageBackground);

        if (errorPage.getOnReloadView()!=null){
            errorPage.getOnReloadView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onReloadListener!=null){
                        onReloadListener.onReload();
                    }
                }
            });
        }

        if (emptyPage.getOnReloadView()!=null){
            emptyPage.getOnReloadView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onReloadListener!=null){
                        onReloadListener.onReload();
                    }
                }
            });
        }

        if (networkPage.getOnReloadView()!=null){
            networkPage.getOnReloadView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onReloadListener!=null){
                        onReloadListener.onReload();
                    }
                }
            });
        }

        this.addView(networkPage.getPageView());
        this.addView(emptyPage.getPageView());
        this.addView(errorPage.getPageView());
        this.addView(loadingPage);
    }
    /**
     * 设置ReloadButton的监听器
     *
     * @param listener
     * @return
     */
    public BaseLoadingLayout setOnReloadListener(OnReloadListener listener) {

        this.onReloadListener = listener;
        return this;
    }

    public void setStatus(@BaseLoadingLayout.Flavour int status) {

        this.state = status;

        switch (status) {
            case Success:
                contentView.setVisibility(View.VISIBLE);
                emptyPage.getPageView().setVisibility(View.GONE);
                errorPage.getPageView().setVisibility(View.GONE);
                networkPage.getPageView().setVisibility(View.GONE);
                if (defineLoadingPage != null) {

                    defineLoadingPage.getPageView().setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case Loading:

                contentView.setVisibility(View.GONE);
                emptyPage.getPageView().setVisibility(View.GONE);
                errorPage.getPageView().setVisibility(View.GONE);
                networkPage.getPageView().setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.getPageView().setVisibility(View.VISIBLE);
                } else {
                    loadingPage.setVisibility(View.VISIBLE);
                }
                break;

            case Empty:

                contentView.setVisibility(View.GONE);
                emptyPage.getPageView().setVisibility(View.VISIBLE);
                errorPage.getPageView().setVisibility(View.GONE);
                networkPage.getPageView().setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.getPageView().setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case Error:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.getPageView().setVisibility(View.GONE);
                errorPage.getPageView().setVisibility(View.VISIBLE);
                networkPage.getPageView().setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.getPageView().setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case No_Network:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.getPageView().setVisibility(View.GONE);
                errorPage.getPageView().setVisibility(View.GONE);
                networkPage.getPageView().setVisibility(View.VISIBLE);
                if (defineLoadingPage != null) {

                    defineLoadingPage.getPageView().setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }

    }

    /**
     * 返回当前状态{Success, Empty, Error, No_Network, Loading}
     *
     * @return
     */
    public int getStatus() {

        return state;
    }


    @IntDef({Success, Empty, Error, No_Network, Loading})
    public @interface Flavour {

    }



    /**
     * 获取全局配置的class
     *
     * @return
     */
    public static LoadingLayout.Config getConfig() {

        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    public static class Config {


    }

    /**
     * 自定义加载页面，仅对当前所在的Activity有效
     *
     * @param loadingPage
     * @return
     */
    public BaseLoadingLayout setLoadingPage(BasePage loadingPage) {

        defineLoadingPage = loadingPage;
        this.removeView(loadingPage.getPageView());
        defineLoadingPage.getPageView().setVisibility(View.GONE);
        this.addView(loadingPage.getPageView());
        return this;
    }

}
