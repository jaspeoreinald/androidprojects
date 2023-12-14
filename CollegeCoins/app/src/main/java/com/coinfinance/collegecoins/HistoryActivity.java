package com.coinfinance.collegecoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
Integer id, userid=1;
String category, name, amount, duedate;
Button search, delete;
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
        delete = (Button)findViewById(R.id.btnremove);
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

                    Toast.makeText(getApplicationContext(), "ID has been found", Toast.LENGTH_SHORT).show();

                    // Convert id to String before setting it to the EditText
                    txt1.setText(String.valueOf(id));
                    txt2.setText(category);
                    txt3.setText(name);
                    txt4.setText(amount);
                    txt5.setText(duedate);
                } else {
                    Toast.makeText(getApplicationContext(), "No data found for ID: " + id, Toast.LENGTH_SHORT).show();
                    txt1.setText("");
                    txt2.setText("");
                    txt3.setText("");
                    txt4.setText("");
                    txt5.setText("");
                }

                cr.close();  // Close the cursor to free up resources
                db.close();
                delete.setEnabled(true);
                delete.setVisibility(View.VISIBLE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                db.delete("data", "tid=" + id, null);
                updateInfoTable();
                Toast.makeText(getApplicationContext(), "Transaction ID deleted", Toast.LENGTH_LONG).show();
                txt1.setText("");
                txt2.setText("");
                txt3.setText("");
                txt4.setText("");
                txt5.setText("");
                delete.setEnabled(false);
                delete.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void updateInfoTable() {
        // Execute SQL query to calculate the sum of amount from the data table
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM data;", null);

        if (cursor.moveToFirst()) {
            // Get the sum value from the query result
            String sumAmount = cursor.getString(0);

            // Update the bal column in the info table with the calculated sum
            ContentValues updateInfo = new ContentValues();
            updateInfo.put("bal", sumAmount);

            // Update the info table
            db.update("info", updateInfo, "id=" + userid, null);
        }

        // Close the cursor after use
        cursor.close();
    }
}