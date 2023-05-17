package com.example.landmoservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {

        super(context, "Landmo.db", null, 1);
    }

    @Override
    //Delete the old database file so that onCreate() is run again
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(name TEXT primary key, contact TEXT, dob TEXT)");
        DB.execSQL("create Table customer(CID TEXT primary key,name TEXT, dob TEXT,  city TEXT, state TEXT,password TEXT, phone_number TEXT)");
        DB.execSQL("create Table CRrequest(num INTEGER primary key autoincrement,RepairID TEXT ,CID TEXT ,vnumber TEXT, vtype TEXT,  vdisc TEXT,mobile TEXT, vaddress TEXT,vlocation TEXT,date TEXT, status TEXT)");
        DB.execSQL("create Table CSrequest(num INTEGER primary key autoincrement,ServiceID TEXT ,CID TEXT ,vnumber TEXT, vtype TEXT,  vdisc TEXT,mobile TEXT, Fday1 TEXT,Fday2 TEXT,date TEXT, status TEXT)");
        DB.execSQL("create Table Employee(EID INTEGER primary key autoincrement,Employ_ID TEXT,NIC TEXT,Name TEXT,Address TEXT,Mobile TEXT,Position TEXT,Password TEXT)");
        DB.execSQL("create Table TOschedule(RID TEXT primary key,CID TEXT,Name TEXT,Mobile TEXT,Vnumber TEXT,TchID TEXT,Date TEXT,Time TEXT,Status TEXT)");
        DB.execSQL("create Table ServiceCenter(SCID INTEGER primary key autoincrement, City TEXT, Mobile TEXT,Location TEXT)");
        DB.execSQL("create Table SCschedule(SID TEXT primary key,CID TEXT,Vnumber TEXT,SCenter TEXT,Date TEXT,Time TEXT,Status TEXT)");
    }

    // implement data migration in onUpgrade() so your users don't lose their data.
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
        DB.execSQL("drop Table if exists customer");
        DB.execSQL("drop Table if exists CRrequest");
        DB.execSQL("drop Table if exists CSrequest");
        DB.execSQL("drop Table if exists Employee");
        DB.execSQL("drop Table if exists TOschedule");
        DB.execSQL("drop Table if exists ServiceCenter");
        DB.execSQL("drop Table if exists SCschedule");
        onCreate(DB);
    }

    public Boolean insertuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        long result = DB.insert("Userdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CRrequest", null);
        return cursor;
    }

    //customer insert update login

    public boolean checkUserLogin(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select CID, password from customer where CID='"+ username +"' and password='"+ password+"'";
        Cursor cursor = null;

        if(username.equals("admin") && password.equals("admin")){
            return true;
        }

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }

    }

    public Cursor Customer_getdata(String CID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from customer where CID='"+ CID +"'", null);
        return cursor;
    }

    public Boolean insert_customer(String CID,String name,String dob,String city,String state,String password,String phone_number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("CID", CID);
        contentValues.put("name", name);
        contentValues.put("dob", dob);
        contentValues.put("city", city);
        contentValues.put("state", state);
        contentValues.put("password", password);
        contentValues.put("phone_number", phone_number);

        long result = DB.insert("customer", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean updatecustomer(String CID,String name,String dob,String city,String state,String password,String phone_number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CID", CID);
        contentValues.put("name", name);
        contentValues.put("dob", dob);
        contentValues.put("city", city);
        contentValues.put("state", state);
        contentValues.put("password", password);
        contentValues.put("phone_number", phone_number);
        Cursor cursor = DB.rawQuery("Select * from customer where CID = ?", new String[]{CID});
        if (cursor.getCount() > 0) {
            long result = DB.update("customer", contentValues, "CID=?", new String[]{CID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }



    // customer repair request isert update adn delete

    public Boolean insert_customer_repair_request(String RepairID,String CID ,String vnumber ,String vtype ,String vdisc ,String mobile,String vaddress ,String vlocation ,String date,String status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RepairID",RepairID );
        contentValues.put("CID", CID);
        contentValues.put("vnumber", vnumber);
        contentValues.put("vtype", vtype);
        contentValues.put("vdisc", vdisc);
        contentValues.put("mobile", mobile);
        contentValues.put("vaddress", vaddress);
        contentValues.put("vlocation", vlocation);
        contentValues.put("date", date);
        contentValues.put("status", status);
        long result = DB.insert("CRrequest", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor repair_Id() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM CRrequest ORDER BY num  DESC LIMIT 1", null);
        return cursor;
    }

    public Cursor getdata_repair(String CID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CRrequest where CID='"+CID+"'", null);
        return cursor;
    }

    public Cursor getdata_repair_for_office() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CRrequest where status='Processing...'", null);
        return cursor;
    }

    public Cursor getdata_repair_for_tech(String NIC) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT TOschedule.RID,TOschedule.Vnumber,TOschedule.Date,TOschedule.Status FROM TOschedule JOIN Employee ON TOschedule.TchID=Employee.Employ_ID WHERE TOschedule.Status="+"'Accepted'"+" AND Employee.NIC='"+NIC+"'", null);
        return cursor;
    }

    public Cursor getdata_repair_for() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CRrequest where status!='Processing...'", null);
        return cursor;
    }

    public Cursor getdata_servic_for() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CSrequest where status!='Processing...'", null);
        return cursor;
    }

    public Cursor Accept_rpeair(String RID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT Employee.Name,Employee.Mobile,TOschedule.Vnumber,TOschedule.Date,TOschedule.Time FROM TOschedule JOIN Employee ON TOschedule.TchID=Employee.Employ_ID WHERE TOschedule.RID='"+RID+"'", null);
        return cursor;
    }

    public Cursor Accept_service(String SID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT SCschedule.Vnumber,ServiceCenter.City,ServiceCenter.Mobile,SCschedule.Date,SCschedule.Time,ServiceCenter.Location FROM SCschedule JOIN ServiceCenter ON SCschedule.SCenter=ServiceCenter.City WHERE SCschedule.SID='"+SID+"'", null);
        return cursor;
    }

    public Cursor Accept_Repair_dtails(String SID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT TOschedule.Vnumber,TOschedule.Name,TOschedule.Mobile,TOschedule.Date,TOschedule.Time,CRrequest.vdisc,CRrequest.vaddress,CRrequest.vlocation FROM TOschedule JOIN CRrequest ON TOschedule.RID= CRrequest.RepairID WHERE CRrequest.RepairID='"+SID+"'", null);
        return cursor;
    }

    public Cursor getdata_servic_for_office() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CSrequest where status='Processing...'", null);
        return cursor;
    }


    // Customer Service request Insert Update And Delete

    public Boolean insert_customer_service_request(String ServiceID,String CID ,String vnumber ,String vtype ,String vdisc ,String mobile,String Fday1 ,String Fday2 ,String date,String status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ServiceID",ServiceID );
        contentValues.put("CID", CID);
        contentValues.put("vnumber", vnumber);
        contentValues.put("vtype", vtype);
        contentValues.put("vdisc", vdisc);
        contentValues.put("mobile", mobile);
        contentValues.put("Fday1", Fday1);
        contentValues.put("Fday2", Fday2);
        contentValues.put("date", date);
        contentValues.put("status", status);
        long result = DB.insert("CSrequest", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor service_Id() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM CSrequest ORDER BY num  DESC LIMIT 1", null);
        return cursor;
    }

    public Cursor getdata_service(String CID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from CSrequest where CID='"+CID+"'", null);
        return cursor;
    }



    // Employee login

    public boolean checkEmployeLogin(String username,String password,String type){
        SQLiteDatabase db=this.getWritableDatabase();
        String Query = "select NIC, Password from Employee where NIC='"+ username +"' and Password='"+ password+"' and Position='"+type+"'";
        Cursor cursor = null;

        if(username.equals("admin") && password.equals("admin")){
            return true;
        }

        try {
            cursor = db.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }

    }

    // Employee adding

    public Cursor getdata_OfficeEmploy() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select Employ_ID from Employee where Position ='Technical Officer'", null);
        return cursor;
    }

    public Cursor getdata_ServiceCenter() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select City from ServiceCenter", null);
        return cursor;
    }

    public Cursor getdata_customer_ID_using_Rid(String RID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT customer.CID,name,mobile,vnumber FROM customer JOIN CRrequest ON customer.CID=CRrequest.CID WHERE CRrequest.RepairID='"+RID+"'", null);
        return cursor;
    }

    public Cursor getdata_customer_ID_using_Sid(String RID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT customer.CID,name,mobile,vnumber,Fday1,Fday2,customer.city FROM customer JOIN CSrequest ON customer.CID=CSrequest.CID WHERE CSrequest.ServiceID='"+RID+"'", null);
        return cursor;
    }

    public Boolean insert_toshedule(String RID,String CID,String Name,String Mobile,String Vnumber,String TchID,String Date,String Time,String Status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("RID", RID);
        contentValues.put("CID", CID);
        contentValues.put("Name", Name);
        contentValues.put("Mobile", Mobile);
        contentValues.put("Vnumber", Vnumber);
        contentValues.put("TchID", TchID);
        contentValues.put("Date", Date);
        contentValues.put("Time", Time);
        contentValues.put("Status", Status);

        long result = DB.insert("TOschedule", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insert_scshedule(String SID,String CID,String Vnumber,String SCenter,String Date,String Time,String status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("SID", SID);
        contentValues.put("CID", CID);
        contentValues.put("Vnumber", Vnumber);
        contentValues.put("SCenter", SCenter);
        contentValues.put("Date", Date);
        contentValues.put("Time", Time);
        contentValues.put("status", status);

        long result = DB.insert("SCschedule", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateRstatus(String RID, String status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);

        Cursor cursor = DB.rawQuery("Select * from CRrequest where RepairID = ?", new String[]{RID});
        if (cursor.getCount() > 0) {
            long result = DB.update("CRrequest", contentValues, "RepairID=?", new String[]{RID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean updateSstatus(String SID, String status) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);

        Cursor cursor = DB.rawQuery("Select * from CSrequest where ServiceID = ?", new String[]{SID});
        if (cursor.getCount() > 0) {
            long result = DB.update("CSrequest", contentValues, "ServiceID=?", new String[]{SID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //******************************************* Start Employee ***********************************
    //Update Employees
    public Cursor employee_getdata(String Employee_ID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Employee where Employ_ID='"+ Employee_ID +"'", null);
        return cursor;
    }

    //Insert Employees to the Database
    public Boolean insert_employee(String employeeID, String nic,String name, String address,String mobile,String position,String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Employ_ID", employeeID);
        contentValues.put("NIC", nic);
        contentValues.put("Name", name);
        contentValues.put("Address", address);
        contentValues.put("Mobile", mobile);
        contentValues.put("Position", position);
        contentValues.put("Password", password);

        long result = DB.insert("Employee", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Update Employees to the Database
    public Boolean update_employee(String Employ_ID, String nic,String name, String address,String mobile,String position,String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        /*contentValues.put("CID", CID);
        contentValues.put("name", name);
        contentValues.put("dob", dob);
        contentValues.put("city", city);
        contentValues.put("state", state);
        contentValues.put("password", password);
        contentValues.put("phone_number", phone_number);*/

        contentValues.put("Employ_ID", Employ_ID);
        contentValues.put("NIC", nic);
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("mobile", mobile);
        contentValues.put("position", position);
        contentValues.put("password", password);

        Cursor cursor = DB.rawQuery("Select * from Employee where Employ_ID = ?", new String[]{Employ_ID});
        if (cursor.getCount() > 0) {
            long result = DB.update("Employee", contentValues, "Employ_ID=?", new String[]{Employ_ID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor employee_Id() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Employee ORDER BY EID  DESC LIMIT 1", null);
        return cursor;
    }
    //******************************************* End Employee ***********************************

    //******************************************* Edit Customer by himself ***********************************

    public Boolean updatecustomer_byCustomer(String CID, String city,String state,String password,String phone_number) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CID", CID);
        contentValues.put("city", city);
        contentValues.put("state", state);
        contentValues.put("password", password);
        contentValues.put("phone_number", phone_number);
        Cursor cursor = DB.rawQuery("Select * from customer where CID = ?", new String[]{CID});
        if (cursor.getCount() > 0) {
            long result = DB.update("customer", contentValues, "CID=?", new String[]{CID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata_customer_details_usingNIC(String nic) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from customer WHERE CID='"+nic+"'", null);
        return cursor;
    }

    // ******************************************* End of edit Customer by himself ***********************************



    //******************************************* Edit Technical Officer by himself ***********************************

    public Boolean updateTechOfficer_byHimself(String NIC, String address,String mobile,String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NIC", NIC);
        contentValues.put("address", address);
        contentValues.put("mobile", mobile);
        contentValues.put("password", password);

        Cursor cursor = DB.rawQuery("Select * from Employee where NIC = ?", new String[]{NIC});
        if (cursor.getCount() > 0) {
            long result = DB.update("Employee", contentValues, "NIC=?", new String[]{NIC});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getTechOfficer_details_usingNIC(String nic) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from Employee WHERE NIC='"+nic+"'", null);
        return cursor;
    }

    // ******************************************* End of edit Tech Officer by himself ***********************************


    public Boolean update_confirm(String status,String RID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("status", status);

        Cursor cursor = DB.rawQuery("Select status from CRrequest where RepairID = ?", new String[]{RID});
        if (cursor.getCount() > 0) {
            long result = DB.update("CRrequest", contentValues, "RepairID= ?", new String[]{RID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean update_confirmTO(String status,String RID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("Status", status);

        Cursor cursor = DB.rawQuery("Select Status from TOschedule where RID = ?", new String[]{RID});
        if (cursor.getCount() > 0) {
            long result = DB.update("TOschedule", contentValues, "RID= ?", new String[]{RID});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}