package com.rogowskibart.priceconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import static com.rogowskibart.priceconverter.R.string.amount;
import static com.rogowskibart.priceconverter.R.string.price;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText amountEditText;
    EditText priceEditText;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starts");
        setContentView(R.layout.activity_main);

        amountEditText = (EditText) findViewById(R.id.amount_edittext);
//        amountEditText.setText("0");
        amountEditText.setHint("Amount");
        priceEditText = (EditText) findViewById(R.id.price_edittext);
//        priceEditText.setText("0");
        priceEditText.setHint("Price");
        resultTextView = (TextView) findViewById(R.id.result_textview);
        resultTextView.setText("0");

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "amountEditText beforeTextChanged: starts");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "amountEditText onTextChanged: starts");
                if (s.length() != 0) {
//                    calculateResult();
                } else {
                    resultTextView.setText("0");
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
                if (s.length() != 0) {
//                    calculateResult();
                } else {
                    resultTextView.setText("0");
                    priceEditText.setText("0");
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

    private void calculateResult() {

        if (amountEditText.getText().length() != 0) {
            int amount = Integer.parseInt(String.valueOf((amountEditText).getText()));
        }
        if (priceEditText.getText().length() != 0) {
            int price = Integer.parseInt(String.valueOf((priceEditText).getText()));
        }
        int pricePerKilo;

        pricePerKilo = (price * 1000) / (amount);
        resultTextView.setText(pricePerKilo);
    }
}
