package com.example.expensetracker.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.expensetracker.Model.Item;
import com.example.expensetracker.R;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public ItemListAdapter(List<Item> itemList, Context context) {
    this.itemList=itemList;
    this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.itemlistcard,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Item itemList1= itemList.get(i);

        if (itemList1.getItemname()==null && itemList1.getItemqty()==null){
           viewHolder.itemListNameCardView.setVisibility(View.GONE);
           viewHolder.itemListQtyCardView.setVisibility(View.GONE);
        }
        else {
            viewHolder.itemListName.setText(itemList1.getItemname());
            viewHolder.itemListQty.setText(itemList1.getItemqty());

        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemListName;
        TextView itemListQty;
        CardView itemListNameCardView;
        CardView itemListQtyCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemListName=itemView.findViewById(R.id.itemListName);
            itemListQty=itemView.findViewById(R.id.itemListQty);
            itemListNameCardView=itemView.findViewById(R.id.itemListNameCardView);
            itemListQtyCardView=itemView.findViewById(R.id.itemListQtyCardView);

        }
    }
}