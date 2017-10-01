package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity gives the user several text fields to edit the selected counter if they want to
 * Activity is started for result, therefore it exits with the user edited information put into
 * its Intent
 * @author Oliver Rarog
 * @version 1.0
 * @see CounterRowAdapter
 */
public class EditCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        final Intent intent = getIntent();

        // retrieves the information about the selected counter from parent activity
        String name = getNameFromIntent(intent);
        String comment = getCommentFromIntent(intent);
        String currentValue = getCurrentValueFromIntent(intent);
        String initialValue = getInitialValueFromIntent(intent);
        final String id = getIdFromIntent(intent);

        // initializes the EditText elements that the user will edit
        final EditText nameText = (EditText) findViewById(R.id.editNameField);
        final EditText commentText = (EditText) findViewById(R.id.editCommentField);
        final EditText currentValueText = (EditText) findViewById(R.id.editCurrentValueField);
        final EditText initialValueText = (EditText) findViewById(R.id.editInitialValueField);

        // sets the EditText field initially with the initial values of the counter
        nameText.setText(name);
        commentText.setText(comment);
        currentValueText.setText(currentValue);
        initialValueText.setText(initialValue);

        // when user is done editing and save button is pressed, puts new information inside
        // the intent, result of activity is set to OK and activity closes
        Button saveButton = (Button) findViewById(R.id.saveEditButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putNameInIntent(intent, nameText.getText().toString());
                putCommentInIntent(intent, commentText.getText().toString());
                putCurrentValueInIntent(intent, currentValueText.getText().toString());
                putInitialValueInIntent(intent, initialValueText.getText().toString());
                putIdInIntent(intent, id);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
    private String getIdFromIntent(Intent intent) {
        return intent.getStringExtra("counterId");
    }

    /**
     * Puts a string into the name field of intent's extra data
     * @param intent Intent  you want filled
     * @param name String of the name you want to send
     */
    private void putNameInIntent(Intent intent, String name) {
        intent.putExtra("counterName", name);
    }

    /**
     * Puts a string into the comment field of intent's extra data
     * @param intent Intent  you want filled
     * @param comment String of the comment you want to send
     */
    private void putCommentInIntent(Intent intent, String comment) {
        intent.putExtra("counterComment", comment);
    }

    /**
     * Puts a int into the current value field of intent's extra data
     * @param intent Intent  you want filled
     * @param currentValue Int of the current value you want to send
     */
    private void putCurrentValueInIntent(Intent intent, String currentValue) {
        intent.putExtra("counterCurrentValue", currentValue);
    }

    /**
     * Puts a int into the initial value field of intent's extra data
     * @param intent Intent  you want filled
     * @param initialValue Int of the initial value you want to send
     */
    private void putInitialValueInIntent(Intent intent, String initialValue) {
        intent.putExtra("counterInitialValue", initialValue);
    }

    /**
     * Puts a int into the id field of intent's extra data
     * @param intent Intent  you want filled
     * @param id Int of the id you want to send
     */
    private void putIdInIntent(Intent intent, String id) {
        intent.putExtra("counterId", id);
    }
}
