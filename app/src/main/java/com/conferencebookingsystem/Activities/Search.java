package com.conferencebookingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.conferencebookingsystem.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Search extends AppCompatActivity {

    Button buttonSearch;
    TableLayout tableLayout;
    ScrollView scrollView;
    TableRow tableRow;
    ImageView imageView;
    TextView textViewDescription, textViewPrice;
    Button buttonViewPlant;
    LinearLayout linearLayoutH, linearLayoutV;

    private static final String tag = "Search";
    private TextView Date;
    private DatePickerDialog.OnDateSetListener DateListener;
    AsyncTask<String, Void, String> asyncSearchAPI;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tableLayout = findViewById(R.id.tableLayout);
        scrollView = findViewById(R.id.scrollView);
        buttonSearch = findViewById(R.id.SearchButton);
        Date = (TextView) findViewById(R.id.Date);
        asyncSearchAPI = new RestConnectionSearch();
        requestQueue = Volley.newRequestQueue(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncSearchAPI.execute("https://dev-be.timetomeet.se/service/rest/conferenceroomavailability/search/",jsonSearchParam());


            }
        });


        // Nusret
        // https://www.youtube.com/watch?v=hwe1abDO2Ag
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

        // Nusret
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

    private String jsonSearchParam(){

        String jsonSearchParam = "{" +
                "    \"cityId\": 1," +
                "    \"seats\": 5," +
                "    \"priceFrom\": 10," +
                //"    \"plantId\": 1," +
                "    \"dateTimeFrom\": \"2020-05-26T09:00:00+02:00\"," +
                "    \"dateTimeTo\": \"2020-05-26T12:00:00+02:00\"," +
                "    \"page\": 1" +
                "}";
        return jsonSearchParam;
    }

        private class RestConnectionSearch extends AsyncTask<String, Void, String> {
        //private HttpURLConnection Client ;
        private String responseContent;
        private String Error = null;
        private URL url;
        int a1=0, a2=0, a3=0;
        String plantName, plantFacts;
        int priceFrom;

        private JSONObject visitingAddress;
        private JSONArray seats, plantImages, plantIDs, plantsOverview;
        private JSONObject jsonObject, plant;

        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Search.this,"Searching for rooms..",Toast.LENGTH_SHORT).show();
        }

        protected String doInBackground(String... requestData) {
            BufferedReader reader=null;

            try {
                url = new URL(requestData[0]);  //https://dev-be.timetomeet.se/service/rest/conferenceroomavailability/search/

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept","application/json");
                //connection.setRequestProperty("Authorization","Token de15b469e3fc7f654700fdbb1bca7778eb113f5a");
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
                int code = connection.getResponseCode();

                responseContent = sb.toString();
                jsonObject = new JSONObject(responseContent);
                //System.out.println("Response content: " + responseContent + "\nResponse code: " + code);

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
                    textViewPrice.setWidth(100);
                    textViewPrice.setHeight(40);
                    textViewPrice.setTextColor(Color.BLACK);
                    //textViewPrice.setText(Typeface.BOLD);

                    textViewDescription = new TextView(getBaseContext());
                    textViewDescription.setWidth(800);
                    textViewDescription.setHeight(300);
                    textViewDescription.setTextColor(Color.BLACK);

                    buttonViewPlant = new Button(getBaseContext());
                    buttonViewPlant.setWidth(80);
                    buttonViewPlant.setHeight(40);

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
                            textViewPrice.setText("Price from" + listPlant.get(i).getString("priceFrom"));

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
