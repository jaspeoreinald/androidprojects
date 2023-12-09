package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class SplashscreenActivity extends AppCompatActivity {
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //CREATE DATABASE
        db = openOrCreateDatabase ("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.execSQL ("CREATE TABLE IF NOT EXISTS info(id INTEGER, name VARCHAR, bal VARCHAR);");
        db.close();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent menu = new Intent(getBaseContext(), MainActivity.class);
                startActivity(menu);
            }
        }, 3000);
    }
}