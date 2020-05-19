package com.conferencebookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button start = (Button)findViewById(R.id.btnLogin);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Search.class));
            }
        });

        //Christian har skrivit den här metoden
        txtCreateAccount = findViewById(R.id.txtCreateAccount);
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, CreateAccount.class));

            }
        });
    }

    // Trycker ner programmet istället för att gå till splashscreen
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //super.onBackPressed();
    }



}
