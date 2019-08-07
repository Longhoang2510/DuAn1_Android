package vn.edu.poly.apppet.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.edu.poly.apppet.Constant;
import vn.edu.poly.apppet.model.User;

public class UserDAO implements Constant{

    private DatabaseHelper databaseHelper;

    public UserDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertUser(User user) {

        // xin quyen ghi sqlite
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        // Su dung ContentValues de dua du lieu vao DB
        ContentValues contentValues = new ContentValues ();
        contentValues.put (TB_COLUMN_IDUSER, user.id);
        contentValues.put (TB_COLUMN_NAME, user.name);
        contentValues.put (TB_COLUMN_OLD, user.old);
        contentValues.put (TB_COLUMN_NAMETYPE, user.type);
        contentValues.put (TB_COLUMN_AVT, user.imv);

        long result = sqLiteDatabase.insert(TABLE_USER, null, contentValues);

        sqLiteDatabase.close ();

        return result;

    }

    public long deleteUser(String userId) {

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        long result = sqLiteDatabase.delete (TABLE_USER,
                TB_COLUMN_IDUSER + "=?", new String[]{userId});

        sqLiteDatabase.close ();
        return result;

    }


    public long updateUser(User user) {


        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        // Su dung ContentValues de dua du lieu vao DB
        ContentValues contentValues = new ContentValues ();
        contentValues.put (TB_COLUMN_IDUSER, user.id);
        contentValues.put (TB_COLUMN_NAME, user.name);
        contentValues.put (TB_COLUMN_OLD, user.old);
        contentValues.put (TB_COLUMN_NAMETYPE, user.type);
        contentValues.put (TB_COLUMN_AVT, user.imv);

        String idd = user.id+"";

        long result = sqLiteDatabase.update (TABLE_USER, contentValues,
                TB_COLUMN_IDUSER + "=?", new String[]{idd.trim ()});

        sqLiteDatabase.close ();
        return result;

    }


    public  String getName(String id){

        String name = "null";
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        Cursor cursor = sqLiteDatabase.query (TABLE_USER, new String[]{TB_COLUMN_NAME},
                TB_COLUMN_IDUSER + "=?", new String[]{id}, null, null, null);

        if(cursor != null && cursor.moveToFirst ()){
            name = cursor.getString (cursor.getColumnIndex (TB_COLUMN_NAME));


        }


        return name;
    };

    public  byte[] getImage(String id){

        byte[] imv = null;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        Cursor cursor = sqLiteDatabase.query (TABLE_USER, new String[]{TB_COLUMN_AVT},
                TB_COLUMN_IDUSER + "=?", new String[]{id}, null, null, null);

        if(cursor != null && cursor.moveToFirst ()){
            imv = cursor.getBlob (cursor.getColumnIndex (TB_COLUMN_AVT));


        }

        return imv;
    };


    public User getUserById(String idUser){

        User user = null;
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();


        Cursor cursor = sqLiteDatabase.query(TABLE_USER,
                new String[]{TB_COLUMN_IDUSER, TB_COLUMN_NAME, TB_COLUMN_OLD, TB_COLUMN_NAMETYPE, TB_COLUMN_AVT},
                TB_COLUMN_IDUSER + "=?", new String[]{idUser},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex(TB_COLUMN_IDUSER));
            String name = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NAME));
            String old = cursor.getString (cursor.getColumnIndex(TB_COLUMN_OLD));
            String type = cursor.getString(cursor.getColumnIndex(TB_COLUMN_NAMETYPE));
            byte[] imv = cursor.getBlob (cursor.getColumnIndex (TB_COLUMN_AVT));

            user = new User ();

            user.id = id;
            user.name = name;
            user.old = old;
            user.type = type;
            user.imv = imv;



        }
        return user;





    };






}
