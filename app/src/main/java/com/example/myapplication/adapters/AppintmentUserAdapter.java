package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Appointment;

import java.util.List;

public class AppintmentUserAdapter extends ArrayAdapter<Appointment> {
    public AppintmentUserAdapter(Context context, List<Appointment> reminders) {
        super(context, 0,reminders);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Appointment appointment = getItem(position);
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
        time.setText(appointment.getTime());
        date.setText(appointment.getDate());
        name.setText(appointment.getDoctorName());
        return convertView;
}
}
