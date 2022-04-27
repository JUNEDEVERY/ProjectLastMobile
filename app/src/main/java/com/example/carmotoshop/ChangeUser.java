package com.example.carmotoshop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ChangeUser extends AppCompatActivity implements View.OnClickListener{

    Button button_back, button_save, button_delete;
    EditText username_field, password_field;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
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

        Bundle arguments = getIntent().getExtras();
        index = arguments.getInt("key");

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
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.KEY_ID + " = '" + index + "'", null);

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
                startActivity(new Intent(this, users.class));
                finish();
                break;
            case R.id.button_save:
                String login = username_field.getText().toString();
                String password = password_field.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, login);
                contentValues.put(DBHelper.KEY_PASSWORD, password);
                database.update(DBHelper.TABLE_USERS, contentValues,DBHelper.KEY_ID + " = '" + index + "'", null);
                startActivity(new Intent(this, users.class));
                finish();
                break;
            case R.id.button_delete:
                database.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + " = " + index, null);
                startActivity(new Intent(this, users.class));
                finish();
                break;
        }
    }




}
