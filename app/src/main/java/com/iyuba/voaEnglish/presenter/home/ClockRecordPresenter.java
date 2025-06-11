package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.ClockRecordBean;
import com.iyuba.voaEnglish.model.home.ClockRecordModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.ClockRecordContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class ClockRecordPresenter extends BasePresenter<ClockRecordContract.ClockRecordView, ClockRecordContract.ClockRecordModel> implements ClockRecordContract.ClockRecordPresenter {


    @Override
    public void getClockRecord(int fid, int uid, String month) {
        Disposable disposable = model.getClockRecord(fid, uid, month, new ClockRecordContract.ClockRecordCallBack() {
            @Override
            public void clockRecordSuccess(ClockRecordBean clockRecordBean) {
                view.getClockRecord(clockRecordBean);
            }

            @Override
            public void clockRecordError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected ClockRecordContract.ClockRecordModel initModel() {
        return new ClockRecordModel();
    }
}
