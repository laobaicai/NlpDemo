package com.demo.nlp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static synchronized boolean copyData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", 0);
        boolean hasData = sharedPreferences.getBoolean("hasData", false);
        LogUtil.i(TAG, "hasData = " + hasData);
        if (hasData) {
            return true;
        }
        AssetManager assetManager = context.getAssets();
        String toPath = "/data/data/" + context.getPackageName() + "/files/config/";  // Your application path
        boolean isCopy = FileUtils.copyAssetFolder(assetManager, "config", toPath);
        LogUtil.i(TAG, "isCopy = " + isCopy);
        if (isCopy) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("hasData", true);
            editor.commit();
        }
        return isCopy;
    }
}
