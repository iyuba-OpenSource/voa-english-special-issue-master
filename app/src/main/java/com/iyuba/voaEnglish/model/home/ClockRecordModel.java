package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.ClockRecordBean;
import com.iyuba.voaEnglish.view.home.ClockRecordContract;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ClockRecordModel implements ClockRecordContract.ClockRecordModel {
    @Override
    public Disposable getClockRecord(int fid, int uid, String month, ClockRecordContract.ClockRecordCallBack clockRecordCallBack) {
        return Network
                .getRequest()
                .getClockRecord(fid,uid,month)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClockRecordBean>() {
                    @Override
                    public void accept(ClockRecordBean clockRecordBean) throws Exception {
                        clockRecordCallBack.clockRecordSuccess(clockRecordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        clockRecordCallBack.clockRecordError((Exception) throwable);
                    }
                });
    }
}
