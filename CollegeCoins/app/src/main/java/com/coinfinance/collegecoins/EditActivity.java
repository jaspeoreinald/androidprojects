package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    EditText input;
Button save;
Integer userid=1;
String inputforuser;
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        input = (EditText)findViewById(R.id.etxtnick);
        save = (Button)findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputforuser = input.getText().toString();
                db = openOrCreateDatabase("DBASE",SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cn = new ContentValues();
                cn.put("id", userid);
                cn.put("name", inputforuser);
                db.update("info", cn, "id=" + userid, null);
                Toast.makeText(getApplicationContext(),"You are now " + inputforuser,Toast.LENGTH_LONG).show();
                input.setText("");
                db.close();
            }
        });
    }
}