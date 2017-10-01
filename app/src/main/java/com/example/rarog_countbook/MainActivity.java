package com.example.rarog_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private static final int ADD_COUNTER_ACTIVITY = 1;
    private static final int EDIT_COUNTER_ACTIVITY = 2;

    ListView counterListView;

    ArrayList<Counter> counterList = new ArrayList<>();
    private CounterRowAdapter adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_COUNTER_ACTIVITY) {
            String nameText = data.getExtras().getString("nameText");
            String commentText = data.getExtras().getString("commentText");
            int initialValue = data.getExtras().getInt("initialValue");

            counterList.add(new Counter(initialValue, nameText, commentText));
            adapter.notifyDataSetChanged();
            saveInFile();
        }

        if (resultCode == RESULT_OK && requestCode == EDIT_COUNTER_ACTIVITY) {
            adapter.onActivityResult(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFromFile();

        final Button addCounterButton = (Button) findViewById(R.id.addCounterButton);
        addCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCounterIntent = new Intent(view.getContext(), AddCounterActivity.class);
                startActivityForResult(addCounterIntent, ADD_COUNTER_ACTIVITY);
            }
        });

        counterListView = findViewById(R.id.counterListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new CounterRowAdapter(counterList, this);
        counterListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveInFile();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveInFile();
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);
            if(counterList == null) {
                counterList = new ArrayList<>();
            }
        }
        catch (FileNotFoundException e) {
            counterList = new ArrayList<>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterList, out);
            out.flush();

            fos.close();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
