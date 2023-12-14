package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HistoryActivity extends AppCompatActivity {
Integer id;
String category, name, amount, duedate;
Button search;
EditText tid, txt1, txt2, txt3, txt4, txt5;
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tid = (EditText)findViewById(R.id.txtsearch);
        txt1 = (EditText)findViewById(R.id.edittext1);
        txt1.setBackgroundColor(Color.TRANSPARENT);
        txt2 = (EditText)findViewById(R.id.edittext2);
        txt2.setBackgroundColor(Color.TRANSPARENT);
        txt3 = (EditText)findViewById(R.id.edittext3);
        txt3.setBackgroundColor(Color.TRANSPARENT);
        txt4 = (EditText)findViewById(R.id.edittext4);
        txt4.setBackgroundColor(Color.TRANSPARENT);
        txt5 = (EditText)findViewById(R.id.edittext5);
        txt5.setBackgroundColor(Color.TRANSPARENT);

        search = (Button)findViewById(R.id.btnsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Integer.parseInt(tid.getText().toString());
                db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);

                // Use parameterized query to avoid SQL injection
                Cursor cr = db.rawQuery("SELECT * FROM data WHERE tid=?", new String[]{String.valueOf(id)});

                if (cr.moveToFirst()) {
                    // Correctly handle column indices
                    id = cr.getInt(cr.getColumnIndex("tid"));
                    category = cr.getString(cr.getColumnIndex("category"));
                    name = cr.getString(cr.getColumnIndex("nameofcompany"));
                    amount = cr.getString(cr.getColumnIndex("amount"));
                    duedate = cr.getString(cr.getColumnIndex("duedate"));

                    // Convert id to String before setting it to the EditText
                    txt1.setText(String.valueOf(id));
                    txt2.setText(category);
                    txt3.setText(name);
                    txt4.setText(amount);
                    txt5.setText(duedate);
                } else {
                    // Handle the case where no data is found
                    // You may want to show a message or handle it as per your app logic
                    Toast.makeText(getApplicationContext(), "No data found for ID: " + id, Toast.LENGTH_SHORT).show();
                }

                cr.close();  // Close the cursor to free up resources
                db.close();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}