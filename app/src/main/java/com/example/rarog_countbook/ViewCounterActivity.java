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

        TextView nameText = (TextView) findViewById(R.id.viewNameField);
        TextView commentText = (TextView) findViewById(R.id.viewCommentField);
        TextView dateText = (TextView) findViewById(R.id.viewDateField);
        TextView currentValueText = (TextView) findViewById(R.id.viewCurrentValueField);
        TextView initialValueText = (TextView) findViewById(R.id.viewInitialValueField);

        nameText.setText(name);
        commentText.setText(comment);
        dateText.setText(date);
        currentValueText.setText(currentValue);
        initialValueText.setText(initialValue);
    }
}
