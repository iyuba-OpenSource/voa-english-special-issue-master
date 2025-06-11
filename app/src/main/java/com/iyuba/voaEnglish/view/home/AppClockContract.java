package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.AppClockBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface AppClockContract {

    interface AppClockView extends LoadingView{

        void getAppClock(AppClockBean appClockBean);
    }

    interface AppClockPresenter extends IBasePresenter<AppClockView>{

        void getAppClock(int uid,int appId,String time);
    }

    interface AppClockModel extends BaseModel{

        Disposable getAppClock(int uid, int appId, String time, AppClockCallBack appClockCallBack);
    }


    interface AppClockCallBack{

        void appClockSuccess(AppClockBean appClockBean);

        void appClockError(Exception e);
    }
}
