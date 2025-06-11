package com.iyuba.voaEnglish.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OriginalTableDb extends SQLiteOpenHelper {
    //创建数据库语句
    public static final String CREATE_ORIGINAL_DB = "create table originalTable(" +
            "voaid integer ," +
            "paraid integer ," +
            "indexid integer," +
            "pageEn text," +
            "pageCn text," +
            "primary key (voaid,paraid,indexid))";

    private Context mcontext;


    public OriginalTableDb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ORIGINAL_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
