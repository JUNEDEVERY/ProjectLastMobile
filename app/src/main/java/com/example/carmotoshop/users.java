package com.example.carmotoshop;


import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class users extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnClear;
    EditText username_field, password_field;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    String adminUser = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersbd);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        username_field = findViewById(R.id.username_field);
        password_field = findViewById(R.id.password_field);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        UpdateTable();

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
    public void UpdateTable() {
        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int loginRouteIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int passwordRouteIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);

            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();
            do {
                if(!adminUser.equals(cursor.getString(loginRouteIndex))) {
                    TableRow dbOutputRow = new TableRow(this);
                    dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    TextView outputLogin = new TextView(this);
                    params.weight = 3.0f;
                    outputLogin.setLayoutParams(params);
                    outputLogin.setText(cursor.getString(loginRouteIndex));
                    outputLogin.setTextSize(12);
                    dbOutputRow.addView(outputLogin);

                    TextView outputPassword = new TextView(this);
                    params.weight = 3.0f;
                    outputPassword.setLayoutParams(params);
                    outputPassword.setText(cursor.getString(passwordRouteIndex));
                    outputPassword.setTextSize(12);
                    dbOutputRow.addView(outputPassword);

                    Button deleteBtn = new Button(this);
                    deleteBtn.setOnClickListener(this);
                    params.weight = 1.0f;
                    deleteBtn.setLayoutParams(params);
                    deleteBtn.setText("Изменить\nзапись");
                    deleteBtn.setTextSize(12);
                    deleteBtn.setId(cursor.getInt(idIndex));
                    dbOutputRow.addView(deleteBtn);

                    dbOutput.addView(dbOutputRow);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                String login = username_field.getText().toString();
                String password = password_field.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, login);
                contentValues.put(DBHelper.KEY_PASSWORD, password);

                database.insert(DBHelper.TABLE_USERS, null, contentValues);
                username_field.setText("");
                password_field.setText("");
                UpdateTable();
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_USERS, null, null);
                TableLayout dbOutput = findViewById(R.id.dbOutput);
                dbOutput.removeAllViews();
                UpdateTable();
                break;

            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                int indexStr = outputDB.indexOfChild(outputDBRow);
                int index = 0;
                Cursor cursorUpdater = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.KEY_NAME + " <> '" + adminUser + "'", null);

                if (cursorUpdater != null) {
                    cursorUpdater.moveToPosition(indexStr);
                    index =  cursorUpdater.getInt(0);
                }
                Intent intent = new Intent(this, ChangeUser.class);
                Bundle b = new Bundle();
                b.putInt("key", index);
                intent.putExtras(b);
                startActivity(intent);
                finish();
        }
    }
}