package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.UserPwdLoginBean;
import com.iyuba.voaEnglish.model.home.UserModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.UserContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class UserPresenter extends BasePresenter<UserContract.UserView, UserContract.UserModel> implements UserContract.UserPresenter {


    @Override
    public void getUserLogin(String username, String password, String app, String token, String format, int appid, String protocol, String sign) {

        Disposable disposable = model.getUserLogin(username,password,app,token,format,appid,protocol,sign, new UserContract.UserLoginCallBack() {
            @Override
            public void successLogin(UserPwdLoginBean userPwdLoginBean) {
                view.getUserLogin(userPwdLoginBean);
            }

            @Override
            public void errorLogin(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }
    @Override
    protected UserContract.UserModel initModel() {
        return new UserModel();
    }
}
