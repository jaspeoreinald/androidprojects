package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button update, exit;
TextView user, bal;
String inputforuser, inputforbal;
SQLiteDatabase db;
Handler handler;
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

        //FIRST TIME INSTALLATION ONLY
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createdb();
        }

        //RUNS BACKGROUND CHECK
        continuous();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void createdb(){
        Integer uid = 1;
        String uname = "user", ubal = "0.00";

        db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        ContentValues cn = new ContentValues();
        cn.put("id", uid);
        cn.put("name", uname);
        cn.put("bal", ubal);
        db.insert("info", " ", cn);
        db.close();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public void openAbout(View view){

        startActivity(new Intent(this, AboutusActivity.class));
    }
    public void openStart(View view) {

        startActivity(new Intent(this, StartActivity.class));
    }

    public void openEdit(View view) {

        startActivity(new Intent(this, EditActivity.class));
    }
    public void openHistory(View view) {

        startActivity(new Intent(this, HistoryActivity.class));
    }
    public void continuous() {
        handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
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
                bal.setText("Amount to pay: P" + inputforbal);
                cr.close();
                db.close();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
    }


}
