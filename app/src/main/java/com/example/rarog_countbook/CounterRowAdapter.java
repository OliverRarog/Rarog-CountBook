package com.example.rarog_countbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oliver on 30/09/2017.
 */

public class CounterRowAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<Counter> counterList = new ArrayList<Counter>();
    private Context context;

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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.counter_row, null);
        }
        final Counter counter = counterList.get(i);


        TextView counterRowValue = view.findViewById(R.id.counterRowValue);
        counterRowValue.setText(Integer.toString(counter.getCurrentValue()));

        TextView counterRowName = view.findViewById(R.id.counterRowName);
        counterRowName.setText(counter.getCounterName());

        TextView counterRowComment = view.findViewById(R.id.counterRowComment);
        counterRowComment.setText(counter.getCounterComment());



        Button incrementButton = view.findViewById(R.id.increment_button);
        Button decrementButton = view.findViewById(R.id.decrement_button);
        Button resetButton = view.findViewById(R.id.reset_button);
        Button viewButton = view.findViewById(R.id.view_button);
        Button editButton = view.findViewById(R.id.edit_button);
        Button deleteButton = view.findViewById(R.id.delete_button);

        incrementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter.incrementCurrentValue();
                notifyDataSetChanged();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter.decrementCurrentValue();
                notifyDataSetChanged();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counter.resetCurrentValue();
                notifyDataSetChanged();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counterList.remove(i);
                notifyDataSetChanged();
            }
        });

        return view;
    }

}
