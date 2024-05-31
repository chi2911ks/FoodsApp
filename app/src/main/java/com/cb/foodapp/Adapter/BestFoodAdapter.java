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
import com.cb.foodapp.Helper.ManagmentCart;
import com.cb.foodapp.R;
import com.google.android.material.shape.CornerSize;

import java.util.ArrayList;


public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.ViewHolder> {
    ArrayList<Foods> itemsFood;
    Context context;
    ManagmentCart managmentCart;

    public BestFoodAdapter(Context context, ArrayList<Foods> itemsFood) {
        this.itemsFood = itemsFood;
        this.context = context;
        managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_bestfood_item, parent, false);
        return new BestFoodAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods item = itemsFood.get(position);
        holder.timeTxt.setText(item.getTimeValue()+" min");
        holder.titleTxt.setText(item.getTitle());
        holder.priceTxt.setText("$"+item.getPrice());
        holder.starTxt.setText(String.valueOf(item.getStar()));
        Glide.with(context).load(item.getImagePath()).transform(new CenterCrop(), new RoundedCorners(30)).into(holder.pic);
        holder.addFoodTxt.setOnClickListener(v->{
            item.setNumberInCart(item.getNumberInCart()+1);
            managmentCart.insertFood(item);
        });
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemsFood.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView addFoodTxt;
        TextView titleTxt;
        TextView starTxt;
        TextView timeTxt;
        TextView priceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            addFoodTxt = itemView.findViewById(R.id.addFoodTxt);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            starTxt = itemView.findViewById(R.id.starTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }
    }
}
