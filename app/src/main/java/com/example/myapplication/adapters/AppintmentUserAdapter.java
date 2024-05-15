package com.example.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Appointment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AppintmentUserAdapter extends ArrayAdapter<Pair<String,Appointment>> {
    FirebaseDatabase database;
    DatabaseReference reference;
    public AppintmentUserAdapter(Context context, List<Pair<String,Appointment>> reminders) {
        super(context, 0,reminders);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<String,Appointment> item  = getItem(position);
        String id = item.first;
        Appointment appointment = item.second;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("appointments");
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_appointment_view,parent,false);
        }
        TextView name = convertView.findViewById(R.id.medi_name);
        TextView time = convertView.findViewById(R.id.medi_time);
        TextView date = convertView.findViewById(R.id.medi_date);
        TextView apppv = convertView.findViewById(R.id.isapp);
        if(appointment.isApproved()){
            apppv.setText("Approved");
        }else{
            apppv.setText("Pending");
        }
        convertView.findViewById(R.id.remapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                reference.child(id).removeValue();
                remove(item);
                notifyDataSetChanged();
            }
        });
        time.setText(appointment.getTime());
        date.setText(appointment.getDate());
        name.setText(appointment.getDoctorName());
        return convertView;
}
}
