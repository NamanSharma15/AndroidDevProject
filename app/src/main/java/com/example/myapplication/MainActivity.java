package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.fragments.ConsultFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.MedFragment;
import com.example.myapplication.fragments.ProfileFragment;
import com.example.myapplication.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

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
                case R.id.set:
                    replaceFragment(new SettingsFragment());
                    break;
                case R.id.med:
                    replaceFragment(new MedFragment());
                    break;
            }
            return  true;
        });
    }
    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}