package com.conferencebookingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.conferencebookingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class Booking extends AppCompatActivity {

    ScrollView scrollView, scrollView1;
    TableLayout tableLayout;
    TableRow tableRow, tableRow1;
    LinearLayout linearLayoutH, linearLayoutH1, linearLayoutV, linearLayoutV1, linearLayoutV2;
    ImageView imageView;
    TextView textViewTime1, textViewTime2, textViewTime3, textViewPrice1, textViewPrice2, textViewPrice3, textViewDescription;
    Button buttonBook;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;


    String jsonSearchParam;
    AsyncTask<String, Void, String> asyncSearchAPI;
    int priceAM, pricePM, conferenceRoomId, conferenceRoomAvailabilityId, priceFull, block;
    String conferenceRoomName, conferenceRoomDescription, seat_name, hoursAvailableFrom, hoursAvailableTo, imageURL,
            preNoonAvailabilityHourStart, preNoonAvailabilityHourEnd, afterNoonAvailabilityHourStart, afterNoonAvailabilityHourEnd;

    private JSONArray seats, images, roomIDs, rooms;
    private JSONObject jsonObject, room, image, seat;

    int aaa=0;

    ArrayList<JSONObject> seatList = new ArrayList<>();
    ArrayList<JSONObject> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        scrollView = findViewById(R.id.scrollviewBooking);
        tableLayout = findViewById(R.id.tableLayoutBooking);

        Intent in = getIntent();
        jsonSearchParam = in.getStringExtra("searchParam");

        asyncSearchAPI = new RestConnectionSearch();
        asyncSearchAPI.execute("https://dev-be.timetomeet.se/service/rest/conferenceroomavailability/search/",jsonSearchParam);


    }

    private class RestConnectionSearch extends AsyncTask<String, Void, String> {
        private String responseContent;
        private String Error = null;
        private URL url;
        int a1=0, a2=0, a3=0;
//        HashMap<Integer, String> imageList = new HashMap<>();
//        HashMap<Integer, String> seatList = new HashMap<>();

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Booking.this,"List of available rooms..",Toast.LENGTH_SHORT).show();

        }

        protected String doInBackground(String... requestData) {
            BufferedReader reader=null;

            try {
                url = new URL(requestData[0]);  //https://dev-be.timetomeet.se/service/rest/conferenceroomavailability/search/

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true); // True för POST, PUT. False för GET
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(requestData[1]); // { "username": ....
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null) {
                    sb.append(line + "  \n");
                }

                responseContent = sb.toString();
                jsonObject = new JSONObject(responseContent);
            }
            catch(Exception ex) {
                Error = ex.getMessage();
                System.out.println("The error message is: " + Error);
            }
            finally {
                try {
                    reader.close();
                }
                catch(Exception ex) {

                }
            }

            return responseContent;
        }

        protected void onPostExecute(final String result) {
            try {
                rooms = jsonObject.getJSONArray("rooms");

                for(int i = 0; i< rooms.length(); i++) {
                    room = rooms.getJSONObject(i);
                    System.out.println("The number of rooms are: " + rooms.length());

                    conferenceRoomId = room.getInt("conferenceRoomId");
                    conferenceRoomName = room.getString("conferenceRoomName");
                    conferenceRoomDescription = room.getString("description");
                    
                    priceAM = room.getInt("priceAM");
                    pricePM = room.getInt("pricePM");
                    priceFull = room.getInt("priceFull");

                    preNoonAvailabilityHourStart = room.getString("preNoonAvailabilityHourStart");
                    preNoonAvailabilityHourEnd = room.getString("preNoonAvailabilityHourEnd");
                    afterNoonAvailabilityHourStart = room.getString("afterNoonAvailabilityHourStart");
                    afterNoonAvailabilityHourEnd = room.getString("afterNoonAvailabilityHourEnd");

                    images = room.getJSONArray("images");
                        System.out.println("The number of images are: " + images.length());
                    seats = room.getJSONArray("seats");
                        System.out.println("The number of seats are: " + seats.length());

                    for(int i1=0;i1<images.length();i1++) {
                        imageList.add(images.getJSONObject(i1));
                    }

                    for(int i2=0; i2<seats.length(); i2++){
                        seatList.add(seats.getJSONObject(i2));
                    }

                    addElements();

                }

            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void addElements() throws JSONException, InterruptedException {
        //tableLayout.removeAllViews();
        aaa++;
        String https = "https:";
        System.out.println("AddElements being called for: " + aaa);

            tableRow = new TableRow(getBaseContext());
            tableRow.setBackgroundResource(R.drawable.table_divider);

            tableRow1 = new TableRow(getBaseContext());
            tableRow1.setLayoutParams(new ScrollView.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow1.setBackgroundResource(R.drawable.table_divider);


            imageView = new ImageView(getBaseContext());
//            imageView.setMaxWidth(20);
//            imageView.setMaxHeight(20);

//            for(int i1=0; i1<imageList.size(); i1++) {
//                System.out.println("The image path is: " + imageList.get(i1).getString("image"));
//                //Picasso.get().load(https.concat(imageList.get(i1).getString("image"))).into(imageView);
//                //Thread.sleep(500);
//            }


        // adding custom font
        Typeface monterrat = ResourcesCompat.getFont(getApplicationContext(),R.font.montserrat);
        Typeface monterratBold = ResourcesCompat.getFont(getApplicationContext(),R.font.montserratbold);

            linearLayoutH = new LinearLayout(getBaseContext());
            linearLayoutH.setOrientation(LinearLayout.HORIZONTAL);

            linearLayoutH1 = new LinearLayout(getBaseContext());
            linearLayoutH1.setOrientation(LinearLayout.HORIZONTAL);

            linearLayoutV = new LinearLayout(getBaseContext());
            linearLayoutV.setOrientation(LinearLayout.VERTICAL);
            linearLayoutV.setLayoutParams(new ScrollView.LayoutParams(400, TableLayout.LayoutParams.WRAP_CONTENT));

            linearLayoutV1 = new LinearLayout(getBaseContext());
            linearLayoutV1.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV2 = new LinearLayout(getBaseContext());
        linearLayoutV2.setOrientation(LinearLayout.VERTICAL);
        linearLayoutV2.setLayoutParams(new ScrollView.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            buttonBook = new Button(getBaseContext());
            buttonBook.setText("Book");
            buttonBook.getBackground().setColorFilter(0xE65BD744, PorterDuff.Mode.MULTIPLY);
            buttonBook.setWidth(60);
            buttonBook.setHeight(40);
            buttonBook.setTextSize(10);
            buttonBook.setTextColor(Color.BLACK);
            buttonBook.setTypeface(monterratBold);
            buttonBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(Booking.this, Choice.class));

                }
            });

            scrollView1 = new ScrollView(getBaseContext());
            textViewDescription = new TextView(getBaseContext());

            textViewTime1 = new TextView(getBaseContext());
            textViewTime1.setTypeface(monterrat);
            textViewTime2 = new TextView(getBaseContext());
            textViewTime2.setTypeface(monterrat);
            textViewTime3 = new TextView(getBaseContext());
            textViewTime3.setTypeface(monterrat);
            textViewTime3.setText("HELDAG");

            textViewDescription.setText(conferenceRoomDescription);
            textViewDescription.setTypeface(monterrat);

            radioGroup = new RadioGroup(getBaseContext());
            radioGroup.setLayoutParams(new ScrollView.LayoutParams(400, TableLayout.LayoutParams.WRAP_CONTENT));

            radioButton1 = new RadioButton(getBaseContext());
                radioButton1.setText(priceAM + " kr");
                radioButton1.setTypeface(monterrat);
                radioButton1.setPadding(0,0,0,38);
                radioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(priceAM + " kr");
                    }
                });
            radioButton2 = new RadioButton(getBaseContext());
                radioButton2.setText(pricePM + " kr");
                radioButton2.setTypeface(monterrat);
                radioButton2.setPadding(0,0,0,38);
                radioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(pricePM + " kr");
                    }
                });
            radioButton3 = new RadioButton(getBaseContext());
                radioButton3.setText(priceFull + " kr");
                radioButton3.setTypeface(monterrat);
                radioButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(priceFull + " kr");
                    }
                });

            textViewPrice1 = new TextView(getBaseContext());
            textViewPrice1.setTypeface(monterrat);
            textViewPrice2 = new TextView(getBaseContext());
            textViewPrice2.setTypeface(monterrat);
            textViewPrice3 = new TextView(getBaseContext());
            textViewPrice3.setTypeface(monterrat);

            tableLayout.addView(linearLayoutV2);
            linearLayoutV2.addView(tableRow1);
        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

        int leftMargin=2;
        int topMargin=30;
        int rightMargin=2;
        int bottomMargin=2;

        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        tableRow1.setLayoutParams(tableRowParams);
            tableRow1.addView(scrollView1);
            scrollView1.addView(textViewDescription);

            tableLayout.addView(tableRow);
                tableRow.addView(linearLayoutH);
                    linearLayoutH.addView(imageView);

                    linearLayoutH.addView(linearLayoutH1);
                        linearLayoutH1.addView(linearLayoutV);
                            linearLayoutV.addView(textViewTime1);

                                linearLayoutV.removeView(textViewTime2);
                            linearLayoutV.addView(textViewTime2);

                                linearLayoutV.removeView(textViewTime3);
                            linearLayoutV.addView(textViewTime3);

                        linearLayoutH1.addView(linearLayoutV1);
                            linearLayoutV1.addView(radioGroup);

                        linearLayoutH.removeView(buttonBook);
                    linearLayoutH.addView(buttonBook);

        textViewTime1.setText("FÖRMIDDAG \n" + preNoonAvailabilityHourStart +" - " + preNoonAvailabilityHourEnd);
        textViewTime2.setText("EFTERMIDDAG \n" + afterNoonAvailabilityHourStart +" - " + afterNoonAvailabilityHourEnd);

        radioGroup.addView(radioButton1);
        radioGroup.addView(radioButton2);
        radioGroup.addView(radioButton3);

        // room id och tid
        //Picasso.get().load("https://dev-be.timetomeet.se/static/crb/media/20190118/DeathtoStock_TheCollaborative-8.jpg").into(imageView);

    }

}

