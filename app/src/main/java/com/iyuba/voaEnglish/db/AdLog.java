package com.iyuba.voaEnglish.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class AdLog {


    /**
     * 广告的名字
     */
    @Ignore
    public static final String NAME_CSJ = "穿山甲";
    @Ignore
    public static final String NAME_YLH = "优量汇";
    @Ignore
    public static final String NAME_KS = "快手";
    @Ignore
    public static final String NAME_BD = "百度";
    @Ignore
    public static final String NAME_YOUDAO = "youdao";

    /**
     * 请求广告
     */
    @Ignore
    public static final String OPERATION_REQUEST = "REQUEST";
    /**
     * 返回广告
     */
    @Ignore
    public static final String OPERATION_RETURN = "RETURN";
    /**
     * 点击广告
     */
    @Ignore
    public static final String OPERATION_CLICK = "CLICK";

    /**
     * 信息流
     */
    @Ignore
    public static final String KIND_TEMPLATE = "TEMPLATE";
    /**
     * banner广告
     */
    @Ignore
    public static final String KIND_BANNER = "BANNER";
    /**
     * 开屏广告
     */
    @Ignore
    public static final String KIND_SPLASH = "SPLASH";

    /**
     * 激励视频广告
     */
    @Ignore
    public static final String KIND_INCENTIVE = "INCENTIVE";

    /**
     * 插屏广告
     */
    @Ignore
    public static final String KIND_TABLE_SCREEN = "TABLE_SCREEN";

    /**
     * draw_video广告
     */
    @Ignore
    public static final String KIND_DRAW_VIDEO = "DRAW_VIDEO";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    /**
     * 哪家的广告
     */
    private String type;
    /**
     * 创建时间
     */
    private String time;

    /**
     * 广告类型  banner、template、splash
     */
    private String kind;


    /**
     * 请求、返回、点击
     */
    private String operation;


    /**
     * 是否上传
     * 0未上传
     * 1已上传
     */
    @ColumnInfo(defaultValue = "0")
    private int upload;


    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
