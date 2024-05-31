package com.cb.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cb.foodapp.R;
import com.cb.foodapp.databinding.ActivitySignupBinding;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
    }
    private void setVariable(){
        binding.loginTxt.setOnClickListener(v->{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        binding.signupBtn.setOnClickListener(v->{
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            if (!email.isEmpty() || !password.isEmpty()){
                if (password.length() < 6) {
                    Toast.makeText(this, "Please enter password must be 6 character!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {

                    if (task.isComplete()){
                        startActivity(new Intent(this, MainActivity.class));
                        Toast.makeText(this, "Create account successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this, "Create account failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            } else{
                Toast.makeText(this, "Please fill username and password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}