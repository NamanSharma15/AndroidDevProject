package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.models.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText name_signup,email_signup,pass_signup;
    TextView login_redirect,dob_signup;
    Button signup;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        name_signup = findViewById(R.id.name_signup);
        email_signup = findViewById(R.id.email_signup);
        pass_signup = findViewById(R.id.password_signup);
        dob_signup = findViewById(R.id.dob_signup);
        signup = findViewById(R.id.signupbutton);
        login_redirect = findViewById(R.id.to_log);
        dob_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDOBPick();
            }
        });
        login_redirect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        }
    private void onDOBPick(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String m1,d1;
                        if(month+1<10){
                            m1 = "0"+(month+1);
                        }else{
                            m1 = String.valueOf(month+1);
                        }
                        if(dayOfMonth<10){
                            d1 = "0"+(dayOfMonth+1);
                        }else{
                            d1 = String.valueOf(dayOfMonth+1);
                        }
                        String date = d1 + "/" + m1 + "/" + year;
                        dob_signup.setText(date);
                    }
                },
                2024, 0, 1);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
    }
    public void onSignup(View view){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        String name =name_signup.getText().toString()  ;
        String email =email_signup.getText().toString()  ;
        String password =pass_signup.getText().toString()  ;
        String dob =dob_signup.getText().toString()  ;
        if(name.isEmpty()) {
            name_signup.setError("Name can't be empty");
        }
        if(email.isEmpty()) {
            email_signup.setError("Email can't be empty");
        }
        if(dob.isEmpty()) {
            dob_signup.setError("DOB can't be empty");
        }
        if(password.isEmpty()) {
            pass_signup.setError("Password can't be empty");
        }else{
        Users user = new Users(name,email,password,dob);
        reference.child(name).setValue(user);
        SharedPreferences sharedPreferences =  getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.putString("dob",dob);
        editor.putString("email",email);
        editor.apply();
        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(intent);
        }
    }
    }