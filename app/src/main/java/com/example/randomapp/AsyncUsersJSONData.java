package com.example.randomapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class AsyncUsersJSONData extends AsyncTask<String, Void, JSONObject> {
    JSONObject result;
    UsersAdapter adapter;

    AsyncUsersJSONData(UsersAdapter adapter) {
        this.adapter = adapter;
    }
    @Override
    protected JSONObject doInBackground(String... strings) {
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        result = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            JSONArray usersList = jsonObject.getJSONArray("results");
            for (int i = 0; i < usersList.length(); i++) {
                JSONObject json = usersList.getJSONObject(i);
                JSONObject userJson = json.getJSONObject("name");
                JSONObject imageJson = json.getJSONObject("picture");
                String imageUrl = imageJson.getString("large");
                String userText = userJson.getString("first") + " " + userJson.getString("last");
                adapter.add(userText, imageUrl);
                Log.i("JFL","adding user : " + userText);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
