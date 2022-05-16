package com.example.familyapp.objects;

import com.example.familyapp.openhelpers.OpenHelper;

public abstract class Item {

    protected long id;
    protected long family_id;
    protected long owner_id;
    protected int done;
    protected String description;

    public Item(long family_id, long owner_id, int done, String description) {
        this.owner_id = owner_id;
        this.family_id = family_id;
        this.done = done;
        this.description = description;
    }

    public long getFamily_id() {
        return family_id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDone() {
        return done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public abstract int getItemLayout();

    public abstract void setUpdateDone(OpenHelper openHelper);
}
