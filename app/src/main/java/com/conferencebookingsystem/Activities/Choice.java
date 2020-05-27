package com.conferencebookingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.conferencebookingsystem.R;

public class Choice extends AppCompatActivity {

    ScrollView scrollViewChoice;
    LinearLayout linearLayoutChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Choice.this, Login.class));
        //normal back button: super.onBackPressed();
    }


}
