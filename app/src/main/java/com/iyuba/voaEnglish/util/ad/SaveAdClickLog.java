package com.iyuba.voaEnglish.util.ad;



import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.dao.AdFailCountDao;
import com.iyuba.voaEnglish.dao.AdLogDao;
import com.iyuba.voaEnglish.db.AdFailCount;
import com.iyuba.voaEnglish.db.AdLog;
import com.iyuba.voaEnglish.db.AdLogDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存储请求广告的日志
 */
public class SaveAdClickLog implements Runnable {

    private BannerAd bannerAd;
    private String adKind;//广告类型

    private String operation;

    private final AdLogDao adLogDao;
    private AdFailCountDao adFailCountDao;
    private SimpleDateFormat simpleDateFormat;

    public SaveAdClickLog(BannerAd bannerAd, String adKind, String operation) {

        this.bannerAd = bannerAd;
        this.adKind = adKind;
        this.operation = operation;

        AdLogDatabase adLogDatabase = AdLogDatabase.getInstence(MyApplication.getContext());
        adLogDao = adLogDatabase.getAdLogDao();
        adFailCountDao = adLogDatabase.getAdFailCountDao();
        simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
    }

    @Override
    public void run() {

        AdLog adLog = new AdLog();
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        adLog.setTime(simpleDateFormat.format(new Date()));
        adLog.setType(bannerAd.getType());
        adLog.setKind(adKind);
        adLog.setOperation(operation);
        adLogDao.insert(adLog);

        //如果正常返回了广告，则重置连续请求失败的次数
        if (operation.equals(AdLog.OPERATION_RETURN)) {

            SimpleDateFormat ymdSdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            ymdSdf.applyPattern("yyyy-MM-dd");

            AdFailCount adFailCount = new AdFailCount();
            adFailCount.setTime(ymdSdf.format(new Date()));
            adFailCount.setType(bannerAd.getType());
            adFailCount.setKind(adKind);
            adFailCount.setCount(0);
            adFailCountDao.update(adFailCount);
        }
    }
}