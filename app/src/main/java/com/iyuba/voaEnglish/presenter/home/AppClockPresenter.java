package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.AppClockBean;
import com.iyuba.voaEnglish.model.home.AppClockModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.AppClockContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class AppClockPresenter extends BasePresenter<AppClockContract.AppClockView, AppClockContract.AppClockModel> implements AppClockContract.AppClockPresenter {


    @Override
    public void getAppClock(int uid, int appId, String time) {
        Disposable disposable = model.getAppClock(uid, appId, time, new AppClockContract.AppClockCallBack() {
            @Override
            public void appClockSuccess(AppClockBean appClockBean) {
                view.getAppClock(appClockBean);
            }

            @Override
            public void appClockError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected AppClockContract.AppClockModel initModel() {
        return new AppClockModel();
    }
}
