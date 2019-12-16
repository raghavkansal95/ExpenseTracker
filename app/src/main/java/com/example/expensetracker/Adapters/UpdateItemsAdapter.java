package com.example.expensetracker.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.expensetracker.Database.RealmDatabase;
import com.example.expensetracker.Model.BuyList;
import com.example.expensetracker.Model.Item;
import com.example.expensetracker.R;
import io.realm.RealmList;

public class UpdateItemsAdapter extends RecyclerView.Adapter<UpdateItemsAdapter.ViewHolder> {
    String PrimaryKeyDate;
    private Context context;
    private RealmList<Item> itemList;
    private RealmDatabase realmDatabase;
    Item item;

    public UpdateItemsAdapter(String PrimaryKeyDate, Context context){
        this.PrimaryKeyDate=PrimaryKeyDate;
        this.context=context;
        this.item=new Item();

        realmDatabase=new RealmDatabase(context);
        itemList= realmDatabase.getAllBuyList(PrimaryKeyDate).getItemlist();
    }

    @NonNull
    @Override
    public UpdateItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.edititemlistcard,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateItemsAdapter.ViewHolder viewHolder, final int i) {
       final Item item1=itemList.get(i);

        if (item1.getItemname()==null && item1.getItemqty()==null){
            viewHolder.EdititemListNameCardView.setVisibility(View.GONE);
            viewHolder.EdititemListQtyCardView.setVisibility(View.GONE);
        }
        else {
            viewHolder.EdititemListName.setText(item1.getItemname());
            viewHolder.EdititemListQty.setText(item1.getItemqty());

            viewHolder.EdititemListName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (itemList.size()== i+1){
                        realmDatabase.addNewItemInBuyList(PrimaryKeyDate);
                    }

                    item.setItemname(s.toString());
                    realmDatabase.updateItemInBuyList(PrimaryKeyDate, i, item);
                }
            });

            viewHolder.EdititemListQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setItemqty(s.toString());
                    realmDatabase.updateItemInBuyList(PrimaryKeyDate,i,item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText EdititemListName;
        EditText EdititemListQty;
        CardView EdititemListNameCardView;
        CardView EdititemListQtyCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           EdititemListName= itemView.findViewById(R.id.EdititemListName);
           EdititemListQty=itemView.findViewById(R.id.EdititemListQty);
           EdititemListNameCardView=itemView.findViewById(R.id.EdititemListNameCardView);
           EdititemListQtyCardView=itemView.findViewById(R.id.EdititemListQtyCardView);
        }

    }
}
