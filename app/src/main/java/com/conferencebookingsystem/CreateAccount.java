package com.conferencebookingsystem;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.security.SecureRandom;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        EditText passwordField = findViewById(R.id.txtPassword);
        passwordField.setText(generateToken(8));

    }

    public String generateToken(int byteLength){

        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);

        return new BigInteger(1,token).toString(16);
    }
}
