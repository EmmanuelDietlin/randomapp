package com.example.randomapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;

public class ShareActivity extends Activity {
    private static final int READ_CONTACTS_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Bundle extras = getIntent().getExtras();
        String metaphor = new String(extras.getString("metaphor"));
        askPermissions(100);

        Button shareName = findViewById(R.id.shareName);
        TextView plaintext = findViewById(R.id.plain_text);
        TextInputLayout shareNameInputLayout = findViewById(R.id.contactNameInputLayout);
        TextView explanation = findViewById(R.id.explanation);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            shareName.setEnabled(true);
            plaintext.setEnabled(true);
            shareNameInputLayout.setEnabled(true);
            explanation.setText("Lecture des contacts autorisée !");
        } else {
            shareName.setEnabled(false);
            plaintext.setEnabled(false);
            shareNameInputLayout.setEnabled(false);
            explanation.setText("Veuillez autoriser la lecture des contacts dans les autorisations de l'application");

        }


        TextView metaphorTextView = findViewById(R.id.metaphore);
        metaphorTextView.setText(metaphor);

        Button shareNumber = findViewById(R.id.shareNumber);
        shareNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText telNumberInput = findViewById(R.id.numberInput);
                String telNumberString = telNumberInput.getText().toString();
                Uri tel_number = Uri.parse("sms:" + telNumberString);
                Intent msg = new Intent(Intent.ACTION_SENDTO, tel_number);
                msg.putExtra("sms_body", metaphor);
                startActivity(msg);
                Toast.makeText(getApplicationContext(), "Envoi du message !", Toast.LENGTH_SHORT).show();
            }
        });


        shareName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(getApplicationContext(), "Veuillez autoriser la lecture des contacts", Toast.LENGTH_SHORT).show();
                    askPermissions(100);
                } else {
                    TextInputEditText nameInput = findViewById(R.id.contactName);
                    String contactName = nameInput.getText().toString();
                    String contactNumber = getContactNumber(contactName);
                    if (contactNumber == null) {
                        Toast.makeText(getApplicationContext(), "Ce contact n'existe pas", Toast.LENGTH_SHORT).show();
                    } else {
                        Uri tel_number = Uri.parse("sms:" + contactNumber);
                        Intent msg = new Intent(Intent.ACTION_SENDTO, tel_number);
                        msg.putExtra("sms_body", metaphor);
                        startActivity(msg);
                        Toast.makeText(getApplicationContext(), "Envoi du message !", Toast.LENGTH_SHORT).show();
                    }
                }
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


    private String getContactNumber(String name) {

        final String[] PROJECTION = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor != null) {
            HashSet<String> mobileNoSet = new HashSet<String>();
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                while (cursor.moveToNext()) {
                    if (cursor.getString(nameIndex).equals(name)) {
                        String number = cursor.getString(numberIndex);
                        return number;
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    private void askPermissions(int code) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ShareActivity.this, new String[] { Manifest.permission.READ_CONTACTS }, code);
        }
    }
}



