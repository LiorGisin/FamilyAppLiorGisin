package com.example.familyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp.objects.Item;
import com.example.familyapp.objects.ShoppingItem;
import com.example.familyapp.objects.Task;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;


import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Item> itemList;
    ItemsAdapter adapter;
    Item lastSelected;
    long userId;
    User user;
    OpenHelper db;
    String item;
    ImageView icon;
    TextView title;
    LinearLayout layout;
    Button add, empty;
    String type = "All";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        db = new OpenHelper(this);

        title = findViewById(R.id.title);
        layout = findViewById(R.id.layouttt);
        icon = findViewById(R.id.iconOfItem);
        add = findViewById(R.id.addAnItem);
        empty = findViewById(R.id.empty);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        Intent intent1 = getIntent();
        userId = intent1.getExtras().getLong("userId");
        item = intent1.getExtras().getString("item");

        db.open();
        user = db.findUser(userId);
        db.close();


        lv = findViewById(R.id.lv);


        if (item.equals("shopping")){
            title.setText("shopping list");
            title.setTextColor(Color.parseColor("#065926"));
            icon.setBackgroundResource(R.drawable.shopping_foreground);
            add.setBackgroundColor(Color.parseColor("#065926"));
            layout.setBackgroundColor(Color.parseColor(ShoppingItem.BGCOLOR));
            empty.setVisibility(View.VISIBLE);
            db.open();
            itemList = db.getAllShoppingItems(user.getFamilyId());
            db.close();
        }
        else{
            db.open();
            layout.setBackgroundColor(Color.parseColor(Task.BGCOLOR));
            itemList = db.getAllTasks(user.getFamilyId());
            db.close();
        }

        adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
        lv.setAdapter(adapter);




       lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                lastSelected = adapter.getItem(i);

                if(lastSelected.getDone() == 1){
                    return;
                }

                if (lastSelected instanceof Task) {

                    Task tasklastSelected = (Task)lastSelected;

                    if (lastSelected.getOwner_id() == userId) {


                        Intent intent = new Intent(getApplicationContext(), NewTask.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("description", lastSelected.getDescription());
                        intent.putExtra("day", tasklastSelected.getDay());
                        intent.putExtra("performer", tasklastSelected.getPerformer_id());
                        intent.putExtra("frec", Task.generateFrequency(tasklastSelected.getFrequency()));
                        intent.putExtra("isNew", false);
                        intent.putExtra("taskId", lastSelected.getId());


                        startActivityForResult(intent, 1);

                    } else {

                        builder.setTitle("you can't edit someone else's task");
                        builder.setMessage("");
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new HandleAlertDialogListener());
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }



                else {


                    ShoppingItem shoplastSelected = (ShoppingItem) lastSelected;

                    Intent intent = new Intent(getApplicationContext(), NewShoppingItem.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("product", lastSelected.getDescription());
                    intent.putExtra("amount", shoplastSelected.getAmount());
                    intent.putExtra("type", shoplastSelected.getType());
                    intent.putExtra("isNew", false);
                    intent.putExtra("shoppingItemId", lastSelected.getId());

                    startActivityForResult(intent, 0);


                }

            }

        });

    }

    public void empty_the_list(View view) {
        db.open();
        db.deleteAllShoppingItem(type);
        db.close();

        if (type == "All"){
            itemList = db.getAllShoppingItems(user.getFamilyId());
        }
        else{
            itemList = db.getAllType(type, user.getFamilyId());
        }
        adapter = new ItemsAdapter(this, 0, 0, itemList);
        lv.setAdapter(adapter);

    }


    public  class  HandleAlertDialogListener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = this.getMenuInflater();
        if (item.equals("shopping")){
            menuInflater.inflate(R.menu.shopping_menu,menu);
        }
        else{
            menuInflater.inflate(R.menu.tasks_menu,menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.my_tasks){
            long id;
            title.setText("My tasks");
            id = userId;
            itemList = db.getMyTasks(id);
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        else if (item.getItemId()==R.id.everyone){
            title.setText("Tasks");
            itemList = db.getAllTasks(user.getFamilyId());
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        else if(item.getItemId()==R.id.food){
            title.setText("Shopping list food");
            itemList = db.getAllType("food", user.getFamilyId());
            type = "food";
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        else if (item.getItemId()==R.id.pharmacy){
            title.setText("Shopping list pharmacy");
            itemList = db.getAllType("pharmacy", user.getFamilyId());
            type = "pharmacy";
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        else if (item.getItemId()==R.id.other){
            title.setText("Shopping list other");
            itemList = db.getAllType("other", user.getFamilyId());
            type = "other";
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        else if (item.getItemId()==R.id.all){
            title.setText("Shopping list");
            itemList = db.getAllShoppingItems(user.getFamilyId());
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }

        else if (item.getItemId()==R.id.done){
            itemList = db.getDoneTasks(user.getFamilyId());
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        else if (item.getItemId()==R.id.frequency){
            itemList = db.getFrequencyTasks2(user.getFamilyId());
            adapter = new ItemsAdapter(this, 0, 0, itemList, userId);
            lv.setAdapter(adapter);
        }
        return true;
    }


    public void new_item(View view) {

        if (item.equals("shopping")){

            Intent intent = new Intent(getApplicationContext(), NewShoppingItem.class);
            intent.putExtra("userId", userId);
            intent.putExtra("isNew", true);

            startActivityForResult(intent, 0);

        }


        else{
            Intent intent = new Intent(getApplicationContext(), NewTask.class);
            intent.putExtra("userId", userId);
            intent.putExtra("isNew", true);


            startActivityForResult(intent, 1);
        }

        }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String owner_id = data.getExtras().getString("ownerId");
                String description = data.getExtras().getString("description");
                String performerName = data.getExtras().getString("performerName");


                itemList = db.getAllTasks(user.getFamilyId());
                adapter = new ItemsAdapter(this, 0, 0, itemList);
                lv.setAdapter(adapter);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "user cancel", Toast.LENGTH_LONG).show();
            }
        }//and of request code -



        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String owner_id = data.getExtras().getString("ownerId");
                String product = data.getExtras().getString("product");
                String amount = data.getExtras().getString("amount");


                itemList = db.getAllShoppingItems(user.getFamilyId());
                adapter = new ItemsAdapter(this, 0, 0, itemList);
                lv.setAdapter(adapter);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "user cancel", Toast.LENGTH_LONG).show();
            }
        }//and of request code -


    }
}