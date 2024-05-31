package com.cb.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cb.foodapp.Adapter.BestFoodAdapter;
import com.cb.foodapp.Adapter.CategoryAdapter;
import com.cb.foodapp.Domain.Category;
import com.cb.foodapp.Domain.Foods;
import com.cb.foodapp.Domain.Location;
import com.cb.foodapp.Domain.Price;
import com.cb.foodapp.Domain.Time;
import com.cb.foodapp.R;
import com.cb.foodapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    ArrayList<Location> listLocation;
    ArrayList<Price> listPrice;
    ArrayList<Time> listTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initLocation();
        initPrice();
        initTime();
        initBestFood();
        initCategory();
        setVariale();
    }
    private void setVariale(){
        binding.logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

        });
        binding.searchBtn.setOnClickListener(v -> {
            String searchText = binding.searchEdText.getText().toString();
            if (!searchText.isEmpty()) {
                Intent intent = new Intent(this, ListFoodsActivity.class);
                intent.putExtra("searchText", searchText);
                intent.putExtra("isSearch", true);
                startActivity(intent);

            }
        });
        binding.filterBtn.setOnClickListener(v->{

            int LocationId = listLocation.get(binding.locationSp.getSelectedItemPosition()).getId();
            int PriceId = listPrice.get(binding.priceSp.getSelectedItemPosition()).getId();
            int TimeId = listTime.get(binding.timeSp.getSelectedItemPosition()).getId();
            Intent intent = new Intent(this, ListFoodsActivity.class);
            intent.putExtra("LocationId", LocationId);
            intent.putExtra("PriceId", PriceId);
            intent.putExtra("TimeId", TimeId);
            intent.putExtra("isFilter", true);
            startActivity(intent);
        });
        binding.cartBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });
        binding.viewAllBtn.setOnClickListener(v->{
            Intent intent = new Intent(this, ListFoodsActivity.class);
            intent.putExtra("isViewAll", true);
            startActivity(intent);
        });
    }
    private void initLocation(){
        DatabaseReference myRef = database.getReference("Location");
        listLocation = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        listLocation.add(issue.getValue(Location.class));

                    }
                    ArrayAdapter<Location> adapterLocation = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, listLocation);

                    adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    binding.locationSp.setAdapter(adapterLocation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initPrice(){
        DatabaseReference myRef = database.getReference("Price");
        listPrice = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        listPrice.add(issue.getValue(Price.class));

                    }
                    ArrayAdapter<Price> adapterPrice = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, listPrice);
                    adapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.priceSp.setAdapter(adapterPrice);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initTime(){
        DatabaseReference myRef = database.getReference("Time");
        listTime = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        listTime.add(issue.getValue(Time.class));

                    }
                    ArrayAdapter<Time> adapterTime = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, listTime);
                    adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.timeSp.setAdapter(adapterTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initBestFood(){
        binding.rcBestFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.loadingBestFood.setVisibility(View.VISIBLE);
        DatabaseReference myRef = database.getReference("Foods");
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }
                    BestFoodAdapter adapter = new BestFoodAdapter(getApplicationContext(), list);
                    binding.rcBestFood.setAdapter(adapter);
                    binding.loadingBestFood.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initCategory(){
        binding.rcCategory.setLayoutManager(new GridLayoutManager(this, 4));
        binding.loadingCategory.setVisibility(View.VISIBLE);
        DatabaseReference myRef = database.getReference("Category");
        ArrayList<Category> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        list.add(issue.getValue(Category.class));
                    }
                    CategoryAdapter adapter = new CategoryAdapter(list);
                    binding.rcCategory.setAdapter(adapter);
                    binding.loadingCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}