package com.example.rarog_countbook;

import java.util.Date;

/**
 * This class is what is used to describe a single counter, all counters are a instance of this class.
 * Holds all information pertaining to counters as well as getters and setters for each field.
 * @author Oliver Rarog
 * @version 1.0
 */
public class Counter {
    // private internal fields for counter object
    private int initialValue;
    private int currentValue;
    private Date date;
    private String comment;
    private String counterName;

    /**
     * Constructor for the counter class, creates a counter object
     * @param initialValue the initial value of the counter
     * @param counterName the given name of the counter
     * @param comment optional - if user wants to add a comment to the counter
     */
    Counter(int initialValue, String counterName, String comment) {
        this.initialValue = initialValue;
        currentValue = initialValue;
        date = new Date();
        this.comment = comment;
        this.counterName = counterName;
    }

    /**
     * Gets the current value of the counter
     * @return Current counter value
     */
    int getCurrentValue() {
        return currentValue;
    }

    /**
     * Gets the initial value of the counter
     * @return Initial value
     */
    int getInitialValue() {
        return initialValue;
    }

    /**
     * Gets the name of the counter
     * @return Counter name
     */
    String getCounterName() {
        return counterName;
    }

    /**
     * Gets the comment of the counter
     * @return Counter comment
     *
     */
    String getCounterComment() {
        return comment;
    }

    /**
     * Gets the date of the counter
     * @return Counter date last edited
     *
     */
    Date getCounterDate() {
        return date;
    }

    /**
     * Increments the current displayed value by 1, updates date
     */
    void incrementCurrentValue() {
        if (currentValue < Integer.MAX_VALUE) {
            currentValue += 1;
        }
        updateDate();
    }

    /**
     * Decrements the current displayed value by 1, updates date
     */
    void decrementCurrentValue() {
        if(currentValue >= 1) {
            currentValue -= 1;
            updateDate();
        }
    }

    /**
     * Resets the current value back to the initial value, updates date
     */
    void resetCurrentValue() {
        currentValue = initialValue;
        updateDate();
    }

    /**
     * Changes the counters name, updates date
     * @param newName The new name you want to call the counter
     */
    void editCounterName(String newName) {
        counterName = newName;
        updateDate();
    }

    /**
     * Changes the current displayed value of the counter, updates date
     * @param newValue New number you want to set the current value, must be non-negative integer
     */
    void editCurrentValue(int newValue) {
        if(newValue >= 0) {
            currentValue = newValue;
            updateDate();
        }
    }

    /**
     * Changes the initial value of the counter, updates date
     * @param newValue New number you want to set the initial value, must be non-negative integer
     */
    void editInitialValue(int newValue) {
        if(newValue >= 0) {
            initialValue = newValue;
            updateDate();
        }
    }

    /**
     * Changes the counters comment, updates date
     * @param newComment New string you want to set the comment to
     */
    void editComment(String newComment) {
        comment = newComment;
        updateDate();
    }

    /**
     * Updates the counters date to the current date, cannot be called or edited by the user
     */
    private void updateDate() {
        date = new Date();
    }


}
