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
        final String id = intent.getStringExtra("counterId");
        String name = intent.getStringExtra("counterName");
        String comment = intent.getStringExtra("counterComment");
        String currentValue = intent.getStringExtra("counterCurrentValue");
        String initialValue = intent.getStringExtra("counterInitialValue");

        // initializes the EditText elements that the user will edit
        final EditText nameText = (EditText) findViewById(R.id.editNameField);
        final EditText commentText = (EditText) findViewById(R.id.editCommentField);
        final EditText currentValueText = (EditText) findViewById(R.id.editCurrentValueField);
        final EditText initialValueText = (EditText) findViewById(R.id.editInitialValueField);

        // sets the EditText field initially with the inital values of the counter
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
                intent.putExtra("counterId", id);
                intent.putExtra("counterName", nameText.getText().toString());
                intent.putExtra("counterComment", commentText.getText().toString());
                intent.putExtra("counterCurrentValue", currentValueText.getText().toString());
                intent.putExtra("counterInitialValue", initialValueText.getText().toString());

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
