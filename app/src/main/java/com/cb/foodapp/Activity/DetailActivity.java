package com.cb.foodapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.cb.foodapp.Domain.Foods;
import com.cb.foodapp.Helper.ManagmentCart;
import com.cb.foodapp.R;
import com.cb.foodapp.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        setVariable();
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

    }

    @SuppressLint("SetTextI18n")
    private void setVariable(){
        managmentCart = new ManagmentCart(getApplicationContext());
        binding.backBtn.setOnClickListener(v->finish());
        binding.favoriteBtn.setOnClickListener(v-> {

        });
        binding.subBtn.setOnClickListener(v->{
            if (num <= 0) return;
            num--;
            binding.numTxt.setText(""+num);
            binding.totalTxt.setText("$"+object.getPrice()*num);
        });
        binding.addBtn.setOnClickListener(v->{
            num++;
            binding.numTxt.setText(""+num);
            binding.totalTxt.setText("$"+object.getPrice()*num);
        });
        Glide.with(this).load(object.getImagePath()).into(binding.picFood);
        binding.titleFood.setText(object.getTitle());
        binding.timeFoodTxt.setText(object.getTimeValue()+" min");
        binding.rateFood.setRating((float) object.getStar());
        binding.rateTxt.setText(object.getStar() +" Rating");
        binding.descriptionTxt.setText(object.getDescription());
        binding.priceFoodTxt.setText("$"+object.getPrice());
        binding.numTxt.setText(""+num);
        binding.totalTxt.setText("$"+(object.getPrice()*num));
        binding.addCartBtn.setOnClickListener(v->{
            object.setNumberInCart(object.getNumberInCart()+num);
            managmentCart.insertFood(object);
        });

    }
    private void getIntentExtra(){
        Intent myIntent = getIntent();
        object = (Foods) myIntent.getSerializableExtra("object");
    }
}