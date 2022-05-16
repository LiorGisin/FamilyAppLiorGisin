package com.example.familyapp.objects;

import com.example.familyapp.R;
import com.example.familyapp.objects.Item;
import com.example.familyapp.openhelpers.OpenHelper;

public class ShoppingItem extends Item {

    public static final String BGCOLOR = "#C9E3D8";
    private String type;
    private int amount;

    public ShoppingItem(long family_id, long owner_id, int done, String description, String type, int amount) {
        super(family_id, owner_id, done, description);
        this.type = type;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public int getItemLayout(){
        return R.layout.activity_shopping_layout;
    }

    @Override
    public void setUpdateDone(OpenHelper openHelper) {
        openHelper.setItemDone(this);
    }
}
