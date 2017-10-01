package com.example.rarog_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class implements a custom ListAdapter so that a ListView can provide an ArrayList of counters
 * and have it properly displayed with its information and buttons to operate on the counters.
 * @author Oliver Rarog
 * @version 1.0
 * @see Counter
 */
public class CounterRowAdapter extends BaseAdapter implements ListAdapter{
    private static final int EDIT_COUNTER_ACTIVITY = 2; // activity code for the EditCounterActivity

    private ArrayList<Counter> counterList = new ArrayList<>();
    private Context context;

    /**
     * Constructor for the adapter class
     * @param counterList The counter list supplied that you want to display
     * @param context The Context of the activity which uses this adapter
     */
    CounterRowAdapter(ArrayList<Counter> counterList, Context context) {
        this.counterList = counterList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return counterList.size();
    }

    @Override
    public Object getItem(int i) {
        return counterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        // inflates the view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.counter_row, null);
        }
        // gets the counter object that we want to display
        final Counter counter = counterList.get(i);

        /* Sets the TextView of the counter name, comment and current value */
        TextView counterRowValue = view.findViewById(R.id.counterRowValue);
        counterRowValue.setText(Integer.toString(counter.getCurrentValue()));

        final TextView counterRowName = view.findViewById(R.id.counterRowName);
        counterRowName.setText(counter.getCounterName());

        TextView counterRowComment = view.findViewById(R.id.counterRowComment);
        counterRowComment.setText(" - " + counter.getCounterComment());

        /* Initializes each button in the row */
        Button incrementButton = view.findViewById(R.id.increment_button);
        Button decrementButton = view.findViewById(R.id.decrement_button);
        Button resetButton = view.findViewById(R.id.reset_button);
        Button viewButton = view.findViewById(R.id.view_button);
        Button editButton = view.findViewById(R.id.edit_button);
        Button deleteButton = view.findViewById(R.id.delete_button);

        // increment button increments the counter object
        incrementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter.incrementCurrentValue();
                notifyDataSetChanged();
            }
        });

        // decrement button decrements the counter object
        decrementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter.decrementCurrentValue();
                notifyDataSetChanged();
            }
        });

        // reset button resets the counter object current value
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter.resetCurrentValue();
                notifyDataSetChanged();
            }
        });

        // view button launches a ViewCounterActivity so that the user can see all the information
        // about the counter. Supplies the ViewCounterActivity with all counter information
        viewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewCounterActivity.class);

                putNameInIntent(intent, counter.getCounterName());
                putCommentInIntent(intent, counter.getCounterComment());
                putCurrentValueInIntent(intent, counter.getCurrentValue());
                putInitialValueInIntent(intent, counter.getInitialValue());
                putDateInIntent(intent, counter.getCounterDate().toString());

                context.startActivity(intent);
            }
        });

        // edit button launches an EditCounterActivity so that the user can edit some information
        // about the counter. Supplies the EditCounterActivity with all relevant information.
        // Activity is started for result, when it closes onActivityResult is called to update
        // the counter with the changed information
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditCounterActivity.class);

                putNameInIntent(intent, counter.getCounterName());
                putCommentInIntent(intent, counter.getCounterComment());
                putCurrentValueInIntent(intent, counter.getCurrentValue());
                putInitialValueInIntent(intent, counter.getInitialValue());
                putIdInIntent(intent, i);

                ((Activity) context).startActivityForResult(intent, EDIT_COUNTER_ACTIVITY );
            }
        });

        // removes the counter from the counterList
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counterList.remove(i);
                notifyDataSetChanged();
            }
        });

        return view;
    }



    /**
     * Called when startActivityForResult is finished, takes the extra data in intent put in
     * by child activity and uses it to update the current counter with new information
     * @param data Intent created by the child activity, contains user inputted information
     */
    void onActivityResult(Intent data) {
        int id;
        try{
            id = getIdFromIntent(data);
        }
        catch (NumberFormatException e) {
            return;
        }
        Counter counter = counterList.get(id);

        String newName = getNameFromIntent(data);
        String newComment = getCommentFromIntent(data);

        // Name must not be empty, if it is, don't update the name
        if(!"".equals(newName.trim())) {
            counter.editCounterName(newName);
        }

        // Comment may be empty and cleared
        counter.editComment(newComment);

        try {
            counter.editCurrentValue(getCurrentValueFromIntent(data));
        }
        catch(NumberFormatException e) {
            // don't update if value gotten is not a number or parsable
        }
        try {
            counter.editInitialValue(getInitialValueFromIntent(data));
        }
        catch(NumberFormatException e) {
            // don't update if value gotten is not a number or parsable
        }
    }

    /**
     * Gets the counter name from an intent's extra data
     * @param intent Intent which holds a counter's name
     * @return String of the counter's name
     */
    private String getNameFromIntent(Intent intent) {
        return intent.getStringExtra("counterName");
    }

    /**
     * Gets the counter comment from an intent's extra data
     * @param intent Intent which holds a counter's comment
     * @return String of the counter's comment
     */
    private String getCommentFromIntent(Intent intent) {
        return intent.getStringExtra("counterComment");
    }

    /**
     * Gets the counter current value from an intent's extra data
     * @param intent Intent which holds a counter's current value
     * @return Int of the counter's current value
     * @throws NumberFormatException If the string in the intent can't be parsed, throw exception
     */
    private int getCurrentValueFromIntent(Intent intent) throws NumberFormatException {
        return Integer.parseInt(intent.getStringExtra("counterCurrentValue"));
    }

    /**
     * Gets the counter initial value from an intent's extra data
     * @param intent Intent which holds a counter's initial value
     * @return Int of the counter's initial value
     * @throws NumberFormatException If the string in the intent can't be parsed, throw exception
     */
    private int getInitialValueFromIntent(Intent intent) throws NumberFormatException {
        return Integer.parseInt(intent.getStringExtra("counterInitialValue"));
    }

    /**
     * Gets the id of the counter from the intent's extra data
     * @param intent Intent which holds a counter's id
     * @return Int of the counter's id
     * @throws NumberFormatException If the string in the intent can't be parsed, throw exception
     */
    private int getIdFromIntent(Intent intent) throws NumberFormatException{
        return Integer.parseInt(intent.getStringExtra("counterId"));
    }

    /**
     * Puts a string into the name field of intent's extra data
     * @param intent Intent  you want filled
     * @param name String of the name you want to send
     */
    private void putNameInIntent(Intent intent, String name) {
        intent.putExtra("counterName", name);
    }

    /**
     * Puts a string into the comment field of intent's extra data
     * @param intent Intent  you want filled
     * @param comment String of the comment you want to send
     */
    private void putCommentInIntent(Intent intent, String comment) {
        intent.putExtra("counterComment", comment);
    }

    /**
     * Puts a int into the current value field of intent's extra data
     * @param intent Intent  you want filled
     * @param currentValue Int of the current value you want to send
     */
    private void putCurrentValueInIntent(Intent intent, int currentValue) {
        intent.putExtra("counterCurrentValue", String.valueOf(currentValue));
    }

    /**
     * Puts a int into the initial value field of intent's extra data
     * @param intent Intent  you want filled
     * @param initialValue Int of the initial value you want to send
     */
    private void putInitialValueInIntent(Intent intent, int initialValue) {
        intent.putExtra("counterInitialValue", String.valueOf(initialValue));
    }

    /**
     * Puts a Date into the date field of intent's extra data
     * @param intent Intent  you want filled
     * @param date Date object of the date you want to send
     */
    private void putDateInIntent(Intent intent, String date) {
        intent.putExtra("counterDate", date);
    }

    /**
     * Puts a int into the id field of intent's extra data
     * @param intent Intent  you want filled
     * @param id Int of the id you want to send
     */
    private void putIdInIntent(Intent intent, int id) {
        intent.putExtra("counterId", String.valueOf(id));
    }
}
