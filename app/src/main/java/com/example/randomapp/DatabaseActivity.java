package com.example.randomapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Vector;

public class DatabaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        MetaphorsDatabase database = new MetaphorsDatabase(getApplicationContext());

        MyAdapter adapter = new MyAdapter();
        ListView databaselistview = findViewById(R.id.databaselistview);
        databaselistview.setAdapter(adapter);
        Vector<String[]> metaphors = database.readData();

        for (int i = 0; i < metaphors.size(); i++) {
            adapter.add("Métaphore "+(metaphors.get(i)[0])+ " : \n" + metaphors.get(i)[1]);
            adapter.notifyDataSetChanged();
        }

        Button deleteRow = findViewById(R.id.deleteRow);
        deleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText rowNumberInput = findViewById(R.id.numberDelete);
                String rowNumber = rowNumberInput.getText().toString();
                if (! rowNumber.equals("")) {
                    database.deleteData(rowNumber);
                    Toast.makeText(getApplicationContext(), "Métaphore "+ rowNumber + " supprimée !", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        Button clearAll = findViewById(R.id.clearDatabase);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.clearAll();
                Toast.makeText(getApplicationContext(), "Données supprimées !", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
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
