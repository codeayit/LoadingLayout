package com.ayti.loadinglayout;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lny on 2018/1/30.
 */

public class DefaultEmptyPage extends BasePage {


    public DefaultEmptyPage(Context context) {
        super(context);
    }

    @Override
    public void initView(View emptyPage) {
        TextView emptyText = Utils.findViewById(emptyPage, R.id.empty_text);
        emptyText.setText("暂无数据");
        emptyText.setTextColor(Utils.getColor(context,R.color.base_text_color_light));
        emptyText.setTextSize(14);

        ImageView  emptyImg = Utils.findViewById(emptyPage, R.id.empty_img);
        emptyImg.setImageResource(R.mipmap.empty);

    }

    @Override
    public int pageViewLayoutId() {
        return R.layout.widget_empty_page;
    }

    @Override
    public View getOnReloadView() {
        return null;
    }
}
