package com.iyuba.voaEnglish.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iyuba.voaEnglish.dao.AdFailCountDao;
import com.iyuba.voaEnglish.dao.AdLogDao;


@Database(entities = {AdLog.class, AdFailCount.class}, version = 2)
public abstract class AdLogDatabase extends RoomDatabase {

    public static AdLogDatabase myDatabase;


    public static synchronized AdLogDatabase getInstence(Context context) {

        if (myDatabase == null) {

            myDatabase = Room.databaseBuilder(context, AdLogDatabase.class, "ad_log3.db")
                    .build();
        }
        return myDatabase;
    }


    /**
     * 声明dao
     *
     * @return
     */
    public abstract AdLogDao getAdLogDao();

    public abstract AdFailCountDao getAdFailCountDao();
}
