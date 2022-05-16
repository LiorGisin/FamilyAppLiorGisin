package com.example.familyapp.objects;

import com.example.familyapp.R;
import com.example.familyapp.openhelpers.OpenHelper;

public class Task extends Item {

    public static final String BGCOLOR = "#DCD3E3";
    public static final int ONCE = 1;
    public static final int EVERY_DAY = 2;
    public static final int ONCE_A_WEEK = 3;

    private long performer_id;
    private int tMonth;
    private FREQUENCY frequency;
    private int day;

    public static enum FREQUENCY {
        once,
        every_day,
        once_a_week
    }

    public Task copyConstructor(){
        Task tCopy = new Task(this.getFamily_id(), this.getOwner_id(), this.getDone(), this.tMonth, this.getDescription(), this.getPerformer_id(), this.getFrequency(), this.getDay());
        return tCopy;
    }

    public int gettMonth() {
        return tMonth;
    }

    public void settMonth(int tMonth) {
        this.tMonth = tMonth;
    }

    public long getPerformer_id() {
        return performer_id;
    }

    public void setPerformer_id(long performer_id) {
        this.performer_id = performer_id;
    }

    public FREQUENCY getFrequency() {
        return frequency;
    }

    public void setFrequency(FREQUENCY frequency) {
        this.frequency = frequency;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Task(long family_id, long owner_id, int done, int tMonth, String description, long performer_id, FREQUENCY f, int day) {
        super(family_id, owner_id, done, description);
        this.performer_id = performer_id;
        this.tMonth = tMonth;
        this.frequency = f;
        this.day = day;
    }

    

    public static int generateFrequency(FREQUENCY frequency){
        switch (frequency){
            case once:
                return ONCE;
            case every_day:
                return EVERY_DAY;
            case once_a_week:
                return ONCE_A_WEEK;
        }

        return -1;
    }

    public static FREQUENCY generateFrequency(int frequency){
        switch (frequency){
            case ONCE:
                return FREQUENCY.once;
            case EVERY_DAY:
                return FREQUENCY.every_day;
            case ONCE_A_WEEK:
                return FREQUENCY.once_a_week;
        }

        return FREQUENCY.once;
    }


    public int getItemLayout(){
        return R.layout.activity_tasks_layout;
    }

    @Override
    public void setUpdateDone(OpenHelper openHelper) {
        openHelper.setItemDone(this);
    }
}
