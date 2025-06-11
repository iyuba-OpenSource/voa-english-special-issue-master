package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UserPwdLoginBean;
import com.iyuba.voaEnglish.view.home.UserContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserModel implements UserContract.UserModel {
    @Override
    public Disposable getUserLogin(String username, String password, String app, String token, String format, int appid, String protocol, String sign,UserContract.UserLoginCallBack userLoginCallBack) {
        return Network
                .getRequest()
                .getUserLogin(username,password,app,token,format,appid,protocol,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserPwdLoginBean>() {
                    @Override
                    public void accept(UserPwdLoginBean userPwdLoginBean) throws Exception {

                        userLoginCallBack.successLogin(userPwdLoginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        userLoginCallBack.errorLogin((Exception) throwable);
                    }
                });
    }
}
