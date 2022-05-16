package com.example.familyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp.objects.Item;
import com.example.familyapp.objects.ShoppingItem;
import com.example.familyapp.objects.Task;
import com.example.familyapp.openhelpers.OpenHelper;


import java.util.List;

public class ItemsAdapter extends ArrayAdapter<Item>
{
    Context context;
    List<Item> objects;
    OpenHelper db;

    boolean isTask;

    long performerID;



    public ItemsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Item> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        db = new OpenHelper(context);
        isTask = false;

    }

    public ItemsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Item> objects, long performerID) {
        this(context, resource, textViewResourceId, objects);
        this.performerID = performerID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the shoe (we want to add itd details to the list item:
        Item temp = objects.get(position);


        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(temp.getItemLayout() ,parent,false);


        CheckBox curCb = view.findViewById(R.id.cb);
        if(temp.getDone() == 1){
            curCb.setVisibility(View.INVISIBLE);
        }


        if (temp instanceof Task) {
            this.isTask = true;

            Task task = (Task)temp;

            if(Task.generateFrequency(((Task) temp).getFrequency()) != 1){
                curCb.setVisibility(View.INVISIBLE);
            }


            if (task.getPerformer_id() != this.performerID) {
                curCb.setEnabled(false);
            }
            else{
                curCb.setEnabled(true);
            }

            //get refernces to the widgets in the list item:
            TextView _description = (TextView) view.findViewById(R.id.description);
            TextView _performer = (TextView) view.findViewById(R.id.performer);
            TextView _owner = (TextView) view.findViewById(R.id.owner);


            //adapt data.
            _description.setText(temp.getDescription());
            _owner.setText("from: " + db.getUserName(task.getOwner_id()));
            _performer.setText("to: " + db.getUserName(task.getPerformer_id()));
        }


        else{


            ShoppingItem shop = (ShoppingItem) temp;

            //get refernces to the widgets in the list item:
            TextView _description = (TextView)view.findViewById(R.id.product);
            TextView _amount = (TextView)view.findViewById(R.id.amount);
            TextView type = (TextView)view.findViewById(R.id.category);


            //adapt data.
            _description.setText(temp.getDescription());
            _amount.setText("amount: " + shop.getAmount());
            type.setText("category: "+shop.getType());

        }


        CheckBox cb;
        Button delete = view.findViewById(R.id.delete1);

        if (temp.getItemLayout()== R.layout.activity_tasks_layout){
            cb = view.findViewById(R.id.cb);
        }
        else{
            cb = view.findViewById(R.id.cb2);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    if (isTask){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Sure the task is done?");
                        builder.setMessage("You gain 1 point!");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Task is done", new HandleAlertDialogListener(cb, temp, position));
                        builder.setNegativeButton("Cancel", new HandleAlertDialogListener(cb, temp, position));
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Sure you want to delete the item from the shopping list?");
                        builder.setMessage("");
                        builder.setCancelable(true);
                        builder.setPositiveButton("delete", new HandleAlertDialogListener(cb, temp, position));
                        builder.setNegativeButton("Cancel", new HandleAlertDialogListener(cb, temp, position));
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    Toast.makeText(context, "yes" + position, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "no" + position, Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (isTask) {
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Sure you want to delete the item?");
                    builder.setMessage("");
                    builder.setCancelable(true);
                    builder.setPositiveButton("delete", new HandleAlertDialogListener2(delete, temp, position));
                    builder.setNegativeButton("Cancel", new HandleAlertDialogListener2(delete, temp, position));
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }



        return view;
    }

    public  class  HandleAlertDialogListener implements DialogInterface.OnClickListener
    {

        CheckBox cb;
        Item item;
        int position;

        public HandleAlertDialogListener(CheckBox cb, Item item, int position){
            super();
            this.cb = cb;
            this.item = item;
            this.position = position;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            System.out.println(which);
            if (which == -1){

                db.open();
                db.setItemDone(item);
                db.close();
                objects.remove(position);
                notifyDataSetChanged();
            }

            else {
                cb.setChecked(false);
            }

        }

    }


    public  class  HandleAlertDialogListener2 implements DialogInterface.OnClickListener
    {

        Button delete;
        Item item;
        int position;

        public HandleAlertDialogListener2 (Button delete, Item item, int position){
            super();
            this.delete = delete;
            this.item = item;
            this.position = position;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            System.out.println(which);
            if (which == -1){
                db.open();
                //db.setItemDone(item);
                db.deleteTaskByRow(item.getId());
                db.close();
                objects.remove(position);
                notifyDataSetChanged();
            }
        }

    }



}