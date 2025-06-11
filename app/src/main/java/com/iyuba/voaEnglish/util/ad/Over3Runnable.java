package com.iyuba.voaEnglish.util.ad;

import android.os.Handler;
import android.os.Looper;


import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.dao.AdLogDao;
import com.iyuba.voaEnglish.db.AdLog;
import com.iyuba.voaEnglish.db.AdLogDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 是否查过三次
 */
public class Over3Runnable implements Runnable {

    private AdLogDao adLogDao;
    private String type;
    private String kind;

    private String today;

    Callback callback;

    public Over3Runnable(String type, String kind, Callback callback) {

        this.kind = kind;
        this.type = type;
        this.callback = callback;

        AdLogDatabase adLogDatabase = AdLogDatabase.getInstence(MyApplication.getContext());
        adLogDao = adLogDatabase.getAdLogDao();
        SimpleDateFormat todayDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        todayDateFormat.applyPattern("yyyy-MM-dd");
        today = todayDateFormat.format(new Date()) + "%";
    }

    @Override
    public void run() {

        int cCount = adLogDao.getAdlog(AdLog.OPERATION_CLICK, kind, type, today);

        if (callback != null) {

            new Handler(Looper.getMainLooper()).post(() -> {


                if (cCount < 3) {

                    callback.noOver();
                } else {

                    callback.over();
                }
            });
        }
    }

    public interface Callback {

        void over();

        void noOver();
    }
}