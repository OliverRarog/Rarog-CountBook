package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        final Intent intent = getIntent();

        final String id = intent.getStringExtra("counterId");
        String name = intent.getStringExtra("counterName");
        String comment = intent.getStringExtra("counterComment");
        String currentValue = intent.getStringExtra("counterCurrentValue");
        String initialValue = intent.getStringExtra("counterInitialValue");

        final EditText nameText = (EditText) findViewById(R.id.editNameField);
        final EditText commentText = (EditText) findViewById(R.id.editCommentField);
        final EditText currentValueText = (EditText) findViewById(R.id.editCurrentValueField);
        final EditText initialValueText = (EditText) findViewById(R.id.editInitialValueField);

        nameText.setText(name);
        commentText.setText(comment);
        currentValueText.setText(currentValue);
        initialValueText.setText(initialValue);

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
