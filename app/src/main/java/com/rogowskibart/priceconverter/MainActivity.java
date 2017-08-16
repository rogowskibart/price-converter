package com.rogowskibart.priceconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText amountEditText;
    EditText priceEditText;
    TextView resultTextView;
    Spinner amountTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starts");
        setContentView(R.layout.activity_main);

        amountEditText = (EditText) findViewById(R.id.amount_edittext);
        amountEditText.setHint("Amount");

        priceEditText = (EditText) findViewById(R.id.price_edittext);
        priceEditText.setHint("Price");

        resultTextView = (TextView) findViewById(R.id.result_textview);

        amountTypeSpinner = (Spinner) findViewById(R.id.amount_type_spinner);
        amountTypeSpinner.setSelection(0, true);

        // Fill the amount spinner with data
        List<String> amountTypeArray = new ArrayList<>();
        amountTypeArray.add("kg");
        amountTypeArray.add("g");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, amountTypeArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = amountTypeSpinner;
        sItems.setAdapter(adapter);

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "amountEditText beforeTextChanged: starts");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "amountEditText onTextChanged: starts");
                if (!verifyFieldsEmpty()) {
                    calculateResult();
                } else {
                    resultTextView.setText("");
//                    amountEditText.setText("0");
                }
                Log.d(TAG, "amountEditText onTextChanged: ends");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "amountEditText afterTextChanged: starts");
            }
        });

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "priceEditText beforeTextChanged: starts");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "priceEditText onTextChanged: starts");
                if (!verifyFieldsEmpty()) {
                    calculateResult();
                } else {
                    resultTextView.setText("");
//                    priceEditText.setText("0");
                }
                Log.d(TAG, "priceEditText onTextChanged: ends");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: starts");
            }
        });
        Log.d(TAG, "onCreate: ends");
    }

    /**
     * This method calculates the price per kilo.
     */
    private void calculateResult() {

        double amount = Double.parseDouble(String.valueOf((amountEditText).getText()));
        double price = Double.parseDouble(String.valueOf((priceEditText).getText()));
        double pricePerKilo;
        double amountTypeMultiplier = 0.0;
        String amountType = amountTypeSpinner.getSelectedItem().toString();
        if (amountType.equalsIgnoreCase("kg")) {
            amountTypeMultiplier = 1.0;
        }
        if (amountType.equalsIgnoreCase("g")) {
            amountTypeMultiplier = 1000.0;
        }
        pricePerKilo = (price * amountTypeMultiplier) / (amount);

        resultTextView.setText(Double.toString(pricePerKilo));
    }

    /**
     * This methods checks if both the fields are empty.
     * @return true if both fields are empty
     */
    private boolean verifyFieldsEmpty() {
        boolean isEmpty = false;

        if(amountEditText.length() == 0) {
            isEmpty = true;
        }

        if(priceEditText.length() == 0) {
            isEmpty = true;
        }

        return isEmpty;
    }
}
