package com.example.randomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    MetaphorsDatabase metaphor_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickListeners();
        metaphor_db = new MetaphorsDatabase(getApplicationContext());
        SetSeekbarListener();



    }

    private void SetSeekbarListener() {
        SeekBar lenghtBar = findViewById(R.id.lenghtbar);
        TextView lenghtvalue = findViewById(R.id.lenghtvalue);
        lenghtBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lenghtvalue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void setOnClickListeners() {
        Button metaphore = findViewById(R.id.generateMetaphore);
        Button saveMetaphore = findViewById(R.id.saveButton);
        Button shareMetaphore = findViewById(R.id.shareButton);
        Button randomUsers = findViewById(R.id.randomUsersButton);
        Button randomImages = findViewById(R.id.randomPicturesButton);
        Button randomCommits = findViewById(R.id.randomCommitButton);
        Button savedMetaphores = findViewById(R.id.savedMetaphorsButton);
        TextView lenghtvalue = findViewById(R.id.lenghtvalue);

        metaphore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView metaphoreText = findViewById(R.id.metaphore);
                String text_lenght = lenghtvalue.getText().toString();
                AsyncMetaphoreData metaphoreData = new AsyncMetaphoreData(metaphoreText);
                metaphoreData.execute("http://metaphorpsum.com/sentences/"+text_lenght);
            }
        });

        shareMetaphore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView metaphor = findViewById(R.id.metaphore);
                String metaphorText = metaphor.getText().toString();
                Intent shareActivity = new Intent(getApplicationContext(), ShareActivity.class);
                shareActivity.putExtra("metaphor", metaphorText);
                startActivity(shareActivity);
            }
        });

        randomUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomUsersActivity = new Intent(getApplicationContext(), RandomUsersActivity.class);
                startActivity(randomUsersActivity);
            }
        });

        randomImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomPicturesActivity = new Intent(getApplicationContext(), RandomPicturesActivity.class);
                startActivity(randomPicturesActivity);
            }
        });

        randomCommits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent randomCommitsActivity = new Intent(getApplicationContext(), RandomCommitsActivity.class);
                startActivity(randomCommitsActivity);
            }
        });

        saveMetaphore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView metaphor = findViewById(R.id.metaphore);
                String metaphorText = metaphor.getText().toString();
                metaphor_db.insertData(metaphorText);
                Toast.makeText(getApplicationContext(), "Métaphore sauvegardée !", Toast.LENGTH_SHORT).show();
            }
        });

        savedMetaphores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent databaseActivity = new Intent(getApplicationContext(), DatabaseActivity.class);
                startActivity(databaseActivity);
            }
        });
    }
}