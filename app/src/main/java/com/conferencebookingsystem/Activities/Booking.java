package com.conferencebookingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.conferencebookingsystem.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class Booking extends AppCompatActivity {

    Button buttonViewBooking;
    TableLayout tableLayout;
    ScrollView scrollView;
    TableRow tableRow, tableRow1, tableRow2, tableRow3, tableRow4, tableRow5,tableRow6,tableRow7;
    ImageView imageView;
    TextView roomInfo, roomName, maxPeople, calendar;
    RadioGroup timeOfDay;
    RadioButton morningPrice, afternoonPrice, fullDayPrice;
    LinearLayout linearLayoutInnerV, linearLayoutV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


    }



         protected void onPostExecute(final String result) {
            tableLayout.removeAllViews();
            //Adding dynamic view to the app

            ArrayList<JSONObject> listPlant= new ArrayList<>();
            ArrayList<JSONObject> addressPlant= new ArrayList<>();

            try {
                plantIDs = jsonObject.getJSONArray("plants");
                plantsOverview = jsonObject.getJSONArray("plantsOverview");

                for(int i=0; i<plantsOverview.length(); i++){
                    plant = plantsOverview.getJSONObject(i);
                    listPlant.add(plant);
                    visitingAddress = (JSONObject) plant.get("visitingAddress");
                    addressPlant.add(visitingAddress);
                    plantFacts = visitingAddress.getString("street");
                    System.out.println("Address is: " + plantFacts);

//                  JSONObject jsonObject = (JSONObject) list.get(i).get("visitingAddress");
//                  System.out.println("Visiting address"+ i + jsonObject.getString("street"));

                }

                for(int i = 0; i< roomsOverview.length(); i++){
                    tableRow = new TableRow(getBaseContext());

                    linearLayoutV = new LinearLayout(getBaseContext());
                    linearLayoutV.setOrientation(LinearLayout.VERTICAL);

                    imageView = new ImageView(getBaseContext());

                    roomName = new TextView(getBaseContext());
                    roomName.setTextColor(Color.BLACK);
                    //textViewPrice.setText(Typeface.BOLD);

                    roomInfo = new TextView(getBaseContext());
                    roomInfo.setTextColor(Color.BLACK);

                    buttonViewBooking = new Button(getBaseContext());
                    buttonViewBooking.getBackground().setColorFilter(0xE65BD744, PorterDuff.Mode.MULTIPLY);
                    buttonViewBooking.setWidth(80);
                    buttonViewBooking.setHeight(40);
                    buttonViewBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Booking.this,  //tillval klassens namn här .class));
                        }
                    });

                    scrollView = new ScrollView(getBaseContext());


                    tableLayout.addView(tableRow);
                    TableLayout.LayoutParams tableRowParams=
                            new TableLayout.LayoutParams
                                    (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

                    int leftMargin=2;
                    int topMargin=20;
                    int rightMargin=2;
                    int bottomMargin=30;

                    tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                    tableRow.setLayoutParams(tableRowParams);

                    tableRow.addView(linearLayoutV);
                    linearLayoutV.addView(imageView);
                    int a1 = 0;
                    a1++;
                    if(a1== 1){
                        linearLayoutV.removeView(scrollView);
                    }
                    linearLayoutV.addView(scrollView);
                        scrollView.addView(linearLayoutInnerV);
                            linearLayoutInnerV.addView(tableRow1);
                                tableRow1.addView(imageView);
                            linearLayoutInnerV.addView(tableRow2);
                                tableRow2.addView(roomName);
                                    roomName.setText("");
                            linearLayoutInnerV.addView(tableRow3);
                                tableRow3.addView(maxPeople);
                                    maxPeople.setText("");
                                tableRow3.addView(imageView);
                            linearLayoutInnerV.addView(tableRow4);
                                tableRow4.addView(imageView);
                                tableRow4.addView(imageView);
                                tableRow4.addView(imageView);
                            linearLayoutInnerV.addView(tableRow5);
                                tableRow5.addView(roomInfo);
                                    roomInfo.setText("");
                            linearLayoutInnerV.addView(tableRow6);
                                tableRow6.addView(timeOfDay);
                                    timeOfDay.addView(morningPrice);
                                        morningPrice.setText("morning price: " + listPlant.get(i).getString("priceFrom"));
                                    timeOfDay.addView(afternoonPrice);
                                        afternoonPrice.setText("Afternoon price: " + listPlant.get(i).getString("priceFrom"));
                                    timeOfDay.addView(fullDayPrice);
                                        fullDayPrice.setText("Full day price: " + listPlant.get(i).getString("priceFrom"));
                            linearLayoutInnerV.addView(tableRow7);
                                tableRow7.addView(calendar);


                    int a2 = 0;
                    a2++;
                    if(a2==1){
                        tableRow7.removeView(buttonViewBooking);
                    }
                    tableRow7.addView(buttonViewBooking);
                    buttonViewBooking.setText("Book");
                    a1=0; a2=0;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

