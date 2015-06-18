package com.storm.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView tv;
    ProgressBar pb;
    MyAsync myAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar);
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
