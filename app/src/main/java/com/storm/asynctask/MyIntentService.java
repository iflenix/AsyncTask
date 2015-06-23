package com.storm.asynctask;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("name");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int timeToWait = intent.getIntExtra("time", 0);
        try {
            TimeUnit.SECONDS.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("TAG!", " Intent: " + intent.getStringExtra("label"));

        Intent backIntent = new Intent("com.storm.myintentservice.callback");
        backIntent.putExtra("finished_intent", intent.getStringExtra("label"));
        sendBroadcast(backIntent);


    }
}
