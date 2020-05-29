package com.conferencebookingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.conferencebookingsystem.R;

import org.json.JSONException;

public class Choice extends AppCompatActivity {

    ScrollView scrollViewChoice;
    LinearLayout linearLayoutChoice, linearLayoutV1, linearLayoutV2, linearLayoutH1, linearLayoutH2;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    CheckBox boxCheck1, boxCheck2, boxCheck3, boxCheck4, boxCheck5, boxCheck6, boxCheck7;
    EditText editText;
    TextView furniture, foodAndDrink, equipment, wishes, header, adress, dateAndTime;
    Button continueBooking;
    TableLayout tableLayout;
    TableRow tableRow1, tableRow2, tableRow3, tableRow4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        
    }

    public void addChoices() throws JSONException, InterruptedException {
        //tableLayout.removeAllViews();

        scrollViewChoice = new ScrollView(getBaseContext());

        linearLayoutChoice = new LinearLayout(getBaseContext());
        linearLayoutChoice.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV1 = new LinearLayout(getBaseContext());
        linearLayoutV1.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV2 = new LinearLayout(getBaseContext());
        linearLayoutV2.setOrientation(LinearLayout.VERTICAL);

        linearLayoutH1 = new LinearLayout(getBaseContext());
        linearLayoutH1.setOrientation(LinearLayout.HORIZONTAL);

        linearLayoutH2 = new LinearLayout(getBaseContext());
        linearLayoutH2.setOrientation(LinearLayout.HORIZONTAL);

        tableRow1 = new TableRow(getBaseContext());
        tableRow2 = new TableRow(getBaseContext());
        tableRow3 = new TableRow(getBaseContext());
        tableRow4 = new TableRow(getBaseContext());

        radioGroup = new RadioGroup(getBaseContext());

        radioButton1 = new RadioButton(getBaseContext());
        radioButton2 = new RadioButton(getBaseContext());
        radioButton3 = new RadioButton(getBaseContext());

        checkBox1 = new CheckBox(getBaseContext());
        checkBox2 = new CheckBox(getBaseContext());
        checkBox3 = new CheckBox(getBaseContext());
        checkBox4 = new CheckBox(getBaseContext());
        checkBox5 = new CheckBox(getBaseContext());
        checkBox6 = new CheckBox(getBaseContext());
        checkBox7 = new CheckBox(getBaseContext());

        boxCheck1 = new CheckBox(getBaseContext());
        boxCheck2 = new CheckBox(getBaseContext());
        boxCheck3 = new CheckBox(getBaseContext());
        boxCheck4 = new CheckBox(getBaseContext());
        boxCheck5 = new CheckBox(getBaseContext());
        boxCheck6 = new CheckBox(getBaseContext());
        boxCheck7 = new CheckBox(getBaseContext());

        editText = new EditText(getBaseContext());

        furniture = new TextView(getBaseContext());
            furniture.setText("Furnitures");
        foodAndDrink = new TextView(getBaseContext());
            foodAndDrink.setText("Food and Drinks");
        equipment = new TextView(getBaseContext());
            equipment.setText("Equipments");
        wishes = new TextView(getBaseContext());
            wishes.setText("Further wishes");

        header = new TextView(getBaseContext());
        adress = new TextView(getBaseContext());
        dateAndTime = new TextView(getBaseContext());

        continueBooking = new Button(getBaseContext());

        tableLayout = new TableLayout(getBaseContext());

// Layout starts here:
        tableLayout.addView(linearLayoutChoice);
            linearLayoutChoice.addView(furniture);
            linearLayoutChoice.addView(tableRow1);
                tableRow1.addView(radioGroup);
                    radioGroup.addView(radioButton1);
                    radioGroup.addView(radioButton2);
                    radioGroup.addView(radioButton3);

            linearLayoutChoice.addView(equipment);
            linearLayoutChoice.addView(tableRow2);

            // for loop här
                tableRow2.addView(checkBox1);
                tableRow2.addView(checkBox2);
                tableRow2.addView(checkBox3);
                tableRow2.addView(checkBox4);
                tableRow2.addView(checkBox5);
                tableRow2.addView(checkBox6);
                tableRow2.addView(checkBox7);

            linearLayoutChoice.addView(foodAndDrink);
            linearLayoutChoice.addView(tableRow3);

            // for loop här
                tableRow3.addView(boxCheck1);
                tableRow3.addView(boxCheck2);
                tableRow3.addView(boxCheck3);
                tableRow3.addView(boxCheck4);
                tableRow3.addView(boxCheck5);
                tableRow3.addView(boxCheck6);
                tableRow3.addView(boxCheck7);

            linearLayoutChoice.addView(wishes);
            linearLayoutChoice.addView(tableRow4);
                tableRow4.addView(editText);
                tableRow4.addView(continueBooking);


    }


}
