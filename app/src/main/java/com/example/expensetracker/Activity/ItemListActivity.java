package com.example.expensetracker.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.expensetracker.Adapters.ItemListAdapter;
import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.Model.BuyList;
import com.example.expensetracker.Model.Item;
import com.example.expensetracker.R;

import io.realm.RealmList;

public class ItemListActivity extends AppCompatActivity {

    TextView textViewBuyListName;
    RecyclerView itemListRecyclerView;
    ItemListAdapter itemListAdapter;
    Button ButtonUpdateItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        RealmDatabase realmDatabase=new RealmDatabase(getApplicationContext());

        Intent intent=getIntent();
        final String primaryKeyDate = intent.getStringExtra("primaryKeyDate");

        textViewBuyListName=findViewById(R.id.textViewBuyListName);
        ButtonUpdateItem=findViewById(R.id.ButtonUpdateItem);

        BuyList buyList = realmDatabase.getAllBuyList(primaryKeyDate);
        RealmList<Item> itemList=  buyList.getItemlist();

        textViewBuyListName.setText(buyList.getName());
        textViewBuyListName.setGravity(Gravity.CENTER_HORIZONTAL);

        itemListRecyclerView=findViewById(R.id.itemListRecyclerView);
        itemListRecyclerView.setHasFixedSize(true);
        itemListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListAdapter=new ItemListAdapter(itemList,this);
        itemListRecyclerView.setAdapter(itemListAdapter);

        ButtonUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ItemListActivity.this,updateItemList.class);
                intent1.putExtra("PrimaryKeyDate" , primaryKeyDate );
                startActivity(intent1);
            }
        });
    }
}
