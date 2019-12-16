package com.example.expensetracker.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.Model.Item;
import com.example.expensetracker.R;
import io.realm.RealmList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    String primaryKeyDate;
    private Context context;
    private RealmDatabase realmDatabase;
    private RealmList<Item> items=new RealmList<>();
    Item item;

    public CustomAdapter(Context context, String primaryKeyDate) {
        this.primaryKeyDate=primaryKeyDate;
        this.context = context;

        realmDatabase = new RealmDatabase(context);
        this.items = realmDatabase.getAllBuyList(primaryKeyDate).getItemlist();
        this.item = new Item(); //We have done this cos in adapter if we have to use same object in different methods we have to initilaise like this
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_item_list,viewGroup,false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.customEditTextItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (items.size() == i+1 ) {
                    realmDatabase.addNewItemInBuyList(primaryKeyDate);
                }

                item.setItemname(s.toString());
                realmDatabase.updateItemInBuyList(primaryKeyDate, i, item);
            }
        });

      viewHolder.customEditTextItemQuantity.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override
          public void afterTextChanged(Editable s) {
            item.setItemqty(s.toString());
            realmDatabase.updateItemInBuyList(primaryKeyDate,i,item);
          }
      });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText customEditTextItemName;
        EditText customEditTextItemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customEditTextItemName=itemView.findViewById(R.id.customEditTextItemName);
            customEditTextItemQuantity=itemView.findViewById(R.id.customEditTextItemQuantity);
        }
    }
}
