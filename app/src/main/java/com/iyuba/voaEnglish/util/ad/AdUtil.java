package com.iyuba.voaEnglish.util.ad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;


import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.db.AdLog;
import com.iyuba.voaEnglish.entity.AdSubmitEventbus;
import com.iyuba.voaEnglish.model.networkCertificates.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 广告上传
 */
public class AdUtil {

//    public static void submitAd(String type, String Platform, String space) {
//
//        //上传
//        AdSubmitEventbus adSubmitEventbus = new AdSubmitEventbus();
//        adSubmitEventbus.setAppid(MyApplication.getContext().getString(R.string.appid));
//        adSubmitEventbus.setDate_time(System.currentTimeMillis() / 1000 + "");
//        adSubmitEventbus.setDevice(Build.BRAND);
//        adSubmitEventbus.setDeviceid(" ");
//        SharedPreferences userSp = MyApplication.getContext().getSharedPreferences("userInfo", 0);
//        String uid = userSp.getString("uid", "0");
//        adSubmitEventbus.setUid(uid);
//        adSubmitEventbus.setPackageStr(MyApplication.getContext().getPackageName());
//        adSubmitEventbus.setOs("2");
//
//        List<AdSubmitEventbus.Ads> adsList = new ArrayList<>();
//        AdSubmitEventbus.Ads ads = new AdSubmitEventbus.Ads();
//        ads.setType(type);
//        ads.setPlatform(Platform);
//        ads.setAd_space(space);
//        adsList.add(ads);
//        adSubmitEventbus.setAds(adsList);
//        EventBus.getDefault().post(adSubmitEventbus);
//    }
    public static AdSubmitEventbus createAdSubmitEventbus(List<AdLog> adLogList) {

        //上传
        AdSubmitEventbus adSubmitEventbus = new AdSubmitEventbus();
        adSubmitEventbus.setAppid(Constant.APPID + "");
        adSubmitEventbus.setDate_time(System.currentTimeMillis() / 1000 + "");
        adSubmitEventbus.setDevice(Build.BRAND);
        adSubmitEventbus.setDeviceid(Constant.oaid);
        SharedPreferences preferences =MyApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String uid = preferences.getString("useruid", "0");

        adSubmitEventbus.setUid(uid);
        adSubmitEventbus.setPackageStr(MyApplication.getContext().getPackageName());
        adSubmitEventbus.setOs("2");

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");

        List<AdSubmitEventbus.Ads> adsList = new ArrayList<>();
        for (int i = 0; i < adLogList.size(); i++) {

            AdLog adLog = adLogList.get(i);

            AdSubmitEventbus.Ads ads = new AdSubmitEventbus.Ads();
            //操作
            if (adLog.getOperation().equals(AdLog.OPERATION_REQUEST)) {//0

                ads.setType("0");
            } else if (adLog.getOperation().equals(AdLog.OPERATION_RETURN)) {//1
                ads.setType("1");
            } else {//点击
                ads.setType("2");
            }
            //广告类型
            String kind = adLog.getKind();
            if (kind.equals(AdLog.KIND_BANNER)) {

                ads.setAd_space("1");
            } else if (kind.equals(AdLog.KIND_DRAW_VIDEO)) {
                ads.setAd_space("6");
            } else if (kind.equals(AdLog.KIND_INCENTIVE)) {
                ads.setAd_space("4");
            } else if (kind.equals(AdLog.KIND_TEMPLATE)) {
                ads.setAd_space("2");
            } else if (kind.equals(AdLog.KIND_SPLASH)) {
                ads.setAd_space("3");
            } else if (kind.equals(AdLog.KIND_TABLE_SCREEN)) {
                ads.setAd_space("5");
            }
            //广告名称
            String name = adLog.getType();
            if (name.equals(AdLog.NAME_BD)) {

                ads.setPlatform("1");
            } else if (name.equals(AdLog.NAME_KS)) {
                ads.setPlatform("4");
            } else if (name.equals(AdLog.NAME_YLH)) {
                ads.setPlatform("2");
            } else if (name.equals(AdLog.NAME_CSJ)) {
                ads.setPlatform("3");
            } else {
                ads.setPlatform("0");
            }

            try {
                Date date = simpleDateFormat.parse(adLog.getTime());
                ads.setDate_time(date.getTime() / 1000 + "");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            adsList.add(ads);
        }

        adSubmitEventbus.setAds(adsList);
        return adSubmitEventbus;
    }
}
