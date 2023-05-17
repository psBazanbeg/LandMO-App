package com.example.landmoservice.Office_Member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.landmoservice.DBHelper;
import com.example.landmoservice.DatePickerFragment;
import com.example.landmoservice.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Accept_Repair extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    String RSID;
    TextView textView9,txtname,txtid,txtmobile,txtvid,day1,time1,vtype;
    Button btntime,schedule;
    int hour,minute;


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    DBHelper DB;
    // Select login type
    String[] items =  {
            " ",
            " ",
            " ",
            " ",
    };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_repair);

        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        RSID=bundle.getString("RSID");
        textView9=findViewById(R.id.textView14);
        textView9.setText(RSID);

        calendar = Calendar.getInstance();

        txtid=findViewById(R.id.textView18);
        txtmobile=findViewById(R.id.textView20);
        txtvid=findViewById(R.id.textView22);
        txtname=findViewById(R.id.textView16);
        btntime=findViewById(R.id.callbtn);

        schedule=findViewById(R.id.button7);


        day1= findViewById(R.id.day1);
        day1.setEnabled(false);

        time1= findViewById(R.id.time);
        time1.setEnabled(false);


        DB = new DBHelper(this);
         autoCompleteTxt = findViewById(R.id.v);

        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());

        setcustomerdata(RSID);
       setTechofficerlist();
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        vtype=findViewById(R.id.v);

       autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String item = parent.getItemAtPosition(position).toString();
               Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
       });




        Button button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });




        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              insetdata();
            }
        });




    }

    public void setTechofficerlist(){
        Cursor res = DB.getdata_OfficeEmploy();
        StringBuffer buffer = new StringBuffer();
        int i=res.getCount()-1;
        while(res.moveToNext()){
            System.out.println(res.getString(0).toString());
            items[i]=res.getString(0).toString();

            i--;
        }
    }

    public  void setcustomerdata(String RID){
        Cursor res = DB.getdata_customer_ID_using_Rid(RID);
        while(res.moveToNext()) {
            txtid.setText(res.getString(0).toString());
            txtname.setText(res.getString(1).toString());
            txtmobile.setText(res.getString(2).toString());
            txtvid.setText(res.getString(3).toString());
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        month++;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        // String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String currentDateString = (dayOfMonth+"/"+month+"/"+year);

            day1.setText(currentDateString);



    }

    public void popTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
             hour=selectedHour;
             minute=selectedMinute;
             time1.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
       timePickerDialog.setTitle("Select Time");
       timePickerDialog.show();
    }

    public void insetdata(){

        Boolean insertemp = DB.insert_toshedule(
                textView9.getText().toString()
                ,txtid.getText().toString()
                ,txtname.getText().toString()
                ,txtmobile.getText().toString()
                ,txtvid.getText().toString()
                ,vtype.getText().toString()
                ,day1.getText().toString()
                ,time1.getText().toString()
                ,"Accepted"
        );

        if(insertemp==true) {
            DB.updateRstatus(RSID,"Accepted");
            Toast.makeText(Accept_Repair.this, "Scheduled", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(Accept_Repair.this, OfficeHomeActivity.class);
            startActivity(intent);


        }else {
            Toast.makeText(Accept_Repair.this, "Not Scheduled", Toast.LENGTH_SHORT).show();
        }
    }
}