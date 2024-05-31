package com.cb.foodapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cb.foodapp.Adapter.FoodListAdapter;
import com.cb.foodapp.Domain.Category;
import com.cb.foodapp.Domain.Foods;
import com.cb.foodapp.R;
import com.cb.foodapp.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodsActivity extends BaseActivity {
    ActivityListFoodBinding binding;
    int CategoryId;
    String CategoryName;
    String searchText;
    boolean isSearch;
    int LocationId, TimeId, PriceId;
    boolean isFilter;
    boolean isViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        searchText = "";
        getIntentExtra();
        binding.btnBack.setOnClickListener(v->finish());
        binding.titleLFoodTxt.setText(CategoryName);
        initList();
    }
    private void getIntentExtra(){
        CategoryId = getIntent().getIntExtra("CategoryId", 0);
        CategoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("searchText");
        isSearch = getIntent().getBooleanExtra("isSearch", false);
        LocationId = getIntent().getIntExtra("LocationId", 0);
        TimeId = getIntent().getIntExtra("TimeId", 0);
        PriceId = getIntent().getIntExtra("PriceId", 0);
        isFilter = getIntent().getBooleanExtra("isFilter", false);
        isViewAll = getIntent().getBooleanExtra("isViewAll", false);
    }
    private void initList(){
        binding.rcListFood.setLayoutManager(new GridLayoutManager(this, 2));
        binding.loadingListFood.setVisibility(View.VISIBLE);
        DatabaseReference myRef = database.getReference("Foods");
        ArrayList<Foods> list = new ArrayList<>();
        Query query;
        if (!isViewAll){
            if (isSearch){
                query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText+'\uf8ff');
            } else{
                query = myRef.orderByChild("CategoryId").equalTo(CategoryId);
            }
        }else{
            query = myRef;
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        Foods item = issue.getValue(Foods.class);
                        if (isFilter){
                            if (item != null && item.getLocationId() == LocationId && item.getPriceId() == PriceId && item.getTimeId() == TimeId){
                                list.add(item);
                            }
                        }
                        else list.add(item);

                    }
                    binding.loadingListFood.setVisibility(View.GONE);
                    FoodListAdapter adapter = new FoodListAdapter(list);
                    binding.rcListFood.setAdapter(adapter);
                }else{
                    binding.loadingListFood.setVisibility(View.GONE);
                    binding.searchNotFound.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}