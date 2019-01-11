package com.ayti.myloadinglayoutdemo;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ayti.loadinglayout.BaseLoadingLayout;
import com.ayti.loadinglayout.OnPageContentClickListener;

public class MainActivity extends AppCompatActivity {

    private BaseLoadingLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = (BaseLoadingLayout) findViewById(R.id.loading);
//        loading.setStatus(BaseLoadingLayout.Error);
//        loading.setOnPageContentClickListener(new OnPageContentClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("xxxxx","Reload");
////                loading.setErrorPage(new ErrorPage(MainActivity.this));
//        loading.setStatus(BaseLoadingLayout.Loading);
//            }
//        });
        final TextView tvLoading = loading.getLoadingPage().findViewById(R.id.tv_loading);

        if (tvLoading == null){
            Log.d("xxxxx","tvLoading == null");
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(3*1000);
                    tvLoading.setText("loading");
                    loading.setStatus(BaseLoadingLayout.Loading);
                }
            });
        }

    }
}
