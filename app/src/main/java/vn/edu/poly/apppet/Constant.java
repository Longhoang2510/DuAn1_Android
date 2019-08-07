package vn.edu.poly.apppet;

public interface Constant {




    //table boook

    //CREAT TABLE TypeBook (MaTheLoai  CHAR(5) PRIMARY KEY NOT NULL;
    //TYPENAME NVARCHAR(50) NOT NULL;
    //Position

    String TABLE_TYPE = "Type";

    String TB_COLUMN_ID = "IdType";



    String CREATE_TABLE_TYPE = "CREATE TABLE " + TABLE_TYPE+ "(" +
            "" + TB_COLUMN_ID + " PRIMARY KEY NOT NULL" +
            ")";


    //table book

    //CREATE TABLE Book (MaSach NCHAR(5) RIMARY KEY NOT NULL;
    //MaTheLoai   NCHAR(50)   FK,  NOT  NULL
    String TABLE_PET = "Pet";

    String TB_COLUMN_IDPET= "IdPet";
    String TB_COLUMN_IDTYPE = "IdType";
    String TB_COLUMN_TEN = "Ten";
    String TB_COLUMN_NGAY = "Ngay";
    String TB_COLUMN_GIO = "Gio";
    String TB_COLUMN_NOTE = "GhiChu";



    String CREATE_TABLE_PET = "CREATE TABLE " + TABLE_PET + "(" +
            "" + TB_COLUMN_IDPET +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + TB_COLUMN_IDTYPE + " NVARCHAR(50)," +
            "" + TB_COLUMN_TEN + " NVARCHAR(50) NOT NULL," +
            "" + TB_COLUMN_NGAY + "  VARCHAR NULL," +
            "" + TB_COLUMN_GIO + " VARCHAR NOT NULL," +
            "" + TB_COLUMN_NOTE + " VARCHAR" +
            ")";




    String TABLE_USER = "User";

    String TB_COLUMN_IDUSER = "iduser";
    String TB_COLUMN_NAME= "Name";
    String TB_COLUMN_OLD = "Old";
    String TB_COLUMN_NAMETYPE = "NameType";
    String TB_COLUMN_AVT = "Avt";



    String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            "" + TB_COLUMN_IDUSER +  " PRIMARY KEY NOT NULL," +
            "" + TB_COLUMN_NAME + " VARCHAR," +
            "" + TB_COLUMN_OLD + " VARCHAR," +
            "" + TB_COLUMN_NAMETYPE + " NVARCHAR(50)," +
            "" + TB_COLUMN_AVT + " BLOB" +
            ")";



}
