package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_COUNTER_ACTIVITY = 1;
    ArrayList<Counter> counterList = new ArrayList<Counter>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_COUNTER_ACTIVITY) {
            String nameText = data.getExtras().getString("nameText");
            String commentText = data.getExtras().getString("commentText");
            int initialValue = data.getExtras().getInt("initialValue");

            counterList.add(new Counter(initialValue, nameText, commentText));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addCounterButton = (Button) findViewById(R.id.addCounterButton);
        addCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCounterIntent = new Intent(view.getContext(), AddCounterActivity.class);
                startActivityForResult(addCounterIntent, ADD_COUNTER_ACTIVITY);
            }
        });


        CounterRowAdapter adapter = new CounterRowAdapter(counterList, this);

        ListView lView = (ListView) findViewById(R.id.counterListView);
        lView.setAdapter(adapter);



    }
}
