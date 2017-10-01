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
    public CounterRowAdapter(ArrayList<Counter> counterList, Context context) {
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

        TextView counterRowName = view.findViewById(R.id.counterRowName);
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
                intent.putExtra("counterName", counter.getCounterName());
                intent.putExtra("counterComment", counter.getCounterComment());
                intent.putExtra("counterDate", counter.getCounterDate().toString());
                intent.putExtra("counterCurrentValue", String.valueOf(counter.getCurrentValue()));
                intent.putExtra("counterInitialValue", String.valueOf(counter.getInitialValue()));

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
                intent.putExtra("counterId", String.valueOf(i));
                intent.putExtra("counterName", counter.getCounterName());
                intent.putExtra("counterComment", counter.getCounterComment());
                intent.putExtra("counterCurrentValue", String.valueOf(counter.getCurrentValue()));
                intent.putExtra("counterInitialValue", String.valueOf(counter.getInitialValue()));

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
    public  void onActivityResult(Intent data) {
        Counter counter = counterList.get(Integer.parseInt(data.getStringExtra("counterId")));

        String newName = data.getStringExtra("counterName");
        String newComment = data.getStringExtra("counterComment");
        String newCurrentValue = data.getStringExtra("counterCurrentValue");
        String newInitialValue = data.getStringExtra("counterInitialValue");

        // Name must not be empty, if it is, don't update the name
        if(!"".equals(newName.trim())) {
            counter.editCounterName(newName);
        }

        // Comment may be empty and cleared
        if("".equals(newComment.trim())) {
            counter.editComment("");
        }
        else {
            counter.editComment(newComment);
        }

        // Current value must be non-negative number, cannot be empty
        if(!"".equals(newCurrentValue.trim())) {
            counter.editCurrentValue(Integer.parseInt(newCurrentValue));
        }

        // Initial value must be non-negative number, cannot be empty
        if (!"".equals(newInitialValue.trim())) {
            counter.editInitialValue(Integer.parseInt(newInitialValue));
        }
    }
}
