package com.khieuthichien.qunlsinhvin.databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.khieuthichien.qunlsinhvin.model.Lop;
import com.khieuthichien.qunlsinhvin.model.Sinhvien;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lop_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_LOP = "lop";
    public static final String TABLE_SINHVIEN = "sinhvien";

    public static final String COLUMN_ID_LOP = "idlop";
    public static final String COLUMN_MA_LOP = "malop";
    public static final String COLUMN_TEN_LOP = "tenlop";

    public static final String COLUMN_ID_SINHVIEN = "idsinhvien";
    public static final String COLUMN_TEN_SINHVIEN = "tensinhvien";
    public static final String SV_COLUMN_MA_LOP = "malop";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_LOP = " CREATE TABLE " + TABLE_LOP + "(" +
                "" + COLUMN_ID_LOP + " INTEGER PRIMARY KEY, " +
                "" + COLUMN_MA_LOP + " NVARCHAR, " +
                "" + COLUMN_TEN_LOP + " TEXT " +
                ")";

        String CREATE_TABLE_SINHVIEN = " CREATE TABLE " + TABLE_SINHVIEN + "(" +
                "" + COLUMN_ID_SINHVIEN + " INTEGER PRIMARY KEY, " +
                "" + COLUMN_TEN_SINHVIEN + " NVARCHAR, " +
                "" + SV_COLUMN_MA_LOP + " NVARCHAR " +
                ")";

        db.execSQL(CREATE_TABLE_LOP);
        db.execSQL(CREATE_TABLE_SINHVIEN);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LOP);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        onCreate(db);
    }

    public void insertLop(Lop lop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID_LOP, lop.getIdlop());
        values.put(COLUMN_MA_LOP, lop.getMalop());
        values.put(COLUMN_TEN_LOP, lop.getTenlop());

        db.insert(TABLE_LOP, null, values);
        db.close();
    }


    public Lop getLop(String idlop) {

        Lop lop = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOP,
                new String[]{ COLUMN_ID_LOP, COLUMN_MA_LOP, COLUMN_TEN_LOP},
                COLUMN_ID_LOP + "=?",
                new String[]{String.valueOf(idlop)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_LOP));

            String ma = cursor.getString(cursor.getColumnIndex(COLUMN_MA_LOP));

            String ten = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_LOP));

            lop = new Lop( id, ma, ten);
        }
        cursor.close();
        return lop;

    }


    public List<Lop> getAllLop() {

        List<Lop> lopList = new ArrayList<>();

        String SELECT_ALL_LOP = "SELECT * FROM " + TABLE_LOP;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(SELECT_ALL_LOP, null);

        cursor.moveToFirst();

        do {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_LOP));

            String ma = cursor.getString(cursor.getColumnIndex(COLUMN_MA_LOP));

            String ten = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_LOP));

            Lop lop = new Lop();
            lop.setIdlop(id);
            lop.setMalop(ma);
            lop.setTenlop(ten);

            lopList.add(lop);

        }while (cursor.moveToNext());

        cursor.close();
        db.close();

        return lopList;

    }

    public int updateLop(Lop lop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_MA_LOP, lop.getMalop());
        values.put(COLUMN_TEN_LOP, lop.getTenlop());

        return db.update(TABLE_LOP, values, COLUMN_ID_LOP + " = ?", new String[]{String.valueOf(lop.getMalop())});

    }

    public void deleteLop(Lop idlop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOP, COLUMN_ID_LOP + " = ?", new String[]{String.valueOf(idlop.getIdlop())});
        db.close();

    }


    //table sv
    public void insertSinhvien(Sinhvien sinhvien){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ID_SINHVIEN, sinhvien.getMasinhvien());
        values.put(COLUMN_TEN_SINHVIEN, sinhvien.getTensinhvien());
        values.put(SV_COLUMN_MA_LOP, sinhvien.getMalop());

        db.insert(TABLE_SINHVIEN, null, values);
        db.close();
    }

    public Sinhvien getSinhvien(String idsinhvien){
        Sinhvien sinhvien = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SINHVIEN,
                new String[]{ COLUMN_ID_SINHVIEN, COLUMN_TEN_SINHVIEN, SV_COLUMN_MA_LOP},
                COLUMN_ID_SINHVIEN + "=?",
                new String[]{String.valueOf(idsinhvien)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            String idsv = cursor.getString(cursor.getColumnIndex(COLUMN_ID_SINHVIEN));

            String tensv = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_SINHVIEN));

            String malopsv = cursor.getString(cursor.getColumnIndex(SV_COLUMN_MA_LOP));

            sinhvien = new Sinhvien( idsv, tensv, malopsv);
        }
        cursor.close();
        return sinhvien;
    }

    public List<Sinhvien> getAllSinhvien(){
        List<Sinhvien> sinhvienList = new ArrayList<>();

        String SELECT_ALL_SINHVIEN = "SELECT * FROM " + TABLE_SINHVIEN;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(SELECT_ALL_SINHVIEN, null);

        cursor.moveToFirst();

        do {
            String idsv = cursor.getString(cursor.getColumnIndex(COLUMN_ID_SINHVIEN));

            String tensv = cursor.getString(cursor.getColumnIndex(COLUMN_TEN_SINHVIEN));

            String malopsv = cursor.getString(cursor.getColumnIndex(SV_COLUMN_MA_LOP));

            Sinhvien sinhvien = new Sinhvien();
            sinhvien.setMasinhvien(idsv);
            sinhvien.setTensinhvien(tensv);
            sinhvien.setMalop(malopsv);

            sinhvienList.add(sinhvien);

        }while (cursor.moveToNext());

        cursor.close();
        db.close();

        return sinhvienList;
    }

    public int updateSinhvien(Sinhvien sinhvien) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_TEN_SINHVIEN, sinhvien.getTensinhvien());
        values.put(SV_COLUMN_MA_LOP, sinhvien.getMalop());

        return db.update(TABLE_SINHVIEN, values, COLUMN_ID_SINHVIEN + " = ?", new String[]{String.valueOf(sinhvien.getMasinhvien())});

    }

    public void deleteSinhvien(Sinhvien idsinhvien) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SINHVIEN, COLUMN_ID_SINHVIEN + " = ?", new String[]{String.valueOf(idsinhvien.getMasinhvien())});
        db.close();

    }



}
