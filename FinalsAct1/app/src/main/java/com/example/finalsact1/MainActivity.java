package com.example.finalsact1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText sid, sname, scourse;
Button search, save, update, delete, close;
String id="", name="", course="";
SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sid = (EditText)findViewById(R.id.txtsid);
        sname = (EditText)findViewById(R.id.txtname);
        scourse = (EditText)findViewById(R.id.txtcourse);
        db = openOrCreateDatabase ("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.execSQL ("CREATE TABLE IF NOT EXISTS Sinfo(ID VARCHAR, NAME VARCHAR, COURSE VARCHAR);");
        db.close();

        search = (Button)findViewById(R.id.btnsearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = sid.getText().toString();
                db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);

                Cursor  cr = db.rawQuery   ("SELECT * FROM  Sinfo  where ID=?" + id,null);

                cr.moveToFirst() ;

                while (cr.isAfterLast() == false) {
                    id = cr.getString(0);
                    name = cr.getString(1);
                    course = cr.getString(2);
                    cr.moveToNext();
                }

                sid.setText(id);
                sname.setText(name);
                scourse.setText(course);
                cr.close();
                db.close();
            }
        });

        save = (Button)findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data
                id = sid.getText().toString();
                name = sname.getText().toString();
                course = scourse.getText().toString();
                // to save in the table
                db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);

                ContentValues   cn = new ContentValues();
                cn.put("ID", id);
                cn.put("NAME", name);
                cn.put("COURSE", course);
                db.insert("Sinfo", " ", cn);

                // db.execSQL("INSERT INTO info VALUES('"+id+"','"+name+"','"+course+"');");

                Toast.makeText(getApplicationContext(),"DATA SUCCESSFULLY SAVED!", Toast.LENGTH_LONG).show();

                db.close();			// close database
                sid.setText("");			// clear
                sname.setText("");
                scourse.setText("");
            }
        });

        update = (Button)findViewById(R.id.btnupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = sid.getText().toString();
                name = sname.getText().toString();
                course = scourse.getText().toString();
                db = openOrCreateDatabase("DBASE",SQLiteDatabase.CREATE_IF_NECESSARY, null);
                ContentValues cn= new ContentValues();
                cn.put("ID",id);
                cn.put("NAME",name);
                cn.put("COURSE",course);
                db.update("Sinfo", cn, "ID=?"+id, null );
                Toast.makeText(getApplicationContext(),"DATA SUCCESSFULLY UPDATED!",Toast.LENGTH_LONG).show();
                sid.setText("");
                sname.setText("");
                scourse.setText("");
                db.close();
            }
        });

        delete = (Button)findViewById(R.id.btndelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = sid.getText().toString();
                db = openOrCreateDatabase("DBASE", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                db.delete("Sinfo", "ID=?" + id, null);
                Toast.makeText(getApplicationContext(), "DATA DELETED!", Toast.LENGTH_LONG).show();
                sid.setText("");
                sname.setText("");
                scourse.setText("");
                db.close();
            }
        });

        close = (Button)findViewById(R.id.btnclose);
        close.setOnClickListener(new View.OnClickListener() {
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
}