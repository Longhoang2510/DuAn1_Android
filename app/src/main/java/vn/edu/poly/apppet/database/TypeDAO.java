package vn.edu.poly.apppet.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.apppet.Constant;
import vn.edu.poly.apppet.model.Type;

public class TypeDAO implements Constant {

    private DatabaseHelper databaseHelper;

    public TypeDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertType(Type type) {
        // xin quyen ghi sqlite
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Su dung ContentValues de dua du lieu vao DB
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_ID, type.id);

        long result = sqLiteDatabase.insert(TABLE_TYPE, null, contentValues);

        sqLiteDatabase.close();

        return result;

    }


    public long deleteType(String typeID) {

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        long result = sqLiteDatabase.delete(TABLE_TYPE,
                TB_COLUMN_ID + "=?", new String[]{typeID});

        sqLiteDatabase.close();
        return result;
    }



    public long updateType(Type type) {

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Su dung ContentValues de dua du lieu vao DB
        ContentValues contentValues = new ContentValues();
        contentValues.put(TB_COLUMN_ID, type.id);


        long result = sqLiteDatabase.update(TABLE_TYPE, contentValues,
                TB_COLUMN_ID + "=?", new String[]{type.id});

        sqLiteDatabase.close();
        return result;
    }



    public List<String> getAllType() {

        List<String> types = new ArrayList<> ();

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        // viet cau lenh select all

        String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_TYPE;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_USERS, null);
        if (cursor.moveToFirst()) {
            do {

                String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_ID));

                Type type = new Type();

                type.id = id;



                types.add(id);

            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();
        return types;
    }
    public List<Type> getAllType2() {

        List<Type> types = new ArrayList<> ();

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        // viet cau lenh select all

        String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_TYPE;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_USERS, null);
        if (cursor.moveToFirst()) {
            do {

                String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_ID));

                Type type = new Type();

                type.id = id;



                types.add(type);

            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();
        return types;
    }




    public Type getTypeByID(String typeID) {

        Type typeBook = null;
        // xin quyen ghi sqlite
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        // query tim kiem user voi username = tham so truyen vao

        // 1: ten bang
        //2 : array cot can lay du lieu
        // 3: ten cot dung de query
        // 4 : gia tri can tim
        // 5,6,7 : null. la cac cau lenh xap xep dieu kien...
        Cursor cursor = sqLiteDatabase.query(TABLE_TYPE,
                new String[]{TB_COLUMN_ID},
                TB_COLUMN_ID + "=?", new String[]{typeID},
                null, null, null);

        // neu cursor co gia tri
        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_ID));

            typeBook = new Type();

            typeBook.id = id;

        }
        return typeBook;
    }






}
