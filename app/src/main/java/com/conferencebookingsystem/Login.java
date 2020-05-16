package com.conferencebookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonCreateAccount = findViewById(R.id.btnCreateAccount);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, CreateAccount.class));
            }
        });


//        buttonCreateAccount.setOnClickListener(new View().OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Login.this, CreateAccount.class));
//            }
//        });

    }

    // Trycker ner programmet istället för att gå till splashscreen
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        //super.onBackPressed();
    }



}
