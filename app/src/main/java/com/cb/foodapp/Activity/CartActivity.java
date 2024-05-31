package com.cb.foodapp.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.foodapp.Adapter.CartAdapter;
import com.cb.foodapp.Helper.ChangeNumberItemsListener;
import com.cb.foodapp.Helper.ManagmentCart;
import com.cb.foodapp.R;
import com.cb.foodapp.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity implements ChangeNumberItemsListener{
    ActivityCartBinding binding;
    ManagmentCart managmentCart;
    double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);
        setVariable();
        initList();
        change();

    }
    private void initList(){
        binding.rcCart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        CartAdapter adapter = new CartAdapter(getApplicationContext(), managmentCart.getListCart(), this);
        binding.rcCart.setAdapter(adapter);
    }
    @SuppressLint("SetTextI18n")
    public void change(){
        double percentTax = 0.2;
        double delivery = 10;
        tax = (double) Math.round(managmentCart.getTotalFee() * percentTax * 100.0) /100;
        double total = (double) Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal = (double) Math.round(managmentCart.getTotalFee()*100) / 100;
        binding.subtotalTxt.setText("$"+itemTotal);
        binding.totalTaxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.totalOrderTxt.setText("$"+total);
        if (managmentCart.getListCart().isEmpty()){
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.linearCart.setVisibility(View.GONE);
        }else{
            binding.emptyCart.setVisibility(View.GONE);
            binding.linearCart.setVisibility(View.VISIBLE);
        }
    }
    private void setVariable(){
        binding.backCartBtn.setOnClickListener(v->finish());
    }


}