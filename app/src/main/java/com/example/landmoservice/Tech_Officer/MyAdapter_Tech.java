package com.example.landmoservice.Tech_Officer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landmoservice.AcceptedVeiwActivity;
import com.example.landmoservice.Office_Member.Accept_Repair;
import com.example.landmoservice.Office_Member.Accept_Service;
import com.example.landmoservice.R;

import java.util.ArrayList;

public class MyAdapter_Tech extends RecyclerView.Adapter<MyAdapter_Tech.MyViewHolder> {
    private Context context;
    private ArrayList name_id, email_id, age_id ,RID_id;

    public MyAdapter_Tech(Context context, ArrayList name_id, ArrayList email_id, ArrayList age_id, ArrayList RID_id) {
        this.context = context;
        this.name_id = name_id;
        this.email_id = email_id;
        this.age_id = age_id;
        this.RID_id = RID_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_repair_request_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.email_id.setText(String.valueOf(email_id.get(position)));
        holder.age_id.setText(String.valueOf(age_id.get(position)));
        holder.RID_id.setText(String.valueOf(RID_id.get(position)));

        if(holder.age_id.getText().toString().equals("Complete")){

            holder.age_id.setTextColor(Color.GREEN);
        }
        else if(holder.age_id.getText().toString().equals("Accepted")){

            holder.age_id.setTextColor(Color.BLUE);

            holder.age_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id=holder.RID_id.getText().toString();
                    System.out.println(id);
                    String txt = id.substring(0, 2);
                    System.out.println(txt);

                    if(txt.equals("RI")) {
                        Intent intent = new Intent(context, RepairDitails.class);
                        intent.putExtra("RSID", id);
                        context.startActivity(intent);

                    }
                    if(txt.equals("SI")) {
                        Intent intent = new Intent(context, Accept_Service.class);
                        intent.putExtra("RSID", id);
                        context.startActivity(intent);

                    }

                }
            });
        }

        else{
            holder.age_id.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_id, email_id, age_id,RID_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.textname);
            email_id = itemView.findViewById(R.id.textemail);
            age_id = itemView.findViewById(R.id.textage);
            RID_id = itemView.findViewById(R.id.textrsid);

        }
    }
}