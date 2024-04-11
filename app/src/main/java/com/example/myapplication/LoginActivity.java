package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText name_login,pass_login;
    TextView signup_redirect;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name_login = findViewById(R.id.name_login);
        pass_login = findViewById(R.id.password_login);
        signup_redirect = findViewById(R.id.to_sign);
        signup_redirect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onLogin(View view){
        if(validateName() & validatePass()){
            checkUser();
        }
    }
    public Boolean validateName(){
        String val = name_login.getText().toString();
        if (val.isEmpty()){
            name_login.setError("Email cannot be empty");
            return false;
        } else {
            name_login.setError(null);
            return true;
        }
    }
    public Boolean validatePass(){
        String val = pass_login.getText().toString();
        if (val.isEmpty()){
            pass_login.setError("Email cannot be empty");
            return false;
        } else {
            pass_login.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String nameUser = name_login.getText().toString().trim();
        String passUser = pass_login.getText().toString().trim();
        database  = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        Toast.makeText(LoginActivity.this,"Working",Toast.LENGTH_LONG).show();
        Query checkUserDatabase = reference.orderByChild("name").equalTo(nameUser);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name_login.setError(null);
                    String passwordFromDB = snapshot.child(nameUser).child("password").getValue(String.class);
                    if(passwordFromDB.equals(passUser)){
                        pass_login.setError(null);
                        String name = snapshot.child(nameUser).child("name").getValue(String.class);
                        String dob = snapshot.child(nameUser).child("dob").getValue(String.class);
                        String email = snapshot.child(nameUser).child("email").getValue(String.class);
                        SharedPreferences sharedPreferences =  getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name",name);
                        editor.putString("dob",dob);
                        editor.putString("email",email);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        pass_login.setError("Invalid Credentials");
                        pass_login.requestFocus();
                    }
                }else{
                    name_login.setError("User Doesn't Exists");
                    name_login.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
