package com.conferencebookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Search extends AppCompatActivity {

    private static final String tag = "Search";

    private TextView Date;
    private DatePickerDialog.OnDateSetListener DateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Date = (TextView) findViewById(R.id.Date);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DatePicker = new DatePickerDialog(
                        Search.this,
                        android.R.style.Theme_Holo_Light_Dialog, DateListener, year, month, day);
                DatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                DatePicker.show();

            }
        });

        DateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String selectedDate = dayOfMonth + "/" + month + "/" + year;
                Date.setText(selectedDate);
            }
        };

        // https://android--code.blogspot.com/2015/08/android-spinner-hint.html
        final Spinner spinner = (Spinner) findViewById(R.id.CitySearch);

        String[] Cities = new String[]{
                "City",
                "Göteborg",
                "Stockholm",
                "Falkenberg",
                "Lidingö",
                "Malmö",
                "Marstrand",
                "Mölndal",
                "Nynäshamn",
                "Solna",
                "Sundbyberg",
                "Uppsala",
                "Vaxholm",
                "Västerås",
                "Växjö",
                "Ystad",
                "Hägersten",
                "Stockholm-Globen",
                "Johanneshov",
                "Bromma",
                "Kista",
                "Skärholmen",
                "Årsta",
                "Danderyd",
                "Hovås",
                "Hisinge Backa",
                "Styrsö",
                "Västra Frölunda",
                "Mölnlycke"
        };

        final List<String> cityList = new ArrayList<>(Arrays.asList(Cities));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String> (
                this,R.layout.support_simple_spinner_dropdown_item,cityList) {
        @Override
        public boolean isEnabled(int position){
            if(position == 0)
            {
                // Disable the first item from Spinner
                // First item will be use for hint
                return false;
            }
            else
            {
                return true;
            }
        }
        @Override
        public View getDropDownView(int position, View convertView,
                ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if(position == 0){
                // Set the hint text color gray
                tv.setTextColor(Color.GRAY);
            }
            else {
                tv.setTextColor(Color.BLACK);
            }
            return view;
        }
    };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItemText = (String) parent.getItemAtPosition(position);
            // If user change the default selection
            // First item is disable and it is used for hint
            if(position > 0){
                // Notify the selected item text
                Toast.makeText
                        (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


    }
}
