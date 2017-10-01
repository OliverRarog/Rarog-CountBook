package com.example.rarog_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button addCounterButton = (Button) findViewById(R.id.addCounterButton);
        addCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCounterIntent = new Intent(view.getContext(), AddCounterActivity.class);
                startActivity(addCounterIntent);
            }
        });

        ArrayList<Counter> list = new ArrayList<Counter>();
        list.add(new Counter(5, "test1", "hello"));
        list.add(new Counter(10, "test2", "bye"));
        for(int i = 0; i < 15; i++) {
            list.add(new Counter(i, "test", "test"));
        }

        CounterRowAdapter adapter = new CounterRowAdapter(list, this);

        ListView lView = (ListView) findViewById(R.id.counterListView);
        lView.setAdapter(adapter);



    }
}
