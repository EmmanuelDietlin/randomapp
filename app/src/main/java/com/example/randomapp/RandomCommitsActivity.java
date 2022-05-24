package com.example.randomapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class RandomCommitsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);

        Button generate = findViewById(R.id.generateCommits);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = findViewById(R.id.commitslistview);
                MyAdapter adapter = new MyAdapter();
                listView.setAdapter(adapter);
                AsyncCommitsData commitsData = new AsyncCommitsData(adapter);
                commitsData.execute("http://whatthecommit.com/index.txt");
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
    }
}
