package com.example.carmotoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.drawable.AnimationDrawable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameField, passwordField;
    Button loginBtn, passwordBtn;

    DBHelper dbHelper;
    SQLiteDatabase database;

    String adminUser = "admin";
    String adminPassword = "admin";

    public static String user;
//
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = findViewById(R.id.moto_img);
        // устанавливаем ресурс анимации
        img.setBackgroundResource(R.drawable.gifcar);
        // получаем объект анимации
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        // по нажатию на ImageView
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // запускаем анимацию
                frameAnimation.start();
            }
        });
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);


        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        passwordBtn = findViewById(R.id.passwordBtn);
        passwordBtn.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        usernameField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                usernameField.setHint("");
            else
                usernameField.setHint("Логин");
        });

        passwordField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                passwordField.setHint("");
            else
                passwordField.setHint("Пароль");
        });
        add_admin();
    }


        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.loginBtn:
                    Cursor logCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                    boolean logged = false;
                    if(logCursor.moveToFirst()){
                        int usernameIndex = logCursor.getColumnIndex(DBHelper.KEY_NAME);
                        int passwordIndex = logCursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                        do{
                            if(usernameField.getText().toString().equals(adminUser) && passwordField.getText().toString().equals(adminPassword)) {
                                startActivity(new Intent(this, Menu1.class));
                                finish();
                                logged = true;
                                break;
                            }
                            if(usernameField.getText().toString().equals(logCursor.getString(usernameIndex)) && passwordField.getText().toString().equals(logCursor.getString(passwordIndex))){
                                user = logCursor.getString(usernameIndex);
                                startActivity(new Intent(this, Menu2.class));
                                finish();
                                logged = true;
                                break;
                            }
                        }while (logCursor.moveToNext());
                    }
                    logCursor.close();
                    if(!logged) Toast.makeText(this, "Введённая комбинация логина и пароля не была найдена", Toast.LENGTH_LONG).show();
                    break;
                case R.id.passwordBtn:
                    Cursor signCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                    boolean finded = false;
                    if(signCursor.moveToFirst()){
                        int usernameIndex = signCursor.getColumnIndex(DBHelper.KEY_NAME);
                        do{
                            if(usernameField.getText().toString().equals(signCursor.getString(usernameIndex))){
                                Toast.makeText(this, "Введённый логин уже зарегистрирован", Toast.LENGTH_LONG).show();
                                finded = true;
                                break;
                            }
                        }while (signCursor.moveToNext());
                    }
                    if(!finded){
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBHelper.KEY_NAME, usernameField.getText().toString());
                        contentValues.put(DBHelper.KEY_PASSWORD, passwordField.getText().toString());
                        database.insert(DBHelper.TABLE_USERS, null, contentValues);
                        Toast.makeText(this, "Вы успешно зарегистрированы", Toast.LENGTH_LONG).show();
                    }
                    signCursor.close();
                    break;
            }
        }
    public void add_admin(){
        Cursor signCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        int check_adm = 0;
        if(signCursor.moveToFirst()){
            int usernameIndex = signCursor.getColumnIndex(DBHelper.KEY_NAME);
            do{
                if(adminUser.equals(signCursor.getString(usernameIndex))){
                    check_adm ++;
                    break;
                }
            }while (signCursor.moveToNext());
        }
        if(check_adm == 0){
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper.KEY_NAME, adminUser);
            contentValues.put(DBHelper.KEY_PASSWORD, adminPassword);

            database.insert(DBHelper.TABLE_USERS, null, contentValues);
        }
        signCursor.close();
    }
    }

