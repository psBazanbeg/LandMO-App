package com.example.landmoservice.Office_Member;

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

public class AddEmployee extends AppCompatActivity {

    Button Register,update,searchIcon,cancel;
    EditText employeeId,NIC,eName, eAddress, eMobile, ePosition, ePassword, searchTxt;

    public String emp_id;

    String[] items =  {
            "Office Member",
            "Technical Officer",
    };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        getSupportActionBar().hide();


        Register=(Button) findViewById(R.id.Registerbtn);
        update=(Button) findViewById(R.id.Updatebtn);
        searchIcon=(Button) findViewById(R.id.searchIcon);
        cancel=(Button) findViewById(R.id.cancelbtn);

        update.setEnabled(false);

        DB = new DBHelper(this);

        employeeId=findViewById(R.id.Eid);
        NIC=findViewById(R.id.Enic);
        eName=findViewById(R.id.Ename);
        eAddress=findViewById(R.id.Eaddress);
        eMobile=findViewById(R.id.Emobile);
        ePassword=findViewById(R.id.Epw);
        searchTxt=findViewById(R.id.searchTxt);

        employeeId.setEnabled(false);

        autoCompleteTxt = findViewById(R.id.Vtype);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        /*****************/
        Cursor res = DB.employee_Id();

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){

            buffer.append(res.getString(1));
        }
        emp_id=buffer.toString();
        System.out.println(emp_id);

        int lenth = emp_id.length();
        System.out.println(lenth);
        String txt = emp_id.substring(0, 2);
        String num = emp_id.substring(2, lenth);
        int d = Integer.parseInt(num);
        d++;
        String snum = Integer.toString(d);
        emp_id = txt + snum;

        employeeId.setText(emp_id);
        /*****************/

        ePosition=findViewById(R.id.Vtype);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setEnabled(true);
                NIC.setEnabled(false);
                Cursor res = DB.employee_getdata(searchTxt.getText().toString());
                if(res.getCount()==0){

                    NIC.setText("");
                    eName.setText("");
                    eAddress.setText("");
                    eMobile.setText("");
                    ePosition.setText("");
                    ePassword.setText("");

                    Toast.makeText(AddEmployee.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;

                    /*Cmobile.setText("");
                    Cpassword.setText("");
                    Cstate.setText("");
                    NIC.setText("");
                    Cname.setText("");
                    Cbd.setText("");
                    Ccity.setText("");
                    Toast.makeText(AddEmployee.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;*/
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){

                    employeeId.setText(res.getString(1).toString());
                    NIC.setText(res.getString(2).toString());
                    eName.setText(res.getString(3).toString());
                    eAddress.setText(res.getString(4).toString());
                    eMobile.setText(res.getString(5).toString());
                    ePosition.setText(res.getString(6).toString());
                    ePassword.setText(res.getString(7).toString());

                    Register.setEnabled(false);
                }
                Toast.makeText(AddEmployee.this, "Entry Exists", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddEmployee.this, "Cleared", Toast.LENGTH_SHORT).show();

                NIC.setText("");
                eName.setText("");
                eAddress.setText("");
                eMobile.setText("");
                ePosition.setText("");
                ePassword.setText("");
                searchTxt.setText("");

                /*Cmobile.setText("");
                Cpassword.setText("");
                Cstate.setText("");
                NIC.setText("");
                Cname.setText("");
                Cbd.setText("");
                Ccity.setText("");
                searchnic.setText("");*/

                NIC.setEnabled(true);
                Register.setEnabled(true);
                update.setEnabled(false);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean insertrepair = DB.insert_employee(
                        emp_id
                        ,NIC.getText().toString()
                        ,eName.getText().toString()
                        ,eAddress.getText().toString()
                        ,eMobile.getText().toString()
                        ,ePosition.getText().toString()
                        ,ePassword.getText().toString());
                if(insertrepair==true) {
                    employeeId.setText("");
                    NIC.setText("");
                    eName.setText("");
                    eAddress.setText("");
                    eMobile.setText("");
                    ePosition.setText("");
                    ePassword.setText("");
                    searchTxt.setText("");

                    Toast.makeText(AddEmployee.this, "New Employee Inserted", Toast.LENGTH_SHORT).show();

                    /*****************/
                    Cursor res = DB.employee_Id();

                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()){

                        buffer.append(res.getString(1));
                    }
                    emp_id=buffer.toString();
                    System.out.println(emp_id);

                    int lenth = emp_id.length();
                    System.out.println(lenth);
                    String txt = emp_id.substring(0, 2);
                    String num = emp_id.substring(2, lenth);
                    int d = Integer.parseInt(num);
                    d++;
                    String snum = Integer.toString(d);
                    emp_id = txt + snum;

                    employeeId.setText(emp_id);
                    /*****************/
                }else
                    Toast.makeText(AddEmployee.this, "New Employee Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkupdatedata = DB.update_employee(
                        employeeId.getText().toString()
                        ,NIC.getText().toString()
                        ,eName.getText().toString()
                        ,eAddress.getText().toString()
                        ,eMobile.getText().toString()
                        ,ePosition.getText().toString()
                        ,ePassword.getText().toString());
                if(checkupdatedata==true) {
                    NIC.setText("");
                    eName.setText("");
                    eAddress.setText("");
                    eMobile.setText("");
                    ePosition.setText("");
                    ePassword.setText("");
                    searchTxt.setText("");
                    Toast.makeText(AddEmployee.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AddEmployee.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

            }
        });


    }
}