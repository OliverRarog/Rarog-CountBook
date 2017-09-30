package com.example.rarog_countbook;

import java.util.Date;

/**
 * Created by Oliver on 30/09/2017.
 */

public class Counter {
    private int initialValue;
    private int currentValue;
    private Date date;
    private String comment;
    private String counterName;


    public Counter(int initialValue, String counterName) {
        this.initialValue = initialValue;
        currentValue = initialValue;
        date = new Date();
        comment = "";
        this.counterName = counterName;
;    }

    public Counter(int initialValue, String counterName, String comment) {
        this.initialValue = initialValue;
        currentValue = initialValue;
        date = new Date();
        this.comment = comment;
        this.counterName = counterName;
    }

    public void incrementCurrentValue() {
        currentValue += 1;
    }

    public void decrementCurrentValue() {
        currentValue -= 1;
    }

    public void resetCurrentValue() {
        currentValue = initialValue;
    }

    public void editCounterName(String newName) {
        counterName = newName;
    }

    public void editCurrentValue(int newValue) {
        currentValue = newValue;
    }

    public void editInitialValue(int newValue) {
        initialValue = newValue;
    }

    public void editComment(String newComment) {
        comment = newComment;
    }


}
