package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.AppClockBean;
import com.iyuba.voaEnglish.view.home.AppClockContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AppClockModel implements AppClockContract.AppClockModel {
    @Override
    public Disposable getAppClock(int uid, int appId, String time, AppClockContract.AppClockCallBack appClockCallBack) {
        return Network
                .getRequest()
                .getAppClock("http://app.iyuba.cn/getShareInfoShow.jsp",uid,appId,time)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AppClockBean>() {
                    @Override
                    public void accept(AppClockBean appClockBean) throws Exception {
                        appClockCallBack.appClockSuccess(appClockBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        appClockCallBack.appClockError((Exception) throwable);
                    }
                });
    }
}
