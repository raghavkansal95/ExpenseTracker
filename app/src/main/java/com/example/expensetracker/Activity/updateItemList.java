package com.example.expensetracker.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;

import com.example.expensetracker.Adapters.UpdateItemsAdapter;
import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.Model.BuyList;
import com.example.expensetracker.R;

import java.util.Calendar;
import java.util.Date;

public class updateItemList extends AppCompatActivity {

    EditText editTextEditBuyListName;
    RecyclerView RecyclerViewEditItemList;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item_list);

        date= Calendar.getInstance().getTime().toString();

        final RealmDatabase realmDatabase=new RealmDatabase(getApplicationContext());

        editTextEditBuyListName=findViewById(R.id.editTextEditBuyListName);
        RecyclerViewEditItemList=findViewById(R.id.RecyclerViewEditItemList);

        Intent intent=getIntent();
        final String PrimaryKeyDate= intent.getStringExtra("PrimaryKeyDate");

        BuyList buyList = realmDatabase.getAllBuyList(PrimaryKeyDate);

        editTextEditBuyListName.setText(buyList.getName());
        editTextEditBuyListName.setGravity(Gravity.CENTER_HORIZONTAL);

        editTextEditBuyListName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  realmDatabase.updateNameOfBuyList(PrimaryKeyDate,s.toString());

            }
        });

        RecyclerViewEditItemList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewEditItemList.setHasFixedSize(true);
        UpdateItemsAdapter updateItemsAdapter=new UpdateItemsAdapter(PrimaryKeyDate, this);
        RecyclerViewEditItemList.setAdapter(updateItemsAdapter);
    }
}
