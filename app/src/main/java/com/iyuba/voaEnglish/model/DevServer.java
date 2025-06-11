package com.iyuba.voaEnglish.model;



import com.iyuba.voaEnglish.model.bean.AdEntryBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DevServer {
    /**
     * 获取广告
     *
     * @param appId
     * @param flag  2 广告顺序  5自家广告内容
     * @param uid
     * @return
     */
    @GET
    Observable<List<AdEntryBean>> getAdEntryAll(@Url String url, @Query("appId") int appId, @Query("flag") int flag, @Query("uid") int uid);
}
