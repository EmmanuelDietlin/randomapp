package com.example.randomapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.net.MalformedURLException;
import java.net.URL;

public class RandomPicturesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        Button generateImages = findViewById(R.id.imageGenerateButton);
        generateImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetImages();

            }
        });

        Button mainMenu = findViewById(R.id.mainMenuButton);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MAIN = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MAIN);
            }
        });

        Button save = findViewById(R.id.savePictures);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImagesInGallery();
                Toast.makeText(getApplicationContext(), "Image(s) sauvegardée(s) avec succès ! ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void SetImages() {
        ImageView robot = findViewById(R.id.robot);
        ImageView monster = findViewById(R.id.monster);
        ImageView robotHead = findViewById(R.id.robotHead);
        ImageView cat = findViewById(R.id.cat);
        TextInputEditText textInput = findViewById(R.id.textImageGeneration);
        String text = textInput.getText().toString();
        if (text.equals("")) {
            text = "default";
        }
        String url = "https://robohash.org/"+text;
        LoadImages(robot, url);
        LoadImages(monster, url+"?set=set2");
        LoadImages(robotHead, url+"?set=set3");
        LoadImages(cat, url+"?set=set4");

    }

    private void LoadImages(ImageView imageView, String url) {
        RequestQueue queue = MySingleton.getInstance(getApplicationContext()).
                getRequestQueue();
        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
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
    }

    private void SaveImagesInGallery() {
        CheckBox check1 = findViewById(R.id.checkBox1);
        CheckBox check2 = findViewById(R.id.checkBox2);
        CheckBox check3 = findViewById(R.id.checkBox3);
        CheckBox check4 = findViewById(R.id.checkBox4);
        ImageView robot = findViewById(R.id.robot);
        ImageView monster = findViewById(R.id.monster);
        ImageView robotHead = findViewById(R.id.robotHead);
        ImageView cat = findViewById(R.id.cat);
        if (check1.isChecked()) {
            MediaStore.Images.Media.insertImage(getContentResolver(), ((BitmapDrawable)robot.getDrawable()).getBitmap(), "robot", "robot");
            check1.toggle();
        }
        if (check2.isChecked()) {
            MediaStore.Images.Media.insertImage(getContentResolver(), ((BitmapDrawable)monster.getDrawable()).getBitmap(), "robot", "robot");
            check2.toggle();
        }
        if (check3.isChecked()) {
            MediaStore.Images.Media.insertImage(getContentResolver(), ((BitmapDrawable)robotHead.getDrawable()).getBitmap(), "robot", "robot");
            check3.toggle();
        }
        if (check4.isChecked()) {
            MediaStore.Images.Media.insertImage(getContentResolver(), ((BitmapDrawable)cat.getDrawable()).getBitmap(), "robot", "robot");
            check4.toggle();
        }

    }
}
