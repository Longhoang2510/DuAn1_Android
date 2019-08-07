package vn.edu.poly.apppet.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.apppet.Constant;
import vn.edu.poly.apppet.model.Pet;

public class PetDAO implements Constant {

    private DatabaseHelper databaseHelper;

    public PetDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long insertPet(Pet pet) {

        // xin quyen ghi sqlite
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        // Su dung ContentValues de dua du lieu vao DB
        ContentValues contentValues = new ContentValues ();
        contentValues.put (TB_COLUMN_IDPET, pet.idPet);
        contentValues.put (TB_COLUMN_IDTYPE, pet.idType);
        contentValues.put (TB_COLUMN_TEN, pet.ten);
        contentValues.put (TB_COLUMN_NGAY, pet.ngay);
        contentValues.put (TB_COLUMN_GIO, pet.gio);
        contentValues.put (TB_COLUMN_NOTE, pet.note);

        long result = sqLiteDatabase.insert (TABLE_PET, null, contentValues);


        sqLiteDatabase.close ();

        return result;

    }


    public long deletePet(String petID) {

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        long result = sqLiteDatabase.delete (TABLE_PET,
                TB_COLUMN_IDPET + "=?", new String[]{petID});

        sqLiteDatabase.close ();
        return result;

    }


    public long updatePet(Pet pet) {


        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        // Su dung ContentValues de dua du lieu vao DB
        ContentValues contentValues = new ContentValues ();
        contentValues.put (TB_COLUMN_IDPET, pet.idPet);
        contentValues.put (TB_COLUMN_IDTYPE, pet.idType);
        contentValues.put (TB_COLUMN_TEN, pet.ten);
        contentValues.put (TB_COLUMN_NGAY, pet.ngay);
        contentValues.put (TB_COLUMN_GIO, pet.gio);
        contentValues.put (TB_COLUMN_NOTE, pet.note);

        long result = sqLiteDatabase.update (TABLE_PET, contentValues,
                TB_COLUMN_IDPET + "=?", new String[]{pet.idPet});

        sqLiteDatabase.close ();
        return result;

    }

    public List<Pet> getAllPet() {

        List<Pet> Pets = new ArrayList<> ();

        // xin quyen doc du lieu

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase ();

        // viet cau lenh select all

        String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_PET;
        Cursor cursor = sqLiteDatabase.rawQuery (SELECT_ALL_USERS, null);
        if (cursor.moveToLast ()) {
            do {

                String idpet = cursor.getString (cursor.getColumnIndex (TB_COLUMN_IDPET));
                String idtype = cursor.getString (cursor.getColumnIndex (TB_COLUMN_IDTYPE));
                String ten = cursor.getString (cursor.getColumnIndex (TB_COLUMN_TEN));
                String ngay = cursor.getString (cursor.getColumnIndex (TB_COLUMN_NGAY));
                String gio = cursor.getString (cursor.getColumnIndex (TB_COLUMN_GIO));
                String note = cursor.getString (cursor.getColumnIndex (TB_COLUMN_NOTE));

                Pet pet = new Pet ();

                pet.idPet = idpet;
                pet.idType = idtype;
                pet.ten = ten;
                pet.ngay = ngay;
                pet.gio = gio;
                pet.note = note;

                Pets.add (pet);

            } while (cursor.moveToPrevious ());
        }

        sqLiteDatabase.close ();

        return Pets;

    }



}
