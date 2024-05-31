package com.cb.foodapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.cb.foodapp.Activity.DetailActivity;
import com.cb.foodapp.Domain.Foods;
import com.cb.foodapp.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    Context context;
    ArrayList<Foods> items;

    public FoodListAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_listfood, parent, false);
        return new FoodListAdapter.ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods item = items.get(position);
        holder.titleFood.setText(item.getTitle());
        holder.timeFood.setText(item.getTimeValue()+" min");
        holder.priceFood.setText("$"+item.getPrice());
        holder.starFood.setText(""+item.getStar());
        Glide.with(context).load(item.getImagePath()).transform(new CenterCrop(), new RoundedCorners(30)).into(holder.pic);
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView titleFood, timeFood, starFood, priceFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.picLF);
            titleFood = itemView.findViewById(R.id.titleLFTxt);
            timeFood = itemView.findViewById(R.id.timeLFTxt);
            starFood = itemView.findViewById(R.id.starLFTxt);
            priceFood = itemView.findViewById(R.id.priceLFTxt);

        }
    }
}
