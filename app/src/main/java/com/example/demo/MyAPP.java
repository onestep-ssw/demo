package com.example.demo;

import android.app.Application;
import android.content.Context;

import com.alipay.mobile.framework.quinoxless.IInitCallback;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.squareup.leakcanary.LeakCanary;

public class MyAPP extends Application {

    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);

        QuinoxlessFramework.setup(this, new IInitCallback() {
            @Override
            public void onPostInit() {
                //在这里使用mPaaS功能
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        QuinoxlessFramework.init();
    }
}
