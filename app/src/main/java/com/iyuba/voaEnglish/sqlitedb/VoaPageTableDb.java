package com.iyuba.voaEnglish.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VoaPageTableDb extends SQLiteOpenHelper {
    //创建数据表
    public static final String CREATE_PAGE_TABLE = "create table pageTable("+
            "voaid integer primary key,"+
            "title text,"+
            "titleCn text,"+
            "readNum text,"+
            "createTime text)";

    private Context mcontext;


    public VoaPageTableDb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
