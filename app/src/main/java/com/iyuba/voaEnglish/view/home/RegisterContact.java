package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.RegisterBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface RegisterContact {

    interface RegisterView extends LoadingView {
        void userRegister(RegisterBean registerBean);
    }

    interface RegisterPresenter extends IBasePresenter<RegisterView>{
        void userRegister(int protocol, String username, String password, String mobile, String app, String Platform, String format, int appid, String sign);
    }

    interface RegisterModel extends BaseModel{
        Disposable userRegister(int protocol, String username, String password, String mobile, String app, String Platform, String format, int appid, String sign, RegisterCallBack registerCallBack);
    }

    interface RegisterCallBack{

        void RegisterSuccess(RegisterBean registerBean);

        void RegisterError(Exception e);
    }
}
