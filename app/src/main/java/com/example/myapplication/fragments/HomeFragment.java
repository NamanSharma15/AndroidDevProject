package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ReminderListAdapter;
import com.example.myapplication.models.MedicineReminders;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView listView;
    private View rootView;
    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        List<MedicineReminders> reminders = new ArrayList<>();
        listView =(ListView) rootView.findViewById(R.id.reminderList);
        MedicineReminders m1 = new MedicineReminders("Drindol","2 times a day");
        MedicineReminders m2 = new MedicineReminders("paracetamol","3 times a day");
        reminders.add(m1);
        reminders.add(m2);
        inflateReminder(reminders);
        return rootView;
    }
    public void inflateReminder(List<MedicineReminders> reminders){
        ReminderListAdapter adapter = new ReminderListAdapter(getContext(),reminders);
        listView.setAdapter(adapter);
    }
}