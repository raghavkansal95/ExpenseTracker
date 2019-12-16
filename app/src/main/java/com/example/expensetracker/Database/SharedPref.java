package com.example.expensetracker.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref{

  SharedPreferences sharedPreferences;
  SharedPreferences.Editor editor;

  Context context;

  int Private_Mode=0;
  private static final String PREF_NAME = "Pref_Name";
  private static final String LOGIN_KEY="IS_LOGIN";


    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Private_Mode);
        editor=sharedPreferences.edit();
    }

    public void createLoginSession(){
       editor.putBoolean(LOGIN_KEY, true);
       editor.apply();
    }

    public boolean checkLogin(){
        return sharedPreferences.getBoolean(LOGIN_KEY,false);
   }

   public void signOut(){
        editor.clear();
        editor.apply();
  }
}
