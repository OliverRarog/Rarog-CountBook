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
        String name = getNameFromIntent(intent);
        String comment = getCommentFromIntent(intent);
        String date = getDateFromIntent(intent);
        String currentValue = getCurrentValueFromIntent(intent);
        String initialValue = getInitialValueFromIntent(intent);

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

    /**
     * Gets the counter name from an intent's extra data
     * @param intent Intent which holds a counter's name
     * @return String of the counter's name
     */
    private String getNameFromIntent(Intent intent) {
        return intent.getStringExtra("counterName");
    }

    /**
     * Gets the counter comment from an intent's extra data
     * @param intent Intent which holds a counter's comment
     * @return String of the counter's comment
     */
    private String getCommentFromIntent(Intent intent) {
        return intent.getStringExtra("counterComment");
    }

    /**
     * Gets the counter current value from an intent's extra data
     * @param intent Intent which holds a counter's current value
     * @return Int of the counter's current value
     * @throws NumberFormatException If the string in the intent can't be parsed, throw exception
     */
    private String getCurrentValueFromIntent(Intent intent) {
        return intent.getStringExtra("counterCurrentValue");
    }

    /**
     * Gets the counter initial value from an intent's extra data
     * @param intent Intent which holds a counter's initial value
     * @return Int of the counter's initial value
     * @throws NumberFormatException If the string in the intent can't be parsed, throw exception
     */
    private String getInitialValueFromIntent(Intent intent) {
        return intent.getStringExtra("counterInitialValue");
    }

    /**
     * Gets the id of the counter from the intent's extra data
     * @param intent Intent which holds a counter's id
     * @return Int of the counter's id
     * @throws NumberFormatException If the string in the intent can't be parsed, throw exception
     */
    private String getDateFromIntent(Intent intent) {
        return intent.getStringExtra("counterDate");
    }
}
