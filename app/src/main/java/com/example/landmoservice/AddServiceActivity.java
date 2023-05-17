package com.example.landmoservice;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddServiceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView day1;
    TextView day2;

    Button submit;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    public String SID;

    TextView A,B,C,D,E,F;
    String CID;


    String status="Processing...";
    DBHelper DB;

    String[] items =  {"Excavators",
            "Backhoe",
            "Excavator",
            "Bulldozers",
            "Graders",
            "Wheel Tractor",
            "Trenchers",
            "Loaders",
            "Tower Cranes",
    };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        Bundle bundle = getIntent().getExtras();
        calendar = Calendar.getInstance();
        getSupportActionBar().hide();

        autoCompleteTxt = findViewById(R.id.Vtype);
        submit=findViewById(R.id.Sendbtn);

        day1= findViewById(R.id.day1);
        day1.setEnabled(false);
        day2= findViewById(R.id.day2);
        day2.setEnabled(false);

        DB = new DBHelper(this);

        CID=bundle.getString("CID");
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());

        A=findViewById(R.id.Cnic);
        B=findViewById(R.id.Vtype);
        C=findViewById(R.id.vdisc);
        D=findViewById(R.id.Cmobile);

        E=findViewById(R.id.day1);
        F=findViewById(R.id.day2);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        Button button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cursor res = DB.service_Id();

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){

                    buffer.append(res.getString(1));
                }

                SID=buffer.toString();
                System.out.println(SID);

                int lenth = SID.length();
                System.out.println(lenth);
                String txt = SID.substring(0, 2);
                String num = SID.substring(2, lenth);
                int d = Integer.parseInt(num);
                d++;
                String snum = Integer.toString(d);
                SID = txt + snum;

                System.out.println(SID);

                System.out.println(CID+" "
                        +A.getText().toString()+" "
                        +B.getText().toString()+" "
                        +C.getText().toString()+" "
                        +D.getText().toString()+" "
                        +E.getText().toString()+" "
                        +F.getText().toString()+" "
                        +date+" "
                        +status);


                Boolean insertrepair = DB.insert_customer_service_request( SID
                        ,CID
                        ,A.getText().toString()
                        ,B.getText().toString()
                        ,C.getText().toString()
                        ,D.getText().toString()
                        ,E.getText().toString()
                        ,F.getText().toString()
                        ,date
                        ,status);
                if(insertrepair==true)
                    Toast.makeText(AddServiceActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddServiceActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(AddServiceActivity.this,CustomerHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        month++;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        // String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String currentDateString = (dayOfMonth+"/"+month+"/"+year);



       // TextView textView = (TextView) findViewById(R.id.textView);

        if(day1.getText().toString().equals("Select 1 Date")){
            day1.setText(currentDateString);
        }
        else if(day2.getText().toString().equals("Select 2 Date")){
            day2.setText(currentDateString);
        }
        else{
            day2.setText(currentDateString);
        }

    }
}