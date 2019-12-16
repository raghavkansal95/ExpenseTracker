package com.example.expensetracker.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.expensetracker.Adapters.BuyListAdapter;
import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.Database.SharedPref;
import com.example.expensetracker.Model.BuyList;
import com.example.expensetracker.R;
import io.realm.RealmResults;

public class ProfileActivity extends AppCompatActivity {

    Button buttonAddBuyList;
    EditText editTextBuyListName;
    FloatingActionButton floatingbutton;

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    RecyclerView buyListRecyclerView;
    BuyListAdapter buyListAdapter;

    SharedPref sharedPref;
    RealmDatabase realmDatabase;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPref=new SharedPref(this);
        if (!sharedPref.checkLogin()){
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        buttonAddBuyList=findViewById(R.id.buttonAddBuyList);
        editTextBuyListName=findViewById(R.id.editTexyBuyListName);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
        buyListRecyclerView=findViewById(R.id.buyListRecyclerView);
        floatingbutton=findViewById(R.id.floatingbutton);

        setSupportActionBar(toolbar);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.buyList){
                    Toast.makeText(getApplicationContext(),"Item1 is selected",Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (menuItem.getItemId()==R.id.item2){
                    Toast.makeText(getApplicationContext(),"Item2 is selected",Toast.LENGTH_SHORT).show();
                }
                else if (menuItem.getItemId()==R.id.item3){
                    Toast.makeText(getApplicationContext(),"Item3 is selected",Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(Gravity.LEFT,false);
                return true;
            }
        });

        realmDatabase=new RealmDatabase(getApplicationContext());
        RealmResults<BuyList> realmResults=realmDatabase.getAllBuyList();

        if (realmResults.isEmpty()){
           buttonAddBuyList.setVisibility(View.VISIBLE);
        }

        else{
            buyListRecyclerView.setVisibility(View.VISIBLE);
            floatingbutton.setVisibility(View.VISIBLE);
        }

        buttonAddBuyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),add_Buy_List.class);
                startActivity(intent);
            }
        });

        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),add_Buy_List.class);
                startActivity(intent);
            }
        });

        buyListRecyclerView.setHasFixedSize(true);
        buyListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        buyListAdapter=new BuyListAdapter(realmResults,this);
        buyListRecyclerView.setAdapter(buyListAdapter);
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