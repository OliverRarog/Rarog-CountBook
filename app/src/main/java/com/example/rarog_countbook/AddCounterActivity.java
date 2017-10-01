package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * This activity runs whenever the user wants to create a new counter. Has a UI with
 * several editText elements so that the user can fill them out. If user decides to create the
 * counter, this activity will use putExtra to send the counter information back to the parent
 *
 * @author Oliver Rarog
 * @version 1.0
 * @see MainActivity
 */
public class AddCounterActivity extends AppCompatActivity {
    Button createButton;
    EditText counterNameEditText;
    EditText counterCommentEditText;
    EditText counterValueEditText;



    /* idea to disable button until required editText fields filled
    taken from
    https://stackoverflow.com/questions/20682865/disable-button-when-edit-text-fields-empty
    */
    /**
     * This TextWatcher implementation calls method to check required fields whenever a
     * required field has its text edited, used to disable create button until all required
     * fields are filled
     */
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkRequiredFields();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    /**
     * Before counter can be made, needs name and initial value, this method verifies that
     * both fields are properly filled before the create button is enabled
     */
    private void checkRequiredFields() {
        String nameField = counterNameEditText.getText().toString();
        String valueField = counterValueEditText.getText().toString();

        if(nameFieldEmpty(nameField) || valueFieldEmpty(valueField) ) {
            createButton.setEnabled(false);
        }
        else {
            createButton.setEnabled(true);
        }
    }

    /**
     * Checks if the name field of activity is empty
     * @param name The name the user typed in the box
     * @return Boolean indicating whether name is empty
     */
    private Boolean nameFieldEmpty(String name) {
        return name.trim().isEmpty();
    }

    /**
     * Verifies that the initial value is empty (or non-negative integer)
     * @param value Value the user typed in
     * @return Boolean indicating whether value is invalid
     */
    private Boolean valueFieldEmpty(String value) {
        // checks that user typed in a non-negative number, no non-numeric characters allowed
        return (Integer.getInteger(value) != null && Integer.getInteger(value) >= 0);
    }

    /**
     * Initializes the EditText elements in the activity
     */
    private void instantiateButtonsAndFields() {
        createButton = (Button) findViewById(R.id.createCounterButton);
        counterNameEditText = (EditText) findViewById(R.id.counterNameEditText);
        counterCommentEditText = (EditText) findViewById(R.id.counterCommentEditText);
        counterValueEditText = (EditText) findViewById(R.id.counterValueEditText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        instantiateButtonsAndFields();

        // attaches the TextWatcher to the required fields
        counterNameEditText.addTextChangedListener(textWatcher);
        counterValueEditText.addTextChangedListener(textWatcher);

        // button must be initially disabled since no text is entered yet
        createButton.setEnabled(false);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gets the user's entered information
                Integer value = 0;
                String nameText = counterNameEditText.getText().toString();
                String commentText = counterCommentEditText.getText().toString();
                try {
                    value = Integer.parseInt(counterValueEditText.getText().toString());
                }
                catch(NumberFormatException e) {
                    // if user enters too large of number, cap the number to max ineger size
                    value = value.MAX_VALUE;
                }

                // puts the extra data into the Intent
                Intent intent = getIntent();
                intent.putExtra("nameText", nameText);
                intent.putExtra("commentText", commentText);
                intent.putExtra("initialValue", value);

                // quits the activity with RESULT_OK status
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
