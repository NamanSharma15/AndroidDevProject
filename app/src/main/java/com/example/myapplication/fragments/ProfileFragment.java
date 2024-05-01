package com.example.myapplication.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ProfileFragment extends Fragment {
    private View rootView;
    TextView Name,DOB,Email,Age;
    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity a = getActivity();
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        String getName = a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE).getString("name","User");
        String getEmail = a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE).getString("email","abc@mail.com");
        String getDOB = a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE).getString("dob","11/01/2004");
        Name =   rootView.findViewById(R.id.name_profile_page);
        Email =   rootView.findViewById(R.id.email_profile);
        DOB =   rootView.findViewById(R.id.dob_profile);
        Age =   rootView.findViewById(R.id.age_profile);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dob_s = LocalDate.parse(getDOB,formatter);
        String getAge = Integer.toString(calculateAge(dob_s));
        Name.setText(getName);
        Email.setText(getEmail);
        DOB.setText(getDOB);
        Age.setText(getAge);
        return rootView;
    }
    public int calculateAge(LocalDate dob_s)
    {
        LocalDate curDate = LocalDate.now();
        if ((dob_s != null) && (curDate != null))
        {
            return Period.between(dob_s, curDate).getYears();
        }
        else
        {
            return 0;
        }
    }
}