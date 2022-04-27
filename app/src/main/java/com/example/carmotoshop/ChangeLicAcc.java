package com.example.carmotoshop;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class ChangeLicAcc extends AppCompatActivity implements View.OnClickListener{

    Button button_back, button_save, button_delete;
    EditText username_field, password_field;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_lic_acc);
        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(this);

        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(this);

        button_delete = findViewById(R.id.button_delete);
        button_delete.setOnClickListener(this);

        username_field = findViewById(R.id.username_field);
        password_field = findViewById(R.id.password_field);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        Update();
        username_field.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                username_field.setHint("");
            else
                username_field.setHint("Логин");
        });
        password_field.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                password_field.setHint("");
            else
                password_field.setHint("Пароль");
        });
    }
    public void Update() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.KEY_NAME + " = '" + MainActivity.user + "'", null);

        int loginIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        int passwordIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);

        if (cursor.moveToFirst()) {
            username_field.setText(cursor.getString(loginIndex));
            password_field.setText(cursor.getString(passwordIndex));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                startActivity(new Intent(this, Menu2.class));
                finish();
                break;
            case R.id.button_save:
                String login = username_field.getText().toString();
                String password = password_field.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, login);
                contentValues.put(DBHelper.KEY_PASSWORD, password);
                database.update(DBHelper.TABLE_USERS, contentValues,DBHelper.KEY_NAME + " = '" + MainActivity.user + "'", null);
                Update();
                break;
            case R.id.button_delete:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                int indexStr = outputDB.indexOfChild(outputDBRow);
                int index = 0;
                Cursor cursorUpdater = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.KEY_NAME + " = '" + MainActivity.user + "'", null);
                if (cursorUpdater != null) {
                    cursorUpdater.moveToPosition(indexStr);
                    index =  cursorUpdater.getInt(0);
                }
                database.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + " = " + index, null);
                startActivity(new Intent(this, Menu2.class));
                finish();
                break;
        }
    }




}
