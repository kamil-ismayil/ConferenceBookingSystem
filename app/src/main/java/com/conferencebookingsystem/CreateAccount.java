package com.conferencebookingsystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;

public class CreateAccount extends AppCompatActivity {

    private Button buttonCreate;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        buttonCreate = findViewById(R.id.btnCreate);
        EditText passwordField = findViewById(R.id.txtPassword);
        passwordField.setText(generateToken(8));

        mQueue = Volley.newRequestQueue(this);



    }

    private void jsonParse(){



    }


    public String generateToken(int byteLength){

        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);

        return new BigInteger(1,token).toString(16);
    }

}
