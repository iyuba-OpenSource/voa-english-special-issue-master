package com.iyuba.voaEnglish.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * 请求广告连续失败的表,用来处理
 */
@Entity(primaryKeys = {"type", "time", "kind"})
public class AdFailCount {

    /**
     * 哪家的广告
     */
    @NonNull
    private String type;

    /**
     * 创建时间 2024-02-18
     */
    @NonNull
    private String time;

    /**
     * 广告类型  banner、template、splash
     */
    @NonNull
    private String kind;

    /**
     * 次数
     */
    private int count;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
