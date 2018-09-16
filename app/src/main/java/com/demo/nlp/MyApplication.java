package com.demo.nlp;

import android.app.Application;

import com.demo.nlp.util.LogUtil;

/**
 * Created by Lori on 2017/12/25.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
    }
}
