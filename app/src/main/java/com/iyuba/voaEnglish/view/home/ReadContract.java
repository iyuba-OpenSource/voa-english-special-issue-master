package com.iyuba.voaEnglish.view.home;


import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.ReadBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface ReadContract {

    interface ReadView extends LoadingView {
        void getRead(ReadBean readBean);
    }

    interface ReadPresenter extends IBasePresenter<ReadView> {
        void getRead(String format, String uid,  String BeginTime,  String EndTime,  String appName,  String Lesson, String LessonId,int appId,  String Device
                ,  String DeviceId,  int EndFlg,  int wordcount,  int categoryid,  String platform,int rewardVersion);
    }

    interface ReadModel extends BaseModel {

        Disposable getRead(String format, String uid,  String BeginTime,  String EndTime,  String appName,  String Lesson, String LessonId,int appId,  String Device
                ,  String DeviceId,  int EndFlg,  int wordcount,  int categoryid,  String platform,int rewardVersion, CallBackRead callBackRead);
    }


    interface CallBackRead{

        void successRead(ReadBean readBean);

        void errorRead(Exception e);

    }
}
