package com.example.myapplication.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AppintmentUserAdapter;
import com.example.myapplication.adapters.ReminderListAdapter;
import com.example.myapplication.dilogs.LoaderDilog;
import com.example.myapplication.models.Appointment;
import com.example.myapplication.models.MedicineReminders;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;import android.util.Pair;

public class HomeFragment extends Fragment {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference dreference;
    LoaderDilog loaderDilog;
    TextView Name;
    private View rootView;
    ImageView profile_pic;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseStorage storage;
    StorageReference reference;
    StorageReference riversRef;
    ListView listView2;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity a = getActivity();
        sharedPreferences =  a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Gson gson = new Gson();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        List<MedicineReminders> reminders = new ArrayList<>();
        String json = sharedPreferences.getString("medicine_data",null);
        database  = FirebaseDatabase.getInstance();
        dreference = database.getReference("appointments");
        Type type = new TypeToken<List<MedicineReminders>>(){
        }.getType();
        reminders = gson.fromJson(json,type);
        if(reminders==null){
            replaceFragment(R.id.r_home_frame,new NoMed());
        }else{
            listView =(ListView) rootView.findViewById(R.id.reminderList);
            listView.setScrollContainer(false);
            inflateReminder(reminders);
        }
        profile_pic = rootView.findViewById(R.id.profile_pic_h);
        String getName = sharedPreferences.getString("name","User");
        user = FirebaseAuth.getInstance().getCurrentUser();
        loaderDilog = new LoaderDilog(a);
        editor = a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE).edit();
        Name =   rootView.findViewById(R.id.name_home_page);
        storage = FirebaseStorage.getInstance();
        reference  = storage.getReference();
        String imguri =  a.getSharedPreferences("PREFERENCE", a.MODE_PRIVATE).getString("imageuri",null);
        List<Pair<String,Appointment>> appointments = new ArrayList<>();
        Query remr = dreference.orderByChild("userId").equalTo(user.getUid());
        loaderDilog.startDilog();
        remr.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String id = dataSnapshot.getKey();
                            Appointment appointment  = dataSnapshot.getValue(Appointment.class);
                            appointments.add(new Pair(id,appointment));
                        }
                    }
                    if(appointments.size()==0){
//                        replaceFragment(R.id.a_home_frame,new NoMed());
                    }else{
                        listView2 =(ListView) rootView.findViewById(R.id.appointmentList);
                        listView2.setScrollContainer(false);
                        inflateReminder2(appointments);
                    }
                    loaderDilog.endDilog();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }
        );
        if(imguri!=null) {
            Picasso.get().load(Uri.parse(imguri)).into(profile_pic);
        }else{
            setImage();
        };
        Name.setText(getName);
        return rootView;
    }
    private void inflateReminder(List<MedicineReminders> reminders){
        ReminderListAdapter adapter = new ReminderListAdapter(getContext(),reminders);
        listView.setAdapter(adapter);
    }
    public void inflateReminder2 (List<Pair<String,Appointment>> reminders){
       AppintmentUserAdapter adapter = new AppintmentUserAdapter(getContext(),reminders);
       listView2.setAdapter(adapter);
    }

    private void replaceFragment(int id, Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.commit();
    }
    private  void setImage(){
        riversRef = reference.child("images/"+user.getUid()+".jpg");
        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_pic);
                editor.putString("imageuri",uri.toString());
                editor.apply();
            }
        });
    }
}