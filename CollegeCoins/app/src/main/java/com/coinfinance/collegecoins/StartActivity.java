package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
Button go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        go_back = (Button)findViewById(R.id.btngohome);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void add(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }

    public void remove(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }
}