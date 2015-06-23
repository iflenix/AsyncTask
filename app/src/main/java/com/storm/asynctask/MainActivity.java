package com.storm.asynctask;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    TextView tv;
    ProgressBar pb;
    MyAsync myAsync;

    private MyBroadcastReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        myReceiver = new MyBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter("com.storm.myintentservice.callback");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myReceiver,intentFilter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick (View v) {
        myAsync = new MyAsync();
        myAsync.execute("Hello");

    }
    public void onButton2Click (View v) {
        Intent intent1 = new Intent(this,MyIntentService.class);
        intent1.putExtra("label","Intent1");
        intent1.putExtra("time", 5);

        Intent intent2 = new Intent(this,MyIntentService.class);
        intent2.putExtra("label","Intent2");
        intent2.putExtra("time", 3);
        startService(intent1);
        startService(intent2);





    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String callbackService = intent.getStringExtra("finished_intent");
            Toast.makeText(getBaseContext(),callbackService,Toast.LENGTH_SHORT).show();

        }
    }





    class MyAsync extends AsyncTask <String, Integer, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("Started");
        }

        @Override
        protected Void doInBackground(String... params) {

            for(int i = 0; i< 100; i++){

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                }
                publishProgress(i);
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText("Finished");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText(String.valueOf(values[0]));
            pb.setProgress(values[0]);
        }
    }



}

