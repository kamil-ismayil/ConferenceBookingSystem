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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.conferencebookingsystem.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class Booking extends AppCompatActivity {

    Button button;
    TableLayout tableLayout;
    ScrollView scrollView;
    TableRow tableRow;
    ImageView imageView;
    TextView roomInfo, plantInfo;
    LinearLayout linearLayoutH, linearLayoutV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        protected void onPostExecute(final String result) {
            tableLayout.removeAllViews();
            /*Adding dynamic view to the app*/

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

                for(int i = 0; i< plantsOverview.length(); i++){
                    tableRow = new TableRow(getBaseContext());
                    linearLayoutH = new LinearLayout(getBaseContext());
                    linearLayoutH.setOrientation(LinearLayout.HORIZONTAL);

                    linearLayoutV = new LinearLayout(getBaseContext());
                    linearLayoutV.setOrientation(LinearLayout.VERTICAL);

                    imageView = new ImageView(getBaseContext());
                    imageView.setMaxWidth(200);
                    imageView.setMaxHeight(200);

                    textViewPrice = new TextView(getBaseContext());
                    textViewPrice.setWidth(130);
                    textViewPrice.setHeight(40);
                    textViewPrice.setTextColor(Color.BLACK);
                    //textViewPrice.setText(Typeface.BOLD);

                    textViewDescription = new TextView(getBaseContext());
                    textViewDescription.setWidth(780);
                    textViewDescription.setHeight(300);
                    textViewDescription.setTextColor(Color.BLACK);

                    buttonViewPlant = new Button(getBaseContext());
                    buttonViewPlant.getBackground().setColorFilter(0xE65BD744, PorterDuff.Mode.MULTIPLY);
                    buttonViewPlant.setWidth(80);
                    buttonViewPlant.setHeight(40);
                    buttonViewPlant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Search.this, Booking.class));
                        }
                    });

                    // Ändrar storkejen på knapparna av någon anledning
                    // buttonViewPlant.setBackgroundColor(getResources().getColor(R.color.Green));

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

                    tableRow.addView(linearLayoutH);
                    linearLayoutH.addView(imageView);
                    a1++;
                    if(a1== 1){
                        linearLayoutH.removeView(scrollView);
                    }
                    linearLayoutH.addView(scrollView);
                    scrollView.addView(textViewDescription);
                    textViewDescription.setText(listPlant.get(i).getString("plantFacts")
                            + "\n" + addressPlant.get(i).getString("street") +
                            ", " + addressPlant.get(i).getString("zip") + " "
                            +  addressPlant.get(i).getString("city")
                    );
                    linearLayoutH.addView(linearLayoutV);
                    linearLayoutV.addView(textViewPrice);
                    textViewPrice.setText("Price: " + listPlant.get(i).getString("priceFrom"));

                    a2++;
                    if(a2==1){
                        linearLayoutV.removeView(buttonViewPlant);
                    }
                    linearLayoutV.addView(buttonViewPlant);
                    buttonViewPlant.setText("Book");
                    a1=0; a2=0;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
