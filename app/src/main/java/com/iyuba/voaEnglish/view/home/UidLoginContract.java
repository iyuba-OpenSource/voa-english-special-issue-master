package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface UidLoginContract {

    interface UidLoginView extends LoadingView {

        void uidLogin(UidBean uidBean);

    }

    interface UidLoginPresenter extends IBasePresenter<UidLoginView> {

        void uidLogin(String platform, String format, int procotol, int id, int myid, int appid, String sign);
    }

    interface UidLoginModel extends BaseModel {
        Disposable uidLogin(String platform, String format, int procotol, int id, int myid, int appid, String sign, UidLoginCallBack uidLoginCallBack);
    }


    interface UidLoginCallBack {

        void uidSuccessLogin(UidBean uidBean);

        void uidErrorLogin(Exception e);
    }
}
