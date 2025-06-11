package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.RegisterBean;
import com.iyuba.voaEnglish.model.home.RegisterModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.RegisterContact;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class RegisterPresenter extends BasePresenter<RegisterContact.RegisterView, RegisterContact.RegisterModel> implements RegisterContact.RegisterPresenter {


    @Override
    public void userRegister(int protocol, String username, String password, String mobile, String app, String Platform, String format, int appid, String sign) {

        Disposable disposable = model.userRegister(protocol, username, password, mobile, app, Platform, format, appid, sign, new RegisterContact.RegisterCallBack() {
            @Override
            public void RegisterSuccess(RegisterBean registerBean) {
                view.userRegister(registerBean);
            }

            @Override
            public void RegisterError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);

    }

    @Override
    protected RegisterContact.RegisterModel initModel() {
        return new RegisterModel();
    }
}
