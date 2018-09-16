package com.demo.nlp.util;

import android.util.Log;

/**
 * Created by Lori on 2017/11/15.
 */

public class LogUtil {
    private static final String TAG = "NlpDemo";

    private static final int MAX_LENGTH = 2000;

    private static final boolean IS_LOG_ENABLE = true;
//            Build.TYPE.equals("eng") || Build.TYPE.equals("userdebug");

    /**
     * @category 打印详细信息
     */
    public static int v(String tag, String msg) {
        if (IS_LOG_ENABLE) {
            int max_str_length = MAX_LENGTH - ((tag + " : ").length() + TAG.length());
            int count = 0;
            while (msg.length() > max_str_length) {
                count = Log.v(TAG, tag + " : " + msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            count += Log.v(TAG, tag + " : " + msg);
            return count;
        }
        return 0;
    }

    /**
     * @c0ategory 打印调试信息
     */
    public static int d(String tag, String msg) {
        if (IS_LOG_ENABLE) {
            int max_str_length = MAX_LENGTH - ((tag + " : ").length() + TAG.length());
            int count = 0;
            while (msg.length() > max_str_length) {
                count = Log.d(TAG, tag + " : " + msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            count += Log.d(TAG, tag + " : " + msg);
            return count;
        }
        return 0;
    }

    /**
     * @category 打印通知信息
     */
    public static int i(String tag, String msg) {
        if (IS_LOG_ENABLE) {
            int max_str_length = MAX_LENGTH - ((tag + " : ").length() + TAG.length());
            int count = 0;
            while (msg.length() > max_str_length) {
                count = Log.i(TAG, tag + " : " + msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            count += Log.i(TAG, tag + " : " + msg);
            return count;
        }
        return 0;
    }

    /**
     * @category 打印警告信息
     */
    public static int w(String tag, String msg) {
        if (IS_LOG_ENABLE) {
            int max_str_length = MAX_LENGTH - ((tag + " : ").length() + TAG.length());
            int count = 0;
            while (msg.length() > max_str_length) {
                count = Log.w(TAG, tag + " : " + msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            count += Log.w(TAG, tag + " : " + msg);
            return count;
        }
        return 0;
    }

    /**
     * @category 打印错误信息
     */
    public static int e(String tag, String msg) {
        if (IS_LOG_ENABLE) {
            int max_str_length = MAX_LENGTH - ((tag + " : ").length() + TAG.length());
            int count = 0;
            while (msg.length() > max_str_length) {
                count = Log.e(TAG, tag + " : " + msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            count += Log.e(TAG, tag + " : " + msg);
            return count;
        }
        return 0;
    }

    public static int wtf(String tag, String msg) {
        if (IS_LOG_ENABLE) {
            int max_str_length = MAX_LENGTH - ((tag + " : ").length() + TAG.length());
            int count = 0;
            while (msg.length() > max_str_length) {
                count = Log.wtf(TAG, tag + " : " + msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            count += Log.wtf(TAG, tag + " : " + msg);
            return count;
        }
        return 0;
    }
}
