package com.example.familyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familyapp.objects.ShoppingItem;
import com.example.familyapp.objects.User;
import com.example.familyapp.openhelpers.OpenHelper;

public class NewShoppingItem extends AppCompatActivity {

    TextView product, amount, titleShopping;
    RadioButton rbFood, rbPharmacy, rbOther;
    User user;
    long userId;
    OpenHelper db;
    Boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shopping_item);

        product = findViewById(R.id.product);
        titleShopping = findViewById(R.id.titleShopping);
        amount = findViewById(R.id.amount);
        rbFood = findViewById(R.id.food);
        rbPharmacy = findViewById(R.id.pharmacy);
        rbOther = findViewById(R.id.other);

        db = new OpenHelper(this);


        Intent intent = getIntent();
        userId = intent.getExtras().getLong("userId");
        String _product = intent.getExtras().getString("product");
        int _amount = intent.getExtras().getInt("amount");

        db.open();
        user = db.findUser(userId);
        db.close();

        isNew = intent.getExtras().getBoolean("isNew");

        if (!isNew){
            titleShopping.setText("Edit your shopping item");
            product.setText(_product);
            amount.setText(String.valueOf(_amount));
            String type = intent.getExtras().getString("type");

            if (type.equals("food")){
                rbFood.setChecked(true);
            }
            else if (type.equals("pharmacy")){
                rbPharmacy.setChecked(true);
            }
            else{
                rbOther.setChecked(true);
            }

        }

    }


    public void save(View view) {

        int _amount;

        try {
            _amount = Integer.valueOf(amount.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(this,"write a number in amount", Toast.LENGTH_LONG).show();
            return;
        }

        if (product.getText().toString().length()>0 && _amount>0)
        {
            if(!rbFood.isChecked() && !rbPharmacy.isChecked() && !rbOther.isChecked()){
                Toast.makeText(this,"choose type", Toast.LENGTH_LONG).show();
            }

            else{
                    String type;
                    if(rbFood.isChecked()){
                        type = "food";
                    }
                    else if(rbPharmacy.isChecked()){
                        type = "pharmacy";
                    }
                    else{
                        type = "other";
                    }

                    User owner = db.findUser(userId);
                    ShoppingItem s = new ShoppingItem(owner.getFamilyId(), userId, 0, product.getText().toString(), type, Integer.valueOf(amount.getText().toString()));

                    if (isNew) {
                        db.open();
                        s = db.saveShoppingItem(s);
                        db.close();
                    }
                    else{
                        long id = getIntent().getExtras().getLong("shoppingItemId");
                        s.setId(id);
                        db.open();
                        db.updateByRow(s);
                        db.close();
                    }

                    Toast.makeText(this, "data saved", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.putExtra("ownerId", String.valueOf(owner.getUserId()));
                    intent.putExtra("product", product.getText().toString());
                    intent.putExtra("amount", amount.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();

                }

            }

        else{
            Toast.makeText(this,"fill all filds", Toast.LENGTH_LONG).show();
        }

        }

    public void cancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
