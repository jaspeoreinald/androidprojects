package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button start, update, exit;
TextView user, bal;
String inputforuser, inputforbal;
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onResume();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        user = (TextView)findViewById(R.id.tvuser);
        bal = (TextView)findViewById(R.id.tvbal);

        //CREATE DATABASE
        db = openOrCreateDatabase ("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.execSQL ("CREATE TABLE IF NOT EXISTS info(id INTEGER, name VARCHAR, bal VARCHAR);");
        db.close();

        //CHECK ONCE PER LAUNCH
        continuous();

        update = (Button)findViewById(R.id.btnupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //JUST IN CASE
                continuous();
            }
        });

        exit = (Button)findViewById(R.id.btnexit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to exit?").setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Auto-generated method stub
                        MainActivity.this.finish();
                    }
                 });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void openEdit(View view) {
        startActivity(new Intent(this, EditActivity.class));
    }

    public void continuous(){
        db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        //CHECK IF TEXT HAS BEEN CHANGED

        Cursor cr = db.rawQuery("SELECT * FROM info WHERE id=1", null);
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            inputforuser = cr.getString(1);
            inputforbal = cr.getString(2);
            cr.moveToNext();
        }
        user.setText("Welcome, " + inputforuser + "!");
        bal.setText("Current Balance: P" + inputforbal);
        cr.close();
        db.close();
    }
}