package com.iyuba.voaEnglish.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.iyuba.voaEnglish.db.AdFailCount;


@Dao
public interface AdFailCountDao {


    @Insert
    void insert(AdFailCount... adFailCount);


    /**
     * 查询广告请求失败的次数
     *
     * @param type
     * @param time
     * @param kind
     * @return
     */
    @Query("select * from adfailcount where type=:type and time = :time and kind =:kind")
    AdFailCount getCount(String type, String time, String kind);


    @Update
    void update(AdFailCount adFailCount);


}
