package vn.edu.poly.apppet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.edu.poly.apppet.Constant;

public class DatabaseHelper extends SQLiteOpenHelper implements Constant {




    public DatabaseHelper(Context context) {
        super (context, "BookManger01", null, 1);
    }






    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL (CREATE_TABLE_PET);
        sqLiteDatabase.execSQL (CREATE_TABLE_TYPE);
        sqLiteDatabase.execSQL (CREATE_TABLE_USER);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL ("DROP TABLE IF EXISTS " + TABLE_PET);
        sqLiteDatabase.execSQL ("DROP TABLE IF EXISTS " + TABLE_TYPE);
        sqLiteDatabase.execSQL ("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate (sqLiteDatabase);

    }




}
