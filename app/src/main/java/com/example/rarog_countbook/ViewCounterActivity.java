package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_counter);

        Intent intent = getIntent();

        String name = intent.getStringExtra("counterName");
        String comment = intent.getStringExtra("counterComment");
        String date = intent.getStringExtra("counterDate");
        String currentValue = intent.getStringExtra("counterCurrentValue");
        String initialValue = intent.getStringExtra("counterInitialValue");

        TextView nameText = (TextView) findViewById(R.id.nameField);
        TextView commentText = (TextView) findViewById(R.id.commentField);
        TextView dateText = (TextView) findViewById(R.id.dateField);
        TextView currentValueText = (TextView) findViewById(R.id.currentValueField);
        TextView initialValueText = (TextView) findViewById(R.id.initialValueField);

        nameText.setText(name);
        commentText.setText(comment);
        dateText.setText(date);
        currentValueText.setText(currentValue);
        initialValueText.setText(initialValue);
    }
}
