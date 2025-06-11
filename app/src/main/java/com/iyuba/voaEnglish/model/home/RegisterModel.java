package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.RegisterBean;
import com.iyuba.voaEnglish.view.home.RegisterContact;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterModel implements RegisterContact.RegisterModel {
    @Override
    public Disposable userRegister(int protocol, String username, String password, String mobile, String app, String Platform, String format, int appid, String sign, RegisterContact.RegisterCallBack registerCallBack) {
        return Network
                .getRequest()
                .userRegister(protocol,username,password,mobile,app,Platform,format,appid,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterBean>() {
                    @Override
                    public void accept(RegisterBean registerBean) throws Exception {
                        registerCallBack.RegisterSuccess(registerBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        registerCallBack.RegisterError((Exception) throwable);
                    }
                });

    }
}
