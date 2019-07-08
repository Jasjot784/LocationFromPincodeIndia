package com.jasjotsingh.locationfrompincode;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {

    private static final String TAG ="AddressActivity" ;
    ArrayList<String> listOfCities = new ArrayList<>();
    ArrayList<String> listOfStates = new ArrayList<>();
    ArrayList<String> listOfDistricts = new ArrayList<>();
    private Button nextButton;
    Spinner spinnerState;
    Spinner spinnerDistrict;
    Spinner spinnerCity;
    Spinner spinnerLocality;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        String url = bundle.getString("url");

        Log.d(TAG, "onCreate: "+url);
        listOfCities.add("Your City");
        listOfDistricts.add("Your District");
        listOfStates.add("Your State");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(TAG, "onResponse: "+response);
                for (int i = 0; i<response.length(); i++) {
                    try {
                        JSONObject user = (JSONObject) response.getJSONObject(i);
                        Log.d(TAG, "onResponse: "+user);
                        JSONArray PostOffice = user.getJSONArray("PostOffice");
                        Log.d(TAG, "onResponse: "+PostOffice);
                         for(int j = 0;j<PostOffice.length();j++){
                            JSONObject user1 = PostOffice.getJSONObject(j);
                            String City = user1.getString("Name");
                             Log.d(TAG, "onResponse: "+City);
                            String District =user1.getString("District");
                             Log.d(TAG, "onResponse: "+District);
                            String State = user1.getString("State");
                             Log.d(TAG, "onResponse: "+State);
                            listOfCities.add(City);
                            listOfDistricts.add(District);
                            listOfStates.add(State);
                             Log.d(TAG, "onResponse: "+listOfCities);
                        }
                        Log.d(TAG, "onResponse: "+listOfCities);

                         spinner();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onResponse: "+listOfCities);


                }}

            private void spinner() {
                Log.d(TAG, "spinner: "+listOfCities);
                init();
                Log.d(TAG, "onCreate2: ");
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent buttonIntent = new Intent(AddressActivity.this, CategoryActivity.class);
                        startActivity(buttonIntent);
                    }
                });

                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(AddressActivity.this,
                        android.R.layout.simple_list_item_1, listOfStates) ;


                spinnerState.setAdapter(stateAdapter);
                Log.d(TAG, "onCreate3: ");

                final ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(AddressActivity.this,
                        android.R.layout.simple_list_item_1, listOfDistricts) ;


                spinnerDistrict.setAdapter(districtAdapter);

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(AddressActivity.this,
                        android.R.layout.simple_list_item_1, listOfCities) ;

                spinnerCity.setAdapter(cityAdapter);

                Log.d(TAG, "onCreate4: " );
                check();

            }

            private void check() {
                spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                        if (position1!=0) {
                            Log.d(TAG, "onCreate5: "+position1);


                            check1();
                        } else {
                            Log.d(TAG, "onCreate6: "+position1);
                            nextButton.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            private void check1() {

                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                        if (position2!=0) {
                            Log.d(TAG, "onCreate5: "+position2);


                            check3();
                        } else {
                            Log.d(TAG, "onCreate6: "+position2);
                            nextButton.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            private void check3() {
                spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {
                        if (position3!=0) {
                            Log.d(TAG, "onCreate5: "+position3);

                            nextButton.setEnabled(true);
                        } else {
                            Log.d(TAG, "onCreate6: "+position3);
                            nextButton.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }



            private void init() {
                nextButton = findViewById(R.id.Next);
                spinnerState = findViewById(R.id.State);
                spinnerDistrict = findViewById(R.id.District);
                spinnerCity = findViewById(R.id.City);
                spinnerLocality = findViewById(R.id.Locality);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error );

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

        Log.d(TAG, "onCreate: "+listOfCities);


    }
}
