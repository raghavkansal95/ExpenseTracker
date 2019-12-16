package com.example.expensetracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracker.Activity.ItemListActivity;
import com.example.expensetracker.Model.BuyList;
import com.example.expensetracker.R;

import java.util.List;

public class BuyListAdapter extends RecyclerView.Adapter<BuyListAdapter.ViewHolder> {

    private List<BuyList> buyList;
    private  Context context;

    public BuyListAdapter(List<BuyList> buyList, Context context) {
               this.buyList=buyList;
               this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View v=layoutInflater.inflate(R.layout.buylist,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BuyList buyList1=buyList.get(i);

        viewHolder.buyListName.setText(buyList1.getName());
        viewHolder.buyListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       Intent intent=new Intent(v.getContext(), ItemListActivity.class);
                       intent.putExtra("primaryKeyDate", buyList1.getDate());
                       v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return buyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       TextView buyListName;
       CardView buyListCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buyListName=itemView.findViewById(R.id.buyListName);
            buyListCardView=itemView.findViewById(R.id.buyListCardView);
        }
    }
}
