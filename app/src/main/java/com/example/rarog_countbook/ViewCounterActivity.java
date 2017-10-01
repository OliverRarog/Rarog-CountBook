package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This activity is called when user wants to see the details of a selected counter.
 * Activity is provided with information about the counter and this class uses it to
 * display it in its TextView elements
 * @author Oliver Rarog
 * @version 1.0
 * @see Counter
 * @see MainActivity
 * @see CounterRowAdapter
 */
public class ViewCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_counter);

        Intent intent = getIntent();

        // gets the provided information about the counter
        String name = intent.getStringExtra("counterName");
        String comment = intent.getStringExtra("counterComment");
        String date = intent.getStringExtra("counterDate");
        String currentValue = intent.getStringExtra("counterCurrentValue");
        String initialValue = intent.getStringExtra("counterInitialValue");

        // initializes the TextView elements
        TextView nameText = (TextView) findViewById(R.id.viewNameField);
        TextView commentText = (TextView) findViewById(R.id.viewCommentField);
        TextView dateText = (TextView) findViewById(R.id.viewDateField);
        TextView currentValueText = (TextView) findViewById(R.id.viewCurrentValueField);
        TextView initialValueText = (TextView) findViewById(R.id.viewInitialValueField);

        // sets the TextView elements with provided information
        nameText.setText(name);
        commentText.setText(comment);
        dateText.setText(date);
        currentValueText.setText(currentValue);
        initialValueText.setText(initialValue);
    }
}
