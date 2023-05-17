package com.example.landmoservice.Office_Member;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.landmoservice.AddRepairActivity;
import com.example.landmoservice.DBHelper;
import com.example.landmoservice.MainActivity;
import com.example.landmoservice.R;

public class AddCustomerActivity extends AppCompatActivity {
Button Register,update,search,cancel;
EditText NIC,Cname,Cbd,Ccity,Cstate,Cpassword,Cmobile,searchnic;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        getSupportActionBar().hide();


        Register=findViewById(R.id.Registerbtn);
        update=findViewById(R.id.Updatebtn);
        search=findViewById(R.id.search);
        cancel=findViewById(R.id.cancelbtn);

        update.setEnabled(false);

        DB = new DBHelper(this);

        NIC=findViewById(R.id.Cnic);
        Cname=findViewById(R.id.Cname);
        Cbd=findViewById(R.id.Cbd);
        Ccity=findViewById(R.id.Ccity);
        Cstate=findViewById(R.id.Cstate);
        Cpassword=findViewById(R.id.Cpassword);
        Cmobile=findViewById(R.id.Cmobile);
        searchnic=findViewById(R.id.Searchnic);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setEnabled(true);
                NIC.setEnabled(false);
                Cursor res = DB.Customer_getdata(searchnic.getText().toString());
                if(res.getCount()==0){

                    Cmobile.setText("");
                    Cpassword.setText("");
                    Cstate.setText("");
                    NIC.setText("");
                    Cname.setText("");
                    Cbd.setText("");
                    Ccity.setText("");
                    Toast.makeText(AddCustomerActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){


                    Cmobile.setText(res.getString(6).toString());
                    Cpassword.setText(res.getString(5).toString());
                    Cstate.setText(res.getString(4).toString());
                    NIC.setText(res.getString(0).toString());
                    Cname.setText(res.getString(1).toString());
                    Cbd.setText(res.getString(2).toString());
                    Ccity.setText(res.getString(3).toString());

                    Register.setEnabled(false);


                }
                Toast.makeText(AddCustomerActivity.this, "Entry Exists", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddCustomerActivity.this, "Update Cancel", Toast.LENGTH_SHORT).show();

                Cmobile.setText("");
                Cpassword.setText("");
                Cstate.setText("");
                NIC.setText("");
                Cname.setText("");
                Cbd.setText("");
                Ccity.setText("");
                searchnic.setText("");
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean insertrepair = DB.insert_customer(
                         NIC.getText().toString()
                        ,Cname.getText().toString()
                        ,Cbd.getText().toString()
                        ,Ccity.getText().toString()
                        ,Cstate.getText().toString()
                        ,Cpassword.getText().toString()
                        ,Cmobile.getText().toString());
                if(insertrepair==true) {
                    Cmobile.setText("");
                    Cpassword.setText("");
                    Cstate.setText("");
                    NIC.setText("");
                    Cname.setText("");
                    Cbd.setText("");
                    Ccity.setText("");
                    searchnic.setText("");

                    Toast.makeText(AddCustomerActivity.this, "New Customer Inserted", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(AddCustomerActivity.this, "New Coustomer Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkupdatedata = DB.updatecustomer(
                         NIC.getText().toString()
                        ,Cname.getText().toString()
                        ,Cbd.getText().toString()
                        ,Ccity.getText().toString()
                        ,Cstate.getText().toString()
                        ,Cpassword.getText().toString()
                        ,Cmobile.getText().toString());
                if(checkupdatedata==true) {
                    Cmobile.setText("");
                    Cpassword.setText("");
                    Cstate.setText("");
                    NIC.setText("");
                    Cname.setText("");
                    Cbd.setText("");
                    Ccity.setText("");
                    searchnic.setText("");
                    Toast.makeText(AddCustomerActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AddCustomerActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

            }
        });


    }
}