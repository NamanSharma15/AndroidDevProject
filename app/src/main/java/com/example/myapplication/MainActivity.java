package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.fragments.ConsultFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.MedFragment;
import com.example.myapplication.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity{
    private static final int PERMISSION_REQUEST_NOT_CODE = 100;
    ActivityMainBinding binding;
    String notLogined;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Bundle b = getIntent().getExtras();
        int value = -1;
        if(b != null)
            value = b.getInt("tab");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    PERMISSION_REQUEST_NOT_CODE);
        }
        SharedPreferences sharedPreferences =  getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notLogined = sharedPreferences.getString("email", null);
        if(notLogined==null){
        Intent intent = new Intent(MainActivity.this,SignupActivity.class);
        startActivity(intent);
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
        if(value==2){
        replaceFragment(new MedFragment());
        binding.bottomNavigationBar.setSelectedItemId(R.id.med);
        }else{
        replaceFragment(new HomeFragment());
        }
        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_p:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.consult:
                    replaceFragment(new ConsultFragment());
                    break;
                case R.id.med:
                    replaceFragment(new MedFragment());
                    break;
            }
            return  true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
    public void onLogOut(View view){
        SharedPreferences sharedPreferences =  getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.apply();
        Intent intent = new Intent(MainActivity.this,SignupActivity.class);
        startActivity(intent);
    }
}