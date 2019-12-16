package com.example.expensetracker.Model;

import io.realm.RealmObject;

public class User extends RealmObject {
    public String name="N/A";
    public String phone="N/A";
    public String email="N/A";
    public String displayimage="N/A";

    public String getDisplayimage() {
        return displayimage;
    }

    public void setDisplayimage(String displayimage) {
        this.displayimage = displayimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
