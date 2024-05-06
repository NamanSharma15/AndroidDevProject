package com.example.myapplication.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ApproveAppointAdapter;
import com.example.myapplication.adapters.MedicalRemAdapter;
import com.example.myapplication.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference dreference;
    FirebaseUser user;
    FirebaseAuth auth;
    Activity a;

    public AppointmentsFragment() {
    }
    public static AppointmentsFragment newInstance(String param1, String param2) {
        AppointmentsFragment fragment = new AppointmentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth  = FirebaseAuth.getInstance();
        user  = auth.getCurrentUser();
        a = getActivity();
        View rootView =  inflater.inflate(R.layout.fragment_appointments, container, false);
        listView = rootView.findViewById(R.id.upapp);
        List<Pair<String,Appointment>> appointments = new ArrayList<>();
        database  = FirebaseDatabase.getInstance();
        dreference = database.getReference("appointments");
        Query remr = dreference.orderByChild("doctorId").equalTo(user.getUid());
        remr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String id = dataSnapshot.getKey();
                    Appointment appointment  = dataSnapshot.getValue(Appointment.class);
                    appointments.add(new Pair(id,appointment));
                }
                inflateReminder(appointments);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }
    private void inflateReminder(List<Pair<String,Appointment>> appointments){
        ApproveAppointAdapter adapter = new ApproveAppointAdapter(getContext(),appointments,getActivity(),dreference);
        listView.setAdapter(adapter);
    }
}