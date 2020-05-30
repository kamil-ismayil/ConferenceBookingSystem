package com.conferencebookingsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.conferencebookingsystem.API.FoodbeverageList;
import com.conferencebookingsystem.API.TechnologyList;
import com.conferencebookingsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Choice extends AppCompatActivity {

    RadioButton[] radioButtons;
    CheckBox[] checkBoxFoodbeverage = null, checkBoxTechnology = null;

    ScrollView scrollViewChoice;
    LinearLayout linearLayoutV1, linearLayoutV2, linearLayoutV3, linearLayoutH1;
    LinearLayout linearLayoutV4, linearLayoutV5, linearLayoutV6, linearLayoutH2;
    LinearLayout linearLayoutV7, linearLayoutV8, linearLayoutV9, linearLayoutH3;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TableLayout tableLayout;
    TableRow tableRow1, tableRow2, tableRow3;
    ArrayList<String> seatList;
    HashMap<Integer, String> listOfFoodBeverageAll = new HashMap<>();
    HashMap<Integer, String> listOfTechnologyAll = new HashMap<>();
    HashMap<Integer, String > listOfFoodBeveragePlant, listOfTechnologyRoom;
    HashMap<Integer, String> seatingListHashmap;

    int conferenceRoomNumber, foodbeverageNumber, clickedCheckBoxFoodbeverageIndex, clickedCheckBoxTechnologyIndex, clickedRadioButtonIndex;
    String chosenPlantId, urlFoodBeverageListPlant, urlTechnology;
    JSONObject jsonObject;
    JSONArray jsonArray;
    AsyncTask<String, Void, String> asyncFoodbeverage;
    AsyncTask<String, Void, String> asyncTechnology;

    ArrayList<Integer> chosenTechnologyByUser = new ArrayList<>();
    ArrayList<Integer> chosenFoodBeverageByUser = new ArrayList<>();

    private View.OnClickListener radioButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            for(int i=0; i<radioButtons.length;i++){
                if(radioButtons[i].getId() == view.getId()){
                    clickedRadioButtonIndex = i;

                    System.out.println("Hashmap: " + seatingListHashmap);

                    for(Map.Entry<Integer, String> entry : seatingListHashmap.entrySet()) {
                        if (entry.getValue().equals(seatList.get(clickedRadioButtonIndex))) {
                            System.out.println("The selected radiobutton has an ID: " + entry.getKey());
                        }
                    }

                }
            }
        }
    };

    private View.OnClickListener checkBoxFoodbeverageListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            for(int i=0; i<checkBoxFoodbeverage.length;i++){
                if(checkBoxFoodbeverage[i].getId() == view.getId()){
                    clickedCheckBoxFoodbeverageIndex = i;
                    chosenFoodBeverageByUser.add(checkBoxFoodbeverage[clickedCheckBoxFoodbeverageIndex].getId());
                    System.out.println("The clicked checkBox has an ID: "+ checkBoxFoodbeverage[clickedCheckBoxFoodbeverageIndex].getId());
                }
            }
        }
    };

    private View.OnClickListener checkBoxTechnologyListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            for(int i=0; i<checkBoxTechnology.length;i++){
                if(checkBoxTechnology[i].getId() == view.getId()){
                    clickedCheckBoxTechnologyIndex = i;
                    chosenTechnologyByUser.add(checkBoxTechnology[clickedCheckBoxTechnologyIndex].getId());
                    System.out.println("The clicked checkBox has an ID: "+ checkBoxTechnology[clickedCheckBoxTechnologyIndex].getId());
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        scrollViewChoice = findViewById(R.id.scrollViewChoice);
        tableLayout = findViewById(R.id.tableLayoutChoice);
        listOfFoodBeveragePlant = new HashMap<>();
        listOfTechnologyRoom = new HashMap<>();

        Intent in = getIntent();
        seatList = (ArrayList<String>) in.getSerializableExtra("seatList");
        seatingListHashmap = (HashMap<Integer, String>) in.getSerializableExtra("seatingHashmap");
        System.out.println("The sent over hashmap" + seatingListHashmap);

        conferenceRoomNumber = in.getIntExtra("roomNumber",0);
        chosenPlantId = in.getStringExtra("plantId");
        urlFoodBeverageListPlant = "https://dev-be.timetomeet.se/service/rest/plantfoodbeverage/venue/" + chosenPlantId;
        urlTechnology = "https://dev-be.timetomeet.se/service/rest/conferenceroomtechnology/conferenceroom/" + conferenceRoomNumber;

        System.out.println(urlFoodBeverageListPlant);
        System.out.println("Hello from Choice class - conf room number: " + conferenceRoomNumber);
        System.out.println("Hello from Choise class: " + seatList.size() + " " + seatList);
        System.out.println("Hello from Choice class: plant id - " + chosenPlantId);
        radioButtons = new RadioButton[seatList.size()];

        FoodbeverageList foodbeverageList = new FoodbeverageList();
        listOfFoodBeverageAll = foodbeverageList.getFoodbeverageList();

        TechnologyList technologyList = new TechnologyList();
        listOfTechnologyAll = technologyList.getTechnologyList();

        asyncFoodbeverage = new RestConnection();
        asyncFoodbeverage.execute(urlFoodBeverageListPlant, "foodbeverage");

        asyncTechnology = new RestConnection();
        asyncTechnology.execute(urlTechnology, "technology");

        System.out.println("conferenceRoomNumber: " + conferenceRoomNumber);
    }

    private class RestConnection extends AsyncTask<String, Void, String> {
        private String responseContent, choice;
        private String Error = null;
        private URL url;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... requestData) {
            BufferedReader reader=null;

            try {
                url = new URL(requestData[0]);
                choice = requestData[1];

                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true); // True för POST, PUT. False för GET

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null) {
                    sb.append(line + "  \n");
                }

                responseContent = sb.toString();
                jsonArray = new JSONArray(responseContent);
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
            switch (choice) {
                case "foodbeverage":
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject results = jsonArray.getJSONObject(i);
                            int foodBeverage = results.getInt("foodBeverage");
                            System.out.println("FoodbeverageID: " + foodBeverage);

                            for(Map.Entry<Integer, String> entry : listOfFoodBeverageAll.entrySet()) {
                                if (entry.getKey().equals(foodBeverage)) {
                                    listOfFoodBeveragePlant.put(entry.getKey(), entry.getValue());
                                    System.out.println("Food list: " + entry.getKey() + "-" + entry.getValue());
                                }
                            }

                        }

                        checkBoxFoodbeverage = new CheckBox[listOfFoodBeveragePlant.size()];
                        //addChoices(seatList.size(), listOfFoodBeveragePlant.size(),3);


                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case "technology":
                    try {
                        System.out.println("executing technology----------------");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject results = jsonArray.getJSONObject(i);
                            int id = results.getInt("id");
                            int technology = results.getInt("technology");
                            System.out.println("The size of technologyAll " + listOfTechnologyAll.size());

                            for(Map.Entry<Integer, String> entry : listOfTechnologyAll.entrySet()) {
                                if (entry.getKey().equals(technology)) {
                                    listOfTechnologyRoom.put(entry.getKey(), entry.getValue());
                                    System.out.println("Technology list: " + entry.getKey() + "-" + entry.getValue());
                                }
                            }
                        }
                        checkBoxTechnology = new CheckBox[listOfTechnologyRoom.size()];


                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }

            if(seatList.size()>0 && listOfFoodBeveragePlant.size()>0 && listOfTechnologyRoom.size()>0){
                addChoices(seatList.size(), listOfFoodBeveragePlant.size(), listOfTechnologyRoom.size());
            }

        }

    }

    public void addChoices(int furnitureNumber,int foodbeverageNumber, int technologyNumber) {
        tableLayout.removeAllViews();

        tableRow1 = new TableRow(getBaseContext());
            tableRow1.removeAllViews();
        tableRow2 = new TableRow(getBaseContext());
            tableRow2.removeAllViews();
        tableRow3 = new TableRow(getBaseContext());
            tableRow3.removeAllViews();

        linearLayoutH1 = new LinearLayout(getBaseContext());
        linearLayoutH1.setOrientation(LinearLayout.HORIZONTAL);

        linearLayoutH2 = new LinearLayout(getBaseContext());
        linearLayoutH2.setOrientation(LinearLayout.HORIZONTAL);

        linearLayoutH3 = new LinearLayout(getBaseContext());
        linearLayoutH3.setOrientation(LinearLayout.HORIZONTAL);

        linearLayoutV1 = new LinearLayout(getBaseContext());
        linearLayoutV1.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV2 = new LinearLayout(getBaseContext());
        linearLayoutV2.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV3 = new LinearLayout(getBaseContext());
        linearLayoutV3.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV4 = new LinearLayout(getBaseContext());
        linearLayoutV4.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV5 = new LinearLayout(getBaseContext());
        linearLayoutV5.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV6 = new LinearLayout(getBaseContext());
        linearLayoutV6.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV7 = new LinearLayout(getBaseContext());
        linearLayoutV7.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV8 = new LinearLayout(getBaseContext());
        linearLayoutV8.setOrientation(LinearLayout.VERTICAL);

        linearLayoutV9 = new LinearLayout(getBaseContext());
        linearLayoutV9.setOrientation(LinearLayout.VERTICAL);


        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        tableLayout.addView(tableRow3);

            tableRow1.addView(linearLayoutH1);
                linearLayoutH1.addView(linearLayoutV1);
                linearLayoutH1.addView(linearLayoutV2);
                linearLayoutH1.addView(linearLayoutV3);

                //adding room furniture
                for(int i=0; i<furnitureNumber; i++){
                    radioButtons[i] = new RadioButton(getBaseContext());
                    radioButtons[i].setId(i);
                    radioButtons[i].setText(seatList.get(i));
                    if(i<3){
                        linearLayoutV1.addView(radioButtons[i]);
                    } else if(i>=3 && i<6){
                        linearLayoutV2.addView(radioButtons[i]);
                    }else{
                        linearLayoutV3.addView(radioButtons[i]);
                    }
                }

                for(RadioButton r:radioButtons){
                    r.setOnClickListener(radioButtonListener);
                }

            tableRow2.addView(linearLayoutH2);
                linearLayoutH2.addView(linearLayoutV4);
                linearLayoutH2.addView(linearLayoutV5);
                linearLayoutH2.addView(linearLayoutV6);

                //adding foodbeverage
                for(int i1=0; i1<foodbeverageNumber; i1++){
                    checkBoxFoodbeverage[i1] = new CheckBox(getBaseContext());
                    checkBoxFoodbeverage[i1].setId((int) listOfFoodBeveragePlant.keySet().toArray()[i1]);
                    checkBoxFoodbeverage[i1].setText((String) listOfFoodBeveragePlant.values().toArray()[i1]);

                    if(i1<3){
                        linearLayoutV4.addView(checkBoxFoodbeverage[i1]);
                    } else if(i1>=3 && i1<6){
                        linearLayoutV5.addView(checkBoxFoodbeverage[i1]);
                    }else{
                        linearLayoutV6.addView(checkBoxFoodbeverage[i1]);
                    }
                }

            for(CheckBox checkBox:checkBoxFoodbeverage){
                checkBox.setOnClickListener(checkBoxFoodbeverageListener);
            }

            //adding technology
            tableRow3.addView(linearLayoutH3);
            linearLayoutH3.addView(linearLayoutV7);
            linearLayoutH3.addView(linearLayoutV8);
            linearLayoutH3.addView(linearLayoutV9);

            for(int i1=0; i1<technologyNumber; i1++){
                checkBoxTechnology[i1] = new CheckBox(getBaseContext());
                checkBoxTechnology[i1].setId((int) listOfTechnologyRoom.keySet().toArray()[i1]);
                checkBoxTechnology[i1].setText((String) listOfTechnologyRoom.values().toArray()[i1]);

                if(i1<3){
                    linearLayoutV7.addView(checkBoxTechnology[i1]);
                } else if(i1>=3 && i1<6){
                    linearLayoutV8.addView(checkBoxTechnology[i1]);
                }else{
                    linearLayoutV9.addView(checkBoxTechnology[i1]);
                }
            }

            for(CheckBox checkBoxT:checkBoxTechnology){
                checkBoxT.setOnClickListener(checkBoxTechnologyListener);
            }
    }


}
