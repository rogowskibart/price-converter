package com.rogowskibart.priceconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText productNameEditText;
    EditText amountEditText;
    EditText priceEditText;
    TextView resultTextView;
    Spinner amountTypeSpinner;
    Button amountClearButton;
    Button priceClearButton;
    ListView resultListView;
    Button saveButton;
    Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: starts");
        setContentView(R.layout.activity_main);

        productNameEditText = (EditText) findViewById(R.id.product_name_edittext);
        productNameEditText.setHint(R.string.product_name);

        amountEditText = (EditText) findViewById(R.id.amount_edittext);
        amountEditText.setHint(R.string.amount);

        priceEditText = (EditText) findViewById(R.id.price_edittext);
        priceEditText.setHint(R.string.price);

        resultTextView = (TextView) findViewById(R.id.result_textview);


        amountTypeSpinner = (Spinner) findViewById(R.id.amount_type_spinner);
        amountTypeSpinner.setSelection(0);
        amountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!verifyFieldsEmpty()) {
                    showResult(calculateResult());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Add the buttons to clear the fields
        amountClearButton = (Button) findViewById(R.id.amount_clear_button);
        priceClearButton = (Button) findViewById(R.id.price_clear_button);

        resultListView = (ListView) findViewById(R.id.result_listview);

        amountClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountEditText.setText("");
            }
        });

        priceClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceEditText.setText("");
            }
        });

        // Fill the amount spinner with data
        ArrayList<String> amountTypeArray = new ArrayList<>();
        String[] paths = getResources().getStringArray(R.array.spinner_array);
        for (int i = 0; i < paths.length; i++) {
            amountTypeArray.add(paths[i]);
        }

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
                    showResult(calculateResult());
                    enableSaveButton();
                } else {
                    resultTextView.setText("");
                    disableSaveButton();
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
                    showResult(calculateResult());
                    enableSaveButton();
                } else {
                    resultTextView.setText("");
                    disableSaveButton();
                }
                Log.d(TAG, "priceEditText onTextChanged: ends");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: starts");
            }
        });

        // Getting ListView to work

        final ArrayList<Product> productList = new ArrayList<>();

        final ProductAdapter productAdapter = new ProductAdapter(this, productList);
        resultListView.setAdapter(productAdapter);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = String.valueOf(productNameEditText.getText());
                if (productName.equalsIgnoreCase("")) {
                    productName = "---";
                }
                Double productAmount = Double.valueOf(String.valueOf(amountEditText.getText()));
                String productType = String.valueOf(amountTypeSpinner.getSelectedItem().toString());
                Double pricePerKilo = calculateResult();
                Product product = new Product(productName, productAmount, productType, pricePerKilo);

                productList.add(product);
                productAdapter.notifyDataSetChanged();

                enableClearButton();
                clearFields();
            }
        });
        disableSaveButton();

        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.removeAll(productList);
                productAdapter.notifyDataSetChanged();

                disableClearButton();
                clearFields();
            }
        });
        disableClearButton();

        Log.d(TAG, "onCreate: ends");
    }

    /**
     * This method calculates the price per kilo.
     */
    private double calculateResult() {

        double amount = Double.parseDouble(String.valueOf((amountEditText).getText()));
        double price = Double.parseDouble(String.valueOf((priceEditText).getText()));
        double pricePerKilo;
        double amountTypeMultiplier = 0.0;
        String amountType = amountTypeSpinner.getSelectedItem().toString();
        switch (amountType) {
            case "kg":
                amountTypeMultiplier = 1.0;
                break;
            case "g":
                amountTypeMultiplier = 1000.0;
                break;
            case "l":
                amountTypeMultiplier = 1.0;
                break;
            case "ml":
                amountTypeMultiplier = 1000.0;
                break;
            case "szt":
                amountTypeMultiplier = 1.0;
                break;
        }
        pricePerKilo = (price * amountTypeMultiplier) / (amount);
        return pricePerKilo;
    }

    /**
     * This methods checks if both the fields are empty.
     *
     * @return true if both fields are empty
     */
    private boolean verifyFieldsEmpty() {
        boolean isEmpty = false;

        if (amountEditText.length() == 0
                || amountEditText.getText().toString().equalsIgnoreCase(".")) {
            isEmpty = true;
        }

        if (priceEditText.length() == 0
                || priceEditText.getText().toString().equalsIgnoreCase(".")) {
            isEmpty = true;
        }


        return isEmpty;
    }

    private void enableSaveButton() {
        saveButton.setEnabled(true);
    }

    private void disableSaveButton() {
        saveButton.setEnabled(false);
    }

    private void enableClearButton() {
        clearButton.setEnabled(true);
    }

    private void disableClearButton() {
        clearButton.setEnabled(false);
    }

    /**
     * Sets the text in the result textview
     *
     * @param number
     */
    private void showResult(double number) {
        String result = String.format("%.2f zł", number);
        resultTextView.setText(result);
    }

    private void clearFields() {
        productNameEditText.setText("");
        amountEditText.setText("");
        priceEditText.setText("");
    }


}
