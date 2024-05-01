package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ReminderListAdapter;
import com.example.myapplication.models.MedicineReminders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView listView;
    TextView Name;
    private View rootView;
    SharedPreferences sharedPreferences;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity a = getActivity();
        sharedPreferences =  a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Gson gson = new Gson();
        List<MedicineReminders> reminders = new ArrayList<>();
        String json = sharedPreferences.getString("medicine_data",null);
        Type type = new TypeToken<List<MedicineReminders>>(){
        }.getType();
        reminders = gson.fromJson(json,type);
        if(reminders==null){
            replaceFragment(new NoMed());
        }else{
            listView =(ListView) rootView.findViewById(R.id.reminderList);
            inflateReminder(reminders);
        }
        String getName = sharedPreferences.getString("name","User");
        Name =   rootView.findViewById(R.id.name_home_page);
        Name.setText(getName);
        return rootView;
    }
    public void inflateReminder(List<MedicineReminders> reminders){
        ReminderListAdapter adapter = new ReminderListAdapter(getContext(),reminders);
        listView.setAdapter(adapter);
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.r_home_frame,fragment);
        fragmentTransaction.commit();
    }
}