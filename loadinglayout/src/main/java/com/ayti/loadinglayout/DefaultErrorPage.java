package com.ayti.loadinglayout;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lny on 2018/1/30.
 */

public class DefaultErrorPage extends BasePage {

    public DefaultErrorPage(Context context) {
        super(context);
        TextView errorText = Utils.findViewById(getPageView(), R.id.error_text);
        errorText.setText("加载失败，请稍后重试···");
        errorText.setTextColor(Utils.getColor(context, R.color.base_text_color_light));
        errorText.setTextSize(14);

        ImageView errorImg = Utils.findViewById(getPageView(), R.id.error_img);
        errorImg.setImageResource(R.mipmap.error);

        TextView errorReloadBtn = Utils.findViewById(getPageView(), R.id.error_reload_btn);
        errorReloadBtn.setBackgroundResource(R.drawable.selector_btn_back_gray);

        errorReloadBtn.setText("点击重试");

        errorReloadBtn.setTextSize(14);

        errorReloadBtn.setTextColor(Utils.getColor(context, R.color.base_text_color_light));
//        errorReloadBtn.setHeight(Utils.dp2px(mContext, mConfig.buttonHeight));
//        errorReloadBtn.setWidth(Utils.dp2px(mContext, mConfig.buttonWidth));

    }

    @Override
    public void initView(View pageView) {

    }

    @Override
    public int pageViewLayoutId() {
        return R.layout.widget_error_page;
    }

    @Override
    public View getOnReloadView() {
        return Utils.findViewById(getPageView(), R.id.error_reload_btn);
    }
}
