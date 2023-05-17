package com.example.landmoservice.Tech_Officer;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.landmoservice.DBHelper;
import com.example.landmoservice.R;
import com.example.landmoservice.editUserProfile;

public class EditTechOffice extends AppCompatActivity {

    Button update, cancel;
    //EditText NIC,Cname,Cbd,Ccity,Cstate,Cpassword,Cmobile;
    EditText employeeId,NIC,eName,eAddress,eMobile,ePassword,ePosition;
    DBHelper DB;

    String nic;

//    String[] items =  {
//            "Office Member",
//            "Technical Officer",
//    };
//    AutoCompleteTextView autoCompleteTxt;
//    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tech_office);

        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        nic=bundle.getString("nic");

        DB = new DBHelper(this);

        update=findViewById(R.id.Updatebtn);
        cancel=findViewById(R.id.cancelbtn);

        employeeId=findViewById(R.id.Eid);
        NIC=findViewById(R.id.Enic);
        eName=findViewById(R.id.Ename);
        eAddress=findViewById(R.id.Eaddress);
        eMobile=findViewById(R.id.Emobile);
        ePosition=findViewById(R.id.Eposition);
        ePassword=findViewById(R.id.Epw);

        NIC.setEnabled(false);
        eName.setEnabled(false);
        employeeId.setEnabled(false);
        ePosition.setEnabled(false);

//        autoCompleteTxt = findViewById(R.id.Vtype);
//
//        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
//        autoCompleteTxt.setAdapter(adapterItems);
//
//        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
//            }
//        });

        displaydata(nic);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkupdatedata = DB.updateTechOfficer_byHimself(
                        NIC.getText().toString()
                        ,eAddress.getText().toString()
                        ,eMobile.getText().toString()
                        ,ePassword.getText().toString());

                if(checkupdatedata==true) {
                    NIC.setText("");
                    eName.setText("");
                    eAddress.setText("");
                    eMobile.setText("");
                    ePosition.setText("");
                    ePassword.setText("");
                    Toast.makeText(EditTechOffice.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(EditTechOffice.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(EditTechOffice.this, "Cleared", Toast.LENGTH_SHORT).show();
                NIC.setText("");
                eName.setText("");
                eAddress.setText("");
                eMobile.setText("");
                ePosition.setText("");
                ePassword.setText("");
            }
        });

    }


    private void displaydata(String nic)
    {
        Cursor cursor = DB.getTechOfficer_details_usingNIC(nic);
        if(cursor.getCount()==0)
        {
            Toast.makeText(EditTechOffice.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                employeeId.setText(cursor.getString(1));
                NIC.setText(cursor.getString(2));
                eName.setText(cursor.getString(3));
                eAddress.setText(cursor.getString(4));
                eMobile.setText(cursor.getString(5));
                ePosition.setText(cursor.getString(6));
                ePassword.setText(cursor.getString(7));
            }
        }
    }
}