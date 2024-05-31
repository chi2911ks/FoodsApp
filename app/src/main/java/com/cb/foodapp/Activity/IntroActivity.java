package com.cb.foodapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cb.foodapp.R;
import com.cb.foodapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkUser();
        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
    }
    private void checkUser(){
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
    private void setVariable(){
        binding.textViewLogin.setOnClickListener(v->startActivity(new Intent(this, LoginActivity.class)));
        binding.textViewSignup.setOnClickListener(v->startActivity(new Intent(this, SignupActivity.class)));
    }
}