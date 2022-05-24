package com.example.randomapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class AsyncCommitsData extends AsyncTask<String, Void, String[]> {
    int commitNumber = 15;
    String[] result = new String[commitNumber];
    MyAdapter adapter;


    AsyncCommitsData(MyAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                for (int i = 0; i < commitNumber; i++) {
                    try {
                        url = new URL(strings[0]);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        try {
                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            String s = readStream(in);
                            result[i] = s;
                        } finally {
                            urlConnection.disconnect();
                        }

                        } catch(MalformedURLException e){
                                e.printStackTrace();
                        } catch(IOException e){
                            e.printStackTrace();
                         }
                     }

            }
        });
        connection.start();
        try {
            connection.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String[] string) {
        super.onPostExecute(string);
        for (int i = 0; i < commitNumber; i++) {
            adapter.add("Commit message "+ (i+1) + " :\n" + string[i]);
            Log.i("JFL", "Adding commit message : " + string[i]);
            adapter.notifyDataSetChanged();
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
