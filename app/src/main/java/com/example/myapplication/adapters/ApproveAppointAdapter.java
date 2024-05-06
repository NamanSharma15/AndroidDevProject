package com.example.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.DoctorMainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Appointment;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ApproveAppointAdapter extends ArrayAdapter<Pair<String,Appointment>> {
    Activity a;
    DatabaseReference dreference;
    public ApproveAppointAdapter(Context context, List<Pair<String,Appointment>> reminders, Activity a,DatabaseReference reference) {
        super(context, 0,reminders);
        this.dreference = reference;
        this.a = a;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pair<String,Appointment> item = getItem(position);
        String id = item.first;
        Appointment appointment  = item.second;
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctorapr_view,parent,false);
        }
        TextView name = convertView.findViewById(R.id.medi_name);
        TextView time = convertView.findViewById(R.id.medi_time);
        TextView date = convertView.findViewById(R.id.medi_date);
        TextView appr =  new TextView(getContext());
        appr.setText("Approved");
        FrameLayout frameLayout = convertView.findViewById(R.id.rightbox);
        if(appointment.isApproved()){
            frameLayout.removeAllViews();
            frameLayout.addView(appr);
        }else{
            frameLayout.removeAllViews();
            ImageView apppv = new ImageView(getContext());
            float scale = getContext().getResources().getDisplayMetrics().density;
            int widthdp = (int) (25 * scale + 0.5f);
            int heightdp = (int) (25 * scale + 0.5f);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthdp, heightdp);
            apppv.setLayoutParams(layoutParams);
            apppv.setImageResource(R.drawable.checkmark);
            frameLayout.addView(apppv);
        apppv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dreference.child(id).child("approved").setValue(true);
                Intent intent  = new Intent(getContext(), DoctorMainActivity.class);
                a.startActivity(intent);
            }
        });}
        time.setText(appointment.getTime());
        date.setText(appointment.getDate());
        name.setText(appointment.getUserName());
        return convertView;
    }
}
