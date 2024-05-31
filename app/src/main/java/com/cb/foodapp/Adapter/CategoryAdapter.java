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
import com.cb.foodapp.Activity.ListFoodsActivity;
import com.cb.foodapp.Domain.Category;
import com.cb.foodapp.Domain.Foods;
import com.cb.foodapp.R;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<Category> items;
    Context context;

    public CategoryAdapter(ArrayList<Category> Category) {
        this.items = Category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category item = items.get(position);
        switch (position){
            case 0:
                holder.picCat.setBackgroundResource(R.drawable.cat0_bg);
                break;
            case 1:
                holder.picCat.setBackgroundResource(R.drawable.cat1_bg);
                break;
            case 2:
                holder.picCat.setBackgroundResource(R.drawable.cat2_bg);
                break;
            case 3:
                holder.picCat.setBackgroundResource(R.drawable.cat3_bg);
                break;
            case 4:
                holder.picCat.setBackgroundResource(R.drawable.cat4_bg);
                break;
            case 5:
                holder.picCat.setBackgroundResource(R.drawable.cat5_bg);
                break;
            case 6:
                holder.picCat.setBackgroundResource(R.drawable.cat6_bg);
                break;
            case 7:
                holder.picCat.setBackgroundResource(R.drawable.cat7_bg);
                break;
        }
        int drawableResourceId = context.getResources().getIdentifier(item.getImagePath(), "drawable", holder.itemView.getContext().getPackageName());
        holder.catNameTxt.setText(item.getName());
        Glide.with(context).load(drawableResourceId).into(holder.picCat);
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, ListFoodsActivity.class);
            intent.putExtra("CategoryId", item.getId());
            intent.putExtra("CategoryName", item.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView picCat;
        TextView catNameTxt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picCat = itemView.findViewById(R.id.picCat);
            catNameTxt = itemView.findViewById(R.id.catNameTxt);

        }
    }
}
