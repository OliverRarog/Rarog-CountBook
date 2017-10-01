package com.example.rarog_countbook;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

//TODO: CLEANUP!!
//TODO: MORE INPUT CHECKING
//TODO: CAP INT VALUE SIZE OR IT WILL CRASH

/**
 * The main activity class, its the first activity to launch and all other activities
 * are a child of this one. The main purpose of this activity is to display all the counters
 * and their buttons through a custom adapter and to be able to create a new counter.
 * @author Oliver Rarog
 * @version 1.0
 * @see AddCounterActivity
 * @see EditCounterActivity
 * @see CounterRowAdapter
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav"; // file where counter data is stored

    // request code for child activities
    private static final int ADD_COUNTER_ACTIVITY = 1;
    private static final int EDIT_COUNTER_ACTIVITY = 2;

    ListView counterListView;

    ArrayList<Counter> counterList = new ArrayList<>();
    private CounterRowAdapter adapter;

    /**
     * Called when startActivityForResult() finishes, used to getExtras put in by child activity
     * @param requestCode The activity code of the activity
     * @param resultCode The code the activity returned
     * @param data Intent created when activity was launched, holds extra data
     */
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

        loadFromFile(); // load old counter list from file

        final Button addCounterButton = (Button) findViewById(R.id.addCounterButton);
        addCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when create counter button is pushed, launch AddCounterActivity
                Intent addCounterIntent = new Intent(view.getContext(), AddCounterActivity.class);
                startActivityForResult(addCounterIntent, ADD_COUNTER_ACTIVITY);
            }
        });

        counterListView = (ListView) findViewById(R.id.counterListView);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter = new CounterRowAdapter(counterList, this);
        counterListView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        // in activity lifecycle, onPause is called whenever activity is not in foreground (including
        // when the user exits the app) therefore, we save counter list in case the activity is
        // not going to start again
        saveInFile();
        super.onPause();
    }

    /**
     * Using GSON, method loads the counterList from a file saved on device
     * @throws FileNotFoundException If there is no file to load on device create a new counterList
     */
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

    /**
     * Using GSON, method saves the counterList to a file saved on device
     * @throws IOException If app cannot open or create new file, will create RuntimeException
     */
    public void saveInFile() {
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
