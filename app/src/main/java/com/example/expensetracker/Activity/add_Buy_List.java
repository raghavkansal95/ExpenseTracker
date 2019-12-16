package com.example.expensetracker.Activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.expensetracker.Adapters.CustomAdapter;
import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.R;
import java.util.Calendar;

public class add_Buy_List extends AppCompatActivity {

    EditText editTextBuyListName;
    RecyclerView dynamicRecyclerView;
    String date;
    Boolean isBuyListEmpty = true;
    CustomAdapter customAdapter;

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    RealmDatabase realmDatabase;

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__buy__list);

        editTextBuyListName = findViewById(R.id.editTexyBuyListName);
        dynamicRecyclerView = findViewById(R.id.dynamicRecyclerView);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(add_Buy_List.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.buyList) {
                    Toast.makeText(getApplicationContext(), "Item1 is selected", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.item2) {
                    Toast.makeText(getApplicationContext(), "Item2 is selected", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.item3) {
                    Toast.makeText(getApplicationContext(), "Item3 is selected", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(Gravity.LEFT, false);
                return true;
            }
        });

        date= Calendar.getInstance().getTime().toString();
        realmDatabase = new RealmDatabase(getApplicationContext());

        editTextBuyListName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isBuyListEmpty){
                    realmDatabase.createNewBuyList(date);
                    isBuyListEmpty = false;
                }

                dynamicRecyclerView.setVisibility(View.VISIBLE);
                dynamicRecyclerView.setHasFixedSize(true);
                dynamicRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                customAdapter = new CustomAdapter(getApplicationContext(), date);
                dynamicRecyclerView.setAdapter(customAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                realmDatabase.updateNameOfBuyList(date,s.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
         if (item.getItemId()==R.id.buyList){
            Toast.makeText(this,"Item1 is selected",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId()==R.id.item2){
            Toast.makeText(this,"Item2 is selected",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId()==R.id.item3){
            Toast.makeText(this,"Item3 is selected",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
