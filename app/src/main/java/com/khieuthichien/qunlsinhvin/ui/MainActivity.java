package com.khieuthichien.qunlsinhvin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.khieuthichien.qunlsinhvin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void quanlylop(View view) {
        startActivity(new Intent(getApplicationContext(), QuanlylopActivity.class));
    }

    public void quanlysinhvien(View view) {
        startActivity(new Intent(getApplicationContext(), QuanlysinhvienActivity.class));
    }
}
