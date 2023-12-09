package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
String categories[] = {"Electric Utilities","Water Utilities","Internet","Telecoms","Credit Cards","Loans","Government","Insurance","Transportation",
"Real Estate","Healthcare","Schools","Others"};
Integer datatid;
String datacategory, datanoc, dataamount;
Button rtn, sbmt;
AutoCompleteTextView actv;
EditText tid, noc, amount;
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,categories);
        tid = (EditText)findViewById(R.id.txttid);
        actv =  (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1); //will start working from first character
        actv.setAdapter(adapter); //setting the adapter data into the AutoCompleteTextView
        noc = (EditText)findViewById(R.id.txtnoc);
        amount = (EditText)findViewById(R.id.txtamount);
        // OPEN DATABASE AND CREATE NEW TABLE
        db = openOrCreateDatabase ("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.execSQL ("CREATE TABLE IF NOT EXISTS data(tid INTEGER, category VARCHAR, nameofcompany VARCHAR, amount VARCHAR);");
        db.close();

        sbmt = (Button)findViewById(R.id.btnsubmit);
        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datatid = Integer.parseInt(tid.getText().toString());
                datacategory = actv.getText().toString();
                datanoc = noc.getText().toString();
                dataamount = amount.getText().toString();
                db = openOrCreateDatabase("DBASE",SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cn = new ContentValues();
                cn.put("tid", datatid);
                cn.put("category", datacategory);
                cn.put("nameofcompany", datanoc);
                cn.put("amount", dataamount);
                db.update("data", cn, "tid=" + datatid, null);
                Toast.makeText(getApplicationContext(),"Transaction ID: " + datatid,Toast.LENGTH_LONG).show();
                tid.setText("");
                actv.setText("");
                noc.setText("");
                amount.setText("");
                db.close();
            }
        });

        rtn = (Button)findViewById(R.id.btnreturn);
        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}