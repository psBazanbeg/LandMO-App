package com.example.landmoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landmoservice.admin_login.AdminLoginActivity;

public class LoginActivity extends AppCompatActivity {

    public  String id,ps;


    Button loginbtn;
    TextView userid,password,admin;
    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();


        DB = new DBHelper(this);

        loginbtn = findViewById(R.id.loginbtn);
        userid = findViewById(R.id.vname);
        password = findViewById(R.id.password);
        admin=findViewById(R.id.textView3);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        userid.setText(pref.getString("id",id));
        password.setText(pref.getString("ps",ps));


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 ps=password.getText().toString();
                 id=userid.getText().toString();



                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("id", id);
                editor.putString("ps", ps );
                editor.apply();

                if (DB.checkUserLogin(id, ps)) {
                    Intent intent =new Intent(LoginActivity.this,CustomerHomeActivity.class);
                    intent.putExtra("CID",id);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Wrong UserID or Password", Toast.LENGTH_LONG).show();
                }



            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, AdminLoginActivity.class);
                intent.putExtra("CID",id);
                startActivity(intent);
            }
        });


    }
}