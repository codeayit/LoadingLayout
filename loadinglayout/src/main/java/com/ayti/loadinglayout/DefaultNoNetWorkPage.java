package com.ayti.loadinglayout;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lny on 2018/1/30.
 */

public class DefaultNoNetWorkPage extends BasePage {


    public DefaultNoNetWorkPage(Context context) {
        super(context);
    }

    @Override
    public void initView(View networkPage) {
        TextView networkText = Utils.findViewById(networkPage, R.id.no_network_text);
        networkText.setText("无网络连接，请检查网络···");
        networkText.setTextSize(14);
        networkText.setTextColor(Utils.getColor(context,R.color.base_text_color_light));

        ImageView networkImg = Utils.findViewById(networkPage, R.id.no_network_img);
        networkImg.setImageResource(R.mipmap.no_network);

        TextView networkReloadBtn = Utils.findViewById(networkPage, R.id.no_network_reload_btn);
        networkReloadBtn.setText("点击重试");
        networkReloadBtn.setTextSize(14);
        networkReloadBtn.setBackgroundResource(R.drawable.selector_btn_back_gray);
    }

    @Override
    public int pageViewLayoutId() {
        return R.layout.widget_nonetwork_page;
    }

    @Override
    public View getOnReloadView() {
        return Utils.findViewById(getPageView(), R.id.no_network_reload_btn);
    }
}
