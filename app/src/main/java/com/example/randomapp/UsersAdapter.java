package com.example.randomapp;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class UsersAdapter extends BaseAdapter {
    Vector<String[]> vector = new Vector<String[]>();


    public void add(String user, String url) {
        String[] temp = {user, url};
        vector.add(temp);
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlayout, parent, false);
        }
        TextView text = convertView.findViewById(R.id.text);
        ImageView image = convertView.findViewById(R.id.image);
        text.setText(vector.get(position)[0]);
        RequestQueue queue = MySingleton.getInstance(parent.getContext()).
                getRequestQueue();
        ImageRequest imgRequest = new ImageRequest(vector.get(position)[1], new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                image.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JFL", "error");
                    }
                });
        queue.add(imgRequest);
        return convertView;
    }
}

