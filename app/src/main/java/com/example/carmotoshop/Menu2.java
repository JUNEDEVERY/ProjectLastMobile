package com.example.carmotoshop;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Menu2 extends AppCompatActivity implements View.OnClickListener {
    Button btnCar, btnMotorcycle;
    ImageView imag, imag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        btnCar = findViewById(R.id.btnCar);
        btnCar.setOnClickListener(this);

        btnMotorcycle = findViewById(R.id.btnMotorcycles);
        btnMotorcycle.setOnClickListener(this);


        imag = (ImageView) findViewById(R.id.car_image);
        imag2 = (ImageView) findViewById(R.id.moto_image);
        imag.setOnClickListener(this);
        imag2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.lic_acc:
                startActivity(new Intent(this, ChangeLicAcc.class));
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCar:
                startActivity(new Intent(this, CarChoice.class));
                break;
            case R.id.car_image:
                startActivity(new Intent(this, CarChoice.class));
                break;
            case R.id.btnMotorcycles:
                startActivity(new Intent(this, MotoChoice.class));
                break;
            case R.id.moto_image:
                startActivity(new Intent(this, MotoChoice.class));
                break;

        }
    }
}
