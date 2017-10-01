package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCounterActivity extends AppCompatActivity {
    Button createButton;
    EditText counterNameEditText;
    EditText counterCommentEditText;
    EditText counterValueEditText;


    /* idea to disable button until required editText fields filled
    taken from
    https://stackoverflow.com/questions/20682865/disable-button-when-edit-text-fields-empty
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

    private void checkRequiredFields() {
        String nameField = counterNameEditText.getText().toString();
        String valueField = counterValueEditText.getText().toString();

        if(verifyNameField(nameField) || verifyValueField(valueField) ) {
            createButton.setEnabled(false);
        }
        else {
            createButton.setEnabled(true);
        }
    }

    private Boolean verifyNameField(String name) {
        return name.trim().isEmpty();
    }

    private Boolean verifyValueField(String value) {
        // checks that user typed in a non-negative number, no non-numeric characters allowed
        return (Integer.getInteger(value) != null && Integer.getInteger(value) >= 0);
    }

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

        counterNameEditText.addTextChangedListener(textWatcher);
        counterValueEditText.addTextChangedListener(textWatcher);

        createButton.setEnabled(false);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = counterNameEditText.getText().toString();
                String commentText = counterCommentEditText.getText().toString();
                Integer value = Integer.parseInt(counterValueEditText.getText().toString());

                Intent intent = getIntent();
                intent.putExtra("nameText", nameText);
                intent.putExtra("commentText", commentText);
                intent.putExtra("initialValue", value);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
