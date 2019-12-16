package com.example.expensetracker.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class BuyList extends RealmObject {

    @PrimaryKey
    @Required
    public String date;
    private String name;
    public RealmList<Item> itemlist=new RealmList<>();
    public String updateddate;

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItemlist(RealmList<Item> itemlist) {
        this.itemlist = itemlist;
      }

      public RealmList<Item> getItemlist() {
        return itemlist;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getName() {
        return name;
      }
}
