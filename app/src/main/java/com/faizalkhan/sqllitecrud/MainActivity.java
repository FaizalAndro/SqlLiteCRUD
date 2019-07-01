package com.faizalkhan.sqllitecrud;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText eid, ename, eemail, ecourse;
    Button addbtn, viewbtn, updatebtn, deletebtn, viewallbtn;
    DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DbHelper(this);

        eid =findViewById(R.id.editText_id);
        ename =findViewById(R.id.editText_name);
        eemail =findViewById(R.id.editText_email);
        ecourse =findViewById(R.id.editText_CC);

        addbtn = findViewById(R.id.button_add);
        viewbtn = findViewById(R.id.button_view);
        updatebtn = findViewById(R.id.button_update);
        deletebtn = findViewById(R.id.button_delete);
        viewallbtn = findViewById(R.id.button_viewAll);

        addData();
        getDBData();
        viewall();
        updateData();
        deleteData();


    }

    public void addData(){
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insert(ename.getText().toString(), eemail.getText().toString(), ecourse.getText().toString());


                if(isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Feed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateData(){
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = mydb.update(eid.getText().toString(),
                        ename.getText().toString(),
                        eemail.getText().toString(),
                        ecourse.getText().toString());

                if(isUpdate==true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }
                else    {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deleteData(){
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRow = mydb.dbDelete(eid.getText().toString());

                if(deleteRow>0){
                    Toast.makeText(MainActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getDBData(){
        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = eid.getText().toString().trim();

                if(id.equals("")){
                    eid.setError("Enter ID");
                    return;
                }
                Cursor cursor = mydb.getData(id);
                String data = null;
                if(cursor.moveToNext()){
                    data = "ID: "+cursor.getString(0)+"\n"+
                            "Name "+cursor.getString(1)+"\n"+
                            "Email "+cursor.getString(2)+"\n"+
                            "Courses "+cursor.getString(3)+"\n";
                }
                showmsz("Info",data);
            }
        });
    }

    public void viewall(){
        viewallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor =mydb.getAlldata();
                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()) {
                    buffer.append("ID: " + cursor.getString(0) + "\n");
                    buffer.append("Name: " + cursor.getString(1) + "\n");
                    buffer.append("Email: " + cursor.getString(2) + "\n");
                    buffer.append("Courses: " + cursor.getString(3) + "\n");
                }
                showmsz("All Data", buffer.toString());
            }
        });
    }

    private void showmsz(String title, String msz){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msz);
        builder.show();
    }
}
