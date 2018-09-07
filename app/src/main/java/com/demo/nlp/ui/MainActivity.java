package com.demo.nlp.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.nlp.R;
import com.demo.nlp.service.MyIntentService;
import com.demo.nlp.util.LogUtil;
import com.gome.nlp.Nlp;

import cn.edu.fudan.flow.NamedIdentityRecognizer;
import cn.edu.fudan.flow.PosTagger;
import cn.edu.fudan.flow.WordSegmentor;
import cn.edu.fudan.sentence.ConvolutionalSentenceModelPreprocess;

import static com.gome.nlp.Nlp.currentDirectory;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mAnalyzeBt;

    private Button mClearBt;

    private EditText mOriTextEt;

    private TextView mResultTv;

    private boolean bIsAnalyzeCopyed = false;

    private boolean bIsAnalyzeClicked = false;

    private static final int CONFIG_COPY_DONE = 0;

    private static final int ANALYZE_DONE = 2;

    private int mInitTime;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONFIG_COPY_DONE:
                    bIsAnalyzeCopyed = true;
                    mInitTime = msg.arg1;
                    LogUtil.i(TAG, "copy done and bIsAnalyzeClicked==" + bIsAnalyzeClicked);
                    if (bIsAnalyzeClicked) {
                        analyze("analyze");
                    }
                    break;
                case ANALYZE_DONE:
                    LogUtil.i(TAG, "analyze end==" + System.currentTimeMillis());
                    if (null != msg.obj) {
                        String result = (String) msg.obj;
                        int time = msg.arg1;
                        mResultTv.setText("init time:" + mInitTime + "ms\n" + "analyze time:" + time + "ms\n\n" + result);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();
        initConfig();
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = activityManager.getMemoryClass();
        LogUtil.i(TAG, "heap:" + memClass);
    }

    private void initView() {
        mAnalyzeBt = findViewById(R.id.ansj_seg);
        mAnalyzeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analyzeOnClick();
            }
        });
        mClearBt = findViewById(R.id.clear);
        mClearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearOnclick();
            }
        });
        mOriTextEt = findViewById(R.id.ori_sentence);
        mResultTv = findViewById(R.id.seg_result);
    }

    private void analyzeOnClick() {
        LogUtil.i(TAG, "analyze onclick and bIsAnalyzeCopyed==" + bIsAnalyzeCopyed + " bIsAnalyzeClicked==" + bIsAnalyzeClicked);
        if (bIsAnalyzeCopyed) {
            analyze("analyze");
            bIsAnalyzeClicked = false;
        } else {
            bIsAnalyzeClicked = true;
        }
    }

    private void clearOnclick() {
        MyIntentService.sb = new StringBuilder();
        mResultTv.setText("");
        mOriTextEt.setText("");
    }

    private void initConfig() {
        LogUtil.i(TAG, "init config:" + System.currentTimeMillis());
        MyIntentService.setHandler(mHandler);
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("task", "init");
        startService(intent);
    }

    private void analyze(String task) {
        LogUtil.i(TAG, "analyze begin==" + System.currentTimeMillis());
        String oriText = mOriTextEt.getText().toString();
//        String path = "/data/data/" + this.getPackageName() + "/files/";
        LogUtil.i(TAG, "OnClick oriTextEt: " + "\n" + oriText);
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("oriText", oriText);
//        intent.putExtra("path", path);
        intent.putExtra("task", task);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Nlp.wordSegmentor = null;
        Nlp.nerRecognizer = null;
        Nlp.posTagger = null;
        Nlp.preprocess = null;
        Nlp.decoder = null;
    }

    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS}, 1);
//        }
    }
}
