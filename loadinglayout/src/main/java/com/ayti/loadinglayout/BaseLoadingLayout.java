package com.ayti.loadinglayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.reflect.Constructor;

/**
 * Created by lny on 2018/1/30.
 */

public class BaseLoadingLayout extends FrameLayout implements View.OnClickListener {

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

    private static int laodingLayout = -1;
    private static Class<? extends BasePage> errorPageClass = null;
    private static Class<? extends BasePage> emptyPageClass = null;
    private static Class<? extends BasePage> networkPageClass = null;

    private View defineLoadingPage;


    /**
     * 设置公共的页面
     * @param errorPage
     * @param emptyPage
     * @param networkPage
     */
    public static void setGlobalPages(Class<? extends BasePage> errorPage,Class<? extends BasePage> emptyPage,Class<? extends BasePage> networkPage){
        errorPageClass = errorPage;
        emptyPageClass = emptyPage;
        networkPageClass = networkPage;
    }

    /**
     * 全局loading page
     * @param resLayout
     */
    public static void setGlobalLoadingPage(int resLayout){
        laodingLayout = resLayout;
    }

    public View getLoadingPage(){
        return loadingPage;
    }

    private BasePage createDefaultErrorPage(Context context){
        if (errorPageClass!=null){
            try {
                Constructor<? extends BasePage> constructor = errorPageClass.getConstructor(Context.class);
                BasePage page = constructor.newInstance(context);
                return page;
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultErrorPage(context);
            }
        }else{
            return new DefaultErrorPage(context);
        }
    }

    private BasePage createDefaultEmptyPage(Context context){
        if (emptyPageClass!=null){
            try {
                Constructor<? extends BasePage> constructor = emptyPageClass.getConstructor(Context.class);
                BasePage page = constructor.newInstance(context);
                return page;
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultEmptyPage(context);
            }
        }else{
            return new DefaultEmptyPage(context);
        }
    }

    private BasePage createDefaultNetworkPage(Context context){
        if (networkPageClass!=null){
            try {
                Constructor<? extends BasePage> constructor = networkPageClass.getConstructor(Context.class);
                BasePage page = constructor.newInstance(context);
                return page;
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultNoNetWorkPage(context);
            }
        }else{
            return new DefaultNoNetWorkPage(context);
        }
    }

    private View contentView;
    private OnPageContentClickListener onPageContentClickListener;
    private boolean isFirstVisible; //是否一开始显示contentview，默认不显示
//    private int pageBackground;

    public BaseLoadingLayout(@NonNull Context context) {
        super(context);
    }

    public BaseLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseLoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.BaseLoadingLayout_isFirstVisible, false);
//        pageBackground = a.getColor(R.styleable.BaseLoadingLayout_pageBackground, Utils.getColor(mContext, R.color
//                .base_loading_background));
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

        if (laodingLayout == -1) {
            loadingPage = LayoutInflater.from(mContext).inflate(R.layout.widget_loading_page, null);
        }else{
            loadingPage = LayoutInflater.from(mContext).inflate(laodingLayout, null);
            loadingPage.setVisibility(GONE);
        }
//        loadingPage.setBackgroundColor(pageBackground);
        this.addView(loadingPage);

//        errorPage = LayoutInflater.from(mContext).inflate(R.layout.widget_error_page, null);
        if (errorPage == null) {
            errorPage = createDefaultErrorPage(mContext);
//            errorPage.getPageView().setBackgroundColor(pageBackground);
            this.addView(errorPage.getPageView());
        }
//        emptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_empty_page, null);
        if (emptyPage == null) {
            emptyPage = createDefaultEmptyPage(mContext);
//            emptyPage.getPageView().setBackgroundColor(pageBackground);
            this.addView(emptyPage.getPageView());
        }
//        networkPage = LayoutInflater.from(mContext).inflate(R.layout.widget_nonetwork_page, null);
        if (networkPage == null) {
            networkPage = createDefaultNetworkPage(mContext);
//            networkPage.getPageView().setBackgroundColor(pageBackground);
            this.addView(networkPage.getPageView());
        }

//        if (errorPage.getOnReloadView() != null) {
//            errorPage.getOnReloadView().setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onReloadListener != null) {
//                        onReloadListener.onReload(view);
//                    }
//                }
//            });
//        }
//
//        if (emptyPage.getOnReloadView() != null) {
//            emptyPage.getOnReloadView().setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onReloadListener != null) {
//                        onReloadListener.onReload(view);
//                    }
//                }
//            });
//        }
//
//        if (networkPage.getOnReloadView() != null) {
//            networkPage.getOnReloadView().setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onReloadListener != null) {
//                        onReloadListener.onReload(view);
//                    }
//                }
//            });
//        }

        setReloadListeners(errorPage.getOnReloadViews());
        setReloadListeners(emptyPage.getOnReloadViews());
        setReloadListeners(networkPage.getOnReloadViews());
    }

    private void setReloadListeners(View[] views){
        if (views!=null){
            for (View v:views){
                v.setOnClickListener(this);
            }
        }
    }

    private void clearReloadListeners(View[] views){
        if (views!=null){
            for (View v:views){
                v.setOnClickListener(null);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (onPageContentClickListener !=null){
            onPageContentClickListener.onClick(view);
        }
    }


    /**
     * 设置空白页
     *
     * @param basePage
     */
    public void setEmptyPage(BasePage basePage) {

        if (basePage != null && basePage.getPageView() != null) {
            basePage.getPageView().setVisibility(emptyPage.getPageView().getVisibility());
            this.removeView(emptyPage.getPageView());
//            if (emptyPage.getOnReloadView() != null)
//                emptyPage.getOnReloadView().setOnClickListener(null);
            clearReloadListeners(emptyPage.getOnReloadViews());
            emptyPage = basePage;
            this.addView(emptyPage.getPageView());
//            if (emptyPage.getOnReloadView() != null) {
//                emptyPage.getOnReloadView().setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (onReloadListener != null) {
//                            onReloadListener.onReload(emptyPage.getOnReloadView());
//                        }
//                    }
//                });
//            }
            setReloadListeners(emptyPage.getOnReloadViews());

        }
    }

    /**
     * 设置错误页面
     *
     * @param basePage
     */
    public void setErrorPage(BasePage basePage) {
        if (basePage != null && basePage.getPageView() != null) {
            basePage.getPageView().setVisibility(errorPage.getPageView().getVisibility());
            this.removeView(errorPage.getPageView());
//            if (errorPage.getOnReloadView() != null)
//                errorPage.getOnReloadView().setOnClickListener(null);
            clearReloadListeners(errorPage.getOnReloadViews());
            errorPage = basePage;
            this.addView(errorPage.getPageView());
//            if (errorPage.getOnReloadView() != null) {
//                errorPage.getOnReloadView().setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (onReloadListener != null) {
//                            onReloadListener.onReload(view);
//                        }
//                    }
//                });
//            }
            setReloadListeners(errorPage.getOnReloadViews());
        }
    }

    /**
     * 网络
     *
     * @param basePage
     */
    public void setNetworkPage(BasePage basePage) {
        if (basePage != null && basePage.getPageView() != null) {
            basePage.getPageView().setVisibility(networkPage.getPageView().getVisibility());
            this.removeView(networkPage.getPageView());
//            if (networkPage.getOnReloadView() != null)
//                networkPage.getOnReloadView().setOnClickListener(null);
            clearReloadListeners(errorPage.getOnReloadViews());
            networkPage = basePage;
            this.addView(networkPage.getPageView());
//            if (networkPage.getOnReloadView() != null) {
//                networkPage.getOnReloadView().setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (onReloadListener != null) {
//                            onReloadListener.onReload(view);
//                        }
//                    }
//                });
//            }
            setReloadListeners(networkPage.getOnReloadViews());
        }
    }


    /**
     * 设置ReloadButton的监听器
     *
     * @param listener
     * @return
     */
    public BaseLoadingLayout setOnPageContentClickListener(OnPageContentClickListener listener) {
        this.onPageContentClickListener = listener;
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

                    defineLoadingPage.setVisibility(View.GONE);
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
                    defineLoadingPage.setVisibility(View.VISIBLE);
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
                    defineLoadingPage.setVisibility(View.GONE);
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
                    defineLoadingPage.setVisibility(View.GONE);
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
                    defineLoadingPage.setVisibility(View.GONE);
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
     * 自定义加载页面，仅对当前所在的Activity有效
     *
     * @param loadingPage
     * @return
     */
    public BaseLoadingLayout setLoadingPage(View loadingPage) {
        defineLoadingPage = loadingPage;
        this.removeView(loadingPage);
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(loadingPage);
        return this;
    }

    public BaseLoadingLayout setLoadingPage(int resLayout) {
        loadingPage = LayoutInflater.from(getContext()).inflate(resLayout,null);
        defineLoadingPage = loadingPage;
        this.removeView(loadingPage);
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(loadingPage);
        return this;
    }

}
