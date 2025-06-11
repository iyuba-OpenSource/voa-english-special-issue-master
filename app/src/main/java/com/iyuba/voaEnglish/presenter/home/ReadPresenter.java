package com.iyuba.voaEnglish.presenter.home;



import com.iyuba.voaEnglish.model.bean.ReadBean;
import com.iyuba.voaEnglish.model.home.ReadModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.ReadContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class ReadPresenter extends BasePresenter<ReadContract.ReadView,ReadContract.ReadModel> implements ReadContract.ReadPresenter{
    @Override
    protected ReadContract.ReadModel initModel() {
        return new ReadModel();
    }

    @Override
    public void getRead(String format, String uid, String BeginTime, String EndTime, String appName, String Lesson, String LessonId, int appId, String Device, String DeviceId, int EndFlg, int wordcount, int categoryid, String platform,int  rewardVersion) {
        Disposable disposable=model.getRead(format, uid, BeginTime, EndTime, appName, Lesson, LessonId, appId, Device, DeviceId, EndFlg, wordcount, categoryid, platform,rewardVersion, new ReadContract.CallBackRead() {
            @Override
            public void successRead(ReadBean readBean) {
                view.getRead(readBean);
            }

            @Override
            public void errorRead(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
