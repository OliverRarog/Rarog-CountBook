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

    public int getCurrentValue() {
        return currentValue;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public String getCounterName() {
        return counterName;
    }

    public String getCounterComment() {
        return comment;
    }

    public Date getCounterDate() {
        return date;
    }

    public void incrementCurrentValue() {
        currentValue += 1;
        updateDate();
    }

    public void decrementCurrentValue() {
        if(currentValue >= 1) {
            currentValue -= 1;
            updateDate();
        }
    }

    public void resetCurrentValue() {
        currentValue = initialValue;
        updateDate();
    }

    public void editCounterName(String newName) {
        counterName = newName;
        updateDate();
    }

    public void editCurrentValue(int newValue) {
        if(newValue >= 0) {
            currentValue = newValue;
            updateDate();
        }
    }

    public void editInitialValue(int newValue) {
        if(newValue >= 0) {
            initialValue = newValue;
            updateDate();
        }
    }

    public void editComment(String newComment) {
        comment = newComment;
        updateDate();
    }

    public void updateDate() {
        date = new Date();
    }


}
