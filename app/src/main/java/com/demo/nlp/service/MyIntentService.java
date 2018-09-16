package com.demo.nlp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.demo.nlp.util.LogUtil;
import com.demo.nlp.util.Utils;
import com.gome.nlp.Nlp;

import cn.edu.fudan.flow.CRFSemanticAnalyzerMultipleEvent;
import cn.edu.fudan.flow.NamedIdentityRecognizer;
import cn.edu.fudan.flow.PosTagger;
import cn.edu.fudan.flow.WordSegmentor;
import cn.edu.fudan.lang.EventWrapper;
import cn.edu.fudan.sentence.ConvolutionalSentenceModelDecoder;
import cn.edu.fudan.sentence.ConvolutionalSentenceModelPreprocess;

/**
 * Created by Lori on 2018/5/8.
 */

public class MyIntentService extends IntentService {
    private static final String TAG = MyIntentService.class.getSimpleName();

    private static Handler mHandler;

    private static final int CONFIG_COPY_DONE = 0;

    private static final int ANALYZE_DONE = 2;

    public static StringBuilder sb = new StringBuilder();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG, "onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.i(TAG, "onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String oriText = intent.getStringExtra("oriText");
        String task = intent.getStringExtra("task");
        switch (task) {
            case "init":
                handleInitTask();
                break;
            case "analyze":
                handleAnalyzeTask(oriText);
                break;
            default:
                break;
        }
    }

    private void handleAnalyzeTask(String oriText) {
        LogUtil.i(TAG, "handleAnalyzeTask result:");
        long time = System.currentTimeMillis();
        String result = Nlp.analyze(oriText);
        long diff = System.currentTimeMillis() - time;
        LogUtil.i(TAG, "handleAnalyzeTask result:" + result);
        if (null != mHandler) {
            Message msg = mHandler.obtainMessage();
            msg.obj = result;
            msg.what = ANALYZE_DONE;
            msg.arg1 = (int)diff;
            mHandler.sendMessage(msg);
        }
    }

    private void handleInitTask() {
        long time = System.currentTimeMillis();
        boolean isCopyed = Utils.copyData(this);
        LogUtil.i(TAG, "init isCopyed:" + isCopyed);
        if (isCopyed) {
            resetConfig();
            long diff = System.currentTimeMillis() - time;
            Message msg = mHandler.obtainMessage();
            msg.what = CONFIG_COPY_DONE;
            msg.arg1 = (int)diff;
            mHandler.sendMessage(msg);
        }
        LogUtil.i(TAG, "init end:" + System.currentTimeMillis());
        Nlp.analyze("init");
    }

    private void resetConfig() {
        Nlp.currentDirectory = "/data/data/" + this.getPackageName() + "/files/config/";
        Nlp.wordSegmentor = new WordSegmentor(Nlp.currentDirectory, Nlp.preprocessFile, Nlp.wordSegmentorFile);
        Nlp.nerRecognizer = new NamedIdentityRecognizer(Nlp.currentDirectory, Nlp.preprocessFile, Nlp.nerRecogizerFile);
        Nlp.posTagger = new PosTagger(Nlp.currentDirectory, Nlp.preprocessFile, Nlp.posTaggerFile);
        Nlp.preprocess = new ConvolutionalSentenceModelPreprocess(Nlp.currentDirectory, Nlp.preprocessFile, Nlp.posTaggerFile);
        Nlp.decoder = new ConvolutionalSentenceModelDecoder(Nlp.currentDirectory, Nlp.inputNetworkSettingFile);
        Nlp.decoder.readPara();
        Nlp.multiSemanticAnalyzer = new CRFSemanticAnalyzerMultipleEvent(Nlp.currentDirectory, Nlp.preprocessFile, Nlp.posTaggerFile, Nlp.crf_multi_sentenceModelSettingFile, Nlp.crf_multi_confFile);
        Nlp.multiWrapper = new EventWrapper(Nlp.currentDirectory, Nlp.crf_multi_eventKeywordFile, Nlp.crf_multi_attributeKeywordFile);
    }

    public static void setHandler(Handler handler) {
        mHandler = handler;
    }
}
