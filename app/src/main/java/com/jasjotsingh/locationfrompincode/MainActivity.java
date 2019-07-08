package com.jasjotsingh.locationfrompincode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView pincodeText;
    EditText pincode;
    String zipcode;
    String url;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pincodeText = findViewById(R.id.pincodeText);
        pincode = findViewById(R.id.pincode);
        submitButton = findViewById(R.id.submitButton);

        postalCode();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postalCode();
                Intent buttonIntent = new Intent(MainActivity.this,AddressActivity.class);
                buttonIntent.putExtra("url",url);
                startActivity(buttonIntent);
            }
        });

    }

    private void postalCode() {
        zipcode = pincode.getText().toString();
        url = "https://api.postalpincode.in/pincode/"+zipcode;
        Log.d(TAG, "onCreate: "+url);

    }

}
