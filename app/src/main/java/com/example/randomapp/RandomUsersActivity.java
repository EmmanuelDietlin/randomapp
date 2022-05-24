package com.example.randomapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RandomUsersActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Button generate = findViewById(R.id.generateUsers);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = findViewById(R.id.listview);
                UsersAdapter adapter = new UsersAdapter();
                listView.setAdapter(adapter);
                AsyncUsersJSONData randomUsers = new AsyncUsersJSONData(adapter);
                randomUsers.execute("https://randomuser.me/api/?results=10");
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
