package com.khieuthichien.qunlsinhvin.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.khieuthichien.qunlsinhvin.R;
import com.khieuthichien.qunlsinhvin.adapter.LopAdapter;
import com.khieuthichien.qunlsinhvin.adapter.SinhvienAdapter;
import com.khieuthichien.qunlsinhvin.databse.DatabaseHelper;
import com.khieuthichien.qunlsinhvin.model.Lop;
import com.khieuthichien.qunlsinhvin.model.Sinhvien;

import java.util.ArrayList;
import java.util.List;

public class QuanlysinhvienActivity extends AppCompatActivity {

    private ImageView imgback;
    private RecyclerView recyclerview;

    private List<Sinhvien> sinhvienList;
    private SinhvienAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlysinhvien);

        imgback = findViewById(R.id.imgback);
        recyclerview = findViewById(R.id.recyclerview);

        databaseHelper = new DatabaseHelper(this);
        sinhvienList = new ArrayList<>();

        sinhvienList = databaseHelper.getAllSinhvien();

        adapter = new SinhvienAdapter(QuanlysinhvienActivity.this, sinhvienList, databaseHelper);
        recyclerview.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlysinhvienActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fabthem = (FloatingActionButton) findViewById(R.id.fabthem);
        fabthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QuanlysinhvienActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_add_sinhvien, null);
                builder.setView(dialogView);
                final Dialog dialog = builder.show();

                final EditText edtmasinhvien;
                final EditText edttensinhvien;
                final Spinner splop;
                final Button btnadd;
                final Button btncancel;

                edtmasinhvien = dialogView.findViewById(R.id.edtmasinhvien);
                edttensinhvien = dialogView.findViewById(R.id.edttensinhvien);
                splop = dialogView.findViewById(R.id.splop);
                btnadd = dialogView.findViewById(R.id.btnadd);
                btncancel = dialogView.findViewById(R.id.btncancel);

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String masv = edtmasinhvien.getText().toString().trim();
                        String tensv = edttensinhvien.getText().toString().trim();

                        if (masv.equals("")){
                            edtmasinhvien.setError(getString(R.string.notify_empty_bo_trong));
                            return;
                        }
                        if (tensv.equals("")){
                            edttensinhvien.setError(getString(R.string.notify_empty_bo_trong));
                            return;
                        }

                        Sinhvien sinhvien = new Sinhvien();
                        sinhvien.setMasinhvien(masv);
                        sinhvien.setTensinhvien(tensv);
                        sinhvien.setMalop("");

                        databaseHelper.insertSinhvien(sinhvien);
                        databaseHelper.getAllSinhvien();

                        startActivity(new Intent(QuanlysinhvienActivity.this, QuanlysinhvienActivity.class));
                        Toast.makeText(QuanlysinhvienActivity.this, "Da them", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });
    }

}
