package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.models.Appointment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.play.integrity.internal.f;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.UUID;

public class BookAppointmentActivity extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference reference;
    StorageReference riversRef;
    FirebaseAuth auth;
    FirebaseUser user;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    DatabaseReference dreference;
    String Date;
    String uid;
    TextView timeview;
    String did;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        did = sharedPreferences.getString("did",null);
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        TextView name = findViewById(R.id.dnamedf);
        TextView spec = findViewById(R.id.dspecdf);
        database = FirebaseDatabase.getInstance();
        dreference = database.getReference("appointments");
        name.setText(sharedPreferences.getString("dname","Doctor"));
        spec.setText(sharedPreferences.getString("dspec",""));
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        ImageView profile_p = findViewById(R.id.profilebk);
        CalendarView calendarView = findViewById(R.id.book_date);
        Date = "01/0/2024";
        String parts[] = Date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        calendarView.setDate(milliTime);
        timeview = findViewById(R.id.time_text);
        findViewById(R.id.sub_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit(sharedPreferences.getString("dname","Doctor"));
            }
        });
        findViewById(R.id.backinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });
        findViewById(R.id.select_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(BookAppointmentActivity.this , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hours, int minute) {
                        boolean isPM = (hours>= 12);
                        timeview.setText(String.format("%02d:%02d %s", (hours == 12 || hours == 0) ? 12 : hours % 12, minute, isPM ? "PM" : "AM"));
                    }
                },12,00,false);
                timePickerDialog.show();
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String m1,d1;
                if(month+1<10){
                    m1 = "0"+(month+1);
                }else{
                    m1 = String.valueOf(month+1);
                }
                if(dayOfMonth<10){
                    d1 = "0"+(dayOfMonth);
                }else{
                    d1 = String.valueOf(dayOfMonth);
                }
                String date = d1 + "/" + m1 + "/" + year;
                setDate(date);
            }
        });
        setImage(did,profile_p);
    }
    private void setDate(String new_date){
        Date = new_date;
    }
    private  void setImage(String id, ImageView profile_pic){
        riversRef = reference.child("images/"+id+".jpg");
        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_pic);
            }
        });
    }
    public void Back(){
        finish();
    }
    void onSubmit(String name){
        String userName = sharedPreferences.getString("name","User");
        String getTime = timeview.getText().toString();
        Appointment appointment  = new Appointment(Date,getTime,userName,uid,name,did,false);
        UUID uuid = UUID.randomUUID();
        dreference.child(uuid.toString()).setValue(appointment);
        Back();
    }
}