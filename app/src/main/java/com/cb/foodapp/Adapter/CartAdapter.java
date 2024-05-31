package com.cb.foodapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.cb.foodapp.Domain.Foods;
import com.cb.foodapp.Helper.ChangeNumberItemsListener;
import com.cb.foodapp.Helper.ManagmentCart;
import com.cb.foodapp.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<Foods> items;
    ManagmentCart managmentCart;
    ChangeNumberItemsListener changeItems;

    public CartAdapter(Context context, ArrayList<Foods> items, ChangeNumberItemsListener changeItems) {
        this.context = context;
        managmentCart = new ManagmentCart(context);
        this.items = items;
        this.changeItems = changeItems;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Foods item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.price.setText(item.getNumberInCart()+" x $"+item.getPrice());
        holder.total.setText("$"+item.getNumberInCart()*item.getPrice());
        holder.num.setText(item.getNumberInCart()+"");
        Glide.with(context).load(item.getImagePath()).transform(new CenterCrop(), new RoundedCorners(30)).into(holder.pic);
        holder.add.setOnClickListener(v->{
            managmentCart.plusNumberItem(items, position, () -> {
                notifyDataSetChanged();
                changeItems.change();
            });
        });
        holder.sub.setOnClickListener(v->{
            managmentCart.minusNumberItem(items, position, () -> {
                notifyDataSetChanged();
                changeItems.change();
            });
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView title, price, add, sub, num, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.picProduct);
            title = itemView.findViewById(R.id.titleProduct);
            price = itemView.findViewById(R.id.priceProduct);
            add = itemView.findViewById(R.id.addProductBtn);
            sub = itemView.findViewById(R.id.subProductBtn);
            num = itemView.findViewById(R.id.numProduct);
            total = itemView.findViewById(R.id.totalProduct);
        }
    }
}
