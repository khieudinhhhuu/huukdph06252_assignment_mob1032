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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.khieuthichien.qunlsinhvin.R;
import com.khieuthichien.qunlsinhvin.adapter.LopAdapter;
import com.khieuthichien.qunlsinhvin.databse.DatabaseHelper;
import com.khieuthichien.qunlsinhvin.model.Lop;

import java.util.ArrayList;
import java.util.List;

public class QuanlylopActivity extends AppCompatActivity {

    private ImageView imgback;
    private RecyclerView recyclerview;

    private List<Lop> lopList;
    private LopAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlylop);

        imgback = findViewById(R.id.imgback);
        recyclerview = findViewById(R.id.recyclerview);

        databaseHelper = new DatabaseHelper(this);
        lopList = new ArrayList<>();

        lopList = databaseHelper.getAllLop();

        adapter = new LopAdapter(QuanlylopActivity.this,lopList, databaseHelper);
        recyclerview.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanlylopActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fabthem = findViewById(R.id.fabthem);
        fabthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanlylopActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_add_lop, null);
                builder.setView(dialogView);
                final Dialog dialog = builder.show();

                final EditText edtidlop;
                final EditText edtmalop;
                final EditText edttenlop;
                final Button btnadd;
                final Button btncancel;

                edtidlop = dialogView.findViewById(R.id.edtidlop);
                edtmalop = dialogView.findViewById(R.id.edtmalop);
                edttenlop = dialogView.findViewById(R.id.edttenlop);
                btnadd = dialogView.findViewById(R.id.btnadd);
                btncancel = dialogView.findViewById(R.id.btncancel);

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = Integer.parseInt(edtidlop.getText().toString().trim());
                        String ma = edtmalop.getText().toString().trim();
                        String ten = edttenlop.getText().toString().trim();

                        if (edtidlop.getText().toString().equals("")) {
                            edtidlop.setError(getString(R.string.notify_empty_name));
                            return;
                        }
                        if (ma.equals("")) {
                            edtmalop.setError(getString(R.string.notify_empty_name));
                            return;
                        }
                        if (ten.equals("")) {
                            edttenlop.setError(getString(R.string.notify_empty_name));
                            return;
                        }

                        Lop lop = new Lop();
                        lop.setIdlop(id);
                        lop.setMalop(ma);
                        lop.setTenlop(ten);

                        databaseHelper.insertLop(lop);
                        databaseHelper.getAllLop();

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edttenlop.getWindowToken(), 0);

                        Intent intent = new Intent(QuanlylopActivity.this, QuanlylopActivity.class);
                        startActivity(intent);

                        Toast.makeText(QuanlylopActivity.this, "đã thêm", Toast.LENGTH_SHORT).show();
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
