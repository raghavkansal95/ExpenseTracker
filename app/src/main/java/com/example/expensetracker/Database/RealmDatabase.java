package com.example.expensetracker.Database;

import android.content.Context;
import android.widget.Toast;

import com.example.expensetracker.Model.BuyList;
import com.example.expensetracker.Model.Item;
import com.example.expensetracker.Model.User;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDatabase {

    Realm realm;
    Context context;
    String date;

    public RealmDatabase(Context context) {
        this.context = context;
        Realm.init(context);
        realm=Realm.getDefaultInstance();
        date= Calendar.getInstance().getTime().toString();
    }

    public void createNewBuyList(final String primaryKey){
        realm.beginTransaction();
        BuyList buyList = realm.createObject(BuyList.class, primaryKey);
        buyList.getItemlist().add(new Item());
        realm.commitTransaction();
    }

    public BuyList getAllBuyList(final String primaryKeyDate) {
        realm.beginTransaction();
        BuyList buyList = realm.where(BuyList.class).equalTo("date", primaryKeyDate).findFirst();
        realm.commitTransaction();
        return buyList;
    }

    public void addNewItemInBuyList(String primaryKeyDate) {
        realm.beginTransaction();
        BuyList buyList = realm.where(BuyList.class).equalTo("date", primaryKeyDate).findFirst();
        buyList.getItemlist().add(new Item());
        realm.commitTransaction();
    }

    public void updateNameOfBuyList(String primaryKeyDate, final String buyListName) {
        realm.beginTransaction();
        BuyList buyList = realm.where(BuyList.class).equalTo("date", primaryKeyDate).findFirst();
        buyList.setName(buyListName);
        buyList.setUpdateddate(date);
        realm.commitTransaction();
    }

    public void insertUser(final User user){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(user);
            }
        });
    }

    public RealmResults<BuyList> getAllBuyList(){
        return realm.where(BuyList.class).findAll();
    }

    public void updateItemInBuyList(final String primaryKeyDate, int position, Item item){
        realm.beginTransaction();
        BuyList buyList=realm.where(BuyList.class).equalTo("date", primaryKeyDate).findFirst();
        buyList.getItemlist().get(position).setItemname(item.getItemname());
        buyList.getItemlist().get(position).setItemqty(item.getItemqty());
//      buyList.getItemlist().get(position).setItemcategory(item.getItemcategory());
        realm.commitTransaction();
    }
}
