package com.example.landmoservice.admin_login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landmoservice.CustomerHomeActivity;
import com.example.landmoservice.DBHelper;

import com.example.landmoservice.LoginActivity;
import com.example.landmoservice.Office_Member.AddCustomerActivity;
import com.example.landmoservice.Office_Member.OfficeHomeActivity;
import com.example.landmoservice.R;
import com.example.landmoservice.Tech_Officer.TechHomeActivity;

public class AdminLoginActivity extends AppCompatActivity {

    public  String id,ps,type;
    Button loginbtn;
    TextView userid,password,admin;
    DBHelper DB;


   // Select login type
    String[] items =  {
            "Office Member",
            "Technical Officer",
    };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().hide();

        DB = new DBHelper(this);

        loginbtn = findViewById(R.id.loginbtn);
        userid = findViewById(R.id.vname);
        password = findViewById(R.id.password);
        admin=findViewById(R.id.textView3);

//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//        userid.setText(pref.getString("id",id));
//        password.setText(pref.getString("ps",ps));

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



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ps=password.getText().toString();
                id=userid.getText().toString();
                type=autoCompleteTxt.getText().toString();


//                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(AdminLoginActivity.this);
//                SharedPreferences.Editor editor=pref.edit();
//                editor.putString("id", id);
//                editor.putString("ps", ps );
//                editor.apply();

                if (DB.checkEmployeLogin(id, ps ,type)) {

                    if(type.equals("Office Member")) {
                        Intent intent = new Intent(AdminLoginActivity.this,OfficeHomeActivity.class);
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                        startActivity(intent);


                    }
                    else if(type.equals("Technical Officer")){

                        Intent intent = new Intent(AdminLoginActivity.this, TechHomeActivity.class);
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                        intent.putExtra("CID",id);
                        startActivity(intent);


                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Login Not Successfull", Toast.LENGTH_LONG).show();
                    }




                } else {
                    Toast.makeText(getApplicationContext(), "Wrong UserID or Password", Toast.LENGTH_LONG).show();
                }



            }
        });
    }
}