package com.example.randomapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {
        Vector<String> vector = new Vector<String>();


        public void add(String user) {
            vector.add(user);
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewlayout, parent, false);
            }
            TextView text = convertView.findViewById(R.id.text);
            text.setText(vector.get(position));
            return text;
        }
    }

