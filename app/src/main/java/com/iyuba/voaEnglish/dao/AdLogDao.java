package com.iyuba.voaEnglish.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.iyuba.voaEnglish.db.AdLog;

import java.util.List;

@Dao
public interface AdLogDao {

    @Query("select * from adlog")
    List<AdLog> getAll();

    @Insert
    void insert(AdLog... adLogs);


    /**
     * 获取某一个操作、某一家、某一类型广告 的数量
     *
     * @param operation
     * @param kind
     * @param type
     * @param today     2024-02-04
     * @return
     */
    @Query("select count(*) from adlog where operation =:operation and kind=:kind and type=:type and time like :today")
    int getAdlog(String operation, String kind, String type, String today);


    /**
     * 获取今天未上传的数据
     *
     * @param today
     * @return
     */
    @Query("select * from adlog where time like :today and upload = 0")
    List<AdLog> getNotUploadAdLogForToday(String today);

    /**
     * 更新数据
     *
     * @param adLogs
     */
    @Update
    void update(AdLog adLogs);
}
