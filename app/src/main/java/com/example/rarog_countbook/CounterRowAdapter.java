package com.example.rarog_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private static final int EDIT_COUNTER_ACTIVITY = 2;

    private ArrayList<Counter> counterList = new ArrayList<>();
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
                Intent intent = new Intent(context, ViewCounterActivity.class);
                intent.putExtra("counterName", counter.getCounterName());
                intent.putExtra("counterComment", counter.getCounterComment());
                intent.putExtra("counterDate", counter.getCounterDate().toString());
                intent.putExtra("counterCurrentValue", String.valueOf(counter.getCurrentValue()));
                intent.putExtra("counterInitialValue", String.valueOf(counter.getInitialValue()));

                context.startActivity(intent);
            }
        });

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

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                counterList.remove(i);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public  void onActivityResult(Intent data) {
        Counter counter = counterList.get(Integer.parseInt(data.getStringExtra("counterId")));

        String newName = data.getStringExtra("counterName");
        String newComment = data.getStringExtra("counterComment");
        String newCurrentValue = data.getStringExtra("counterCurrentValue");
        String newInitialValue = data.getStringExtra("counterInitialValue");

        if(!"".equals(newName.trim())) {
            counter.editCounterName(newName);
        }
        if("".equals(newComment.trim())) {
            counter.editComment("");
        }
        else {
            counter.editComment(newComment);
        }
        if(!"".equals(newCurrentValue.trim())) {
            counter.editCurrentValue(Integer.parseInt(newCurrentValue));
        }
        if (!"".equals(newInitialValue.trim())) {
            counter.editInitialValue(Integer.parseInt(newInitialValue));
        }
    }
}
