package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.UserPwdLoginBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface UserContract {

    interface UserView extends LoadingView {

        void getUserLogin(UserPwdLoginBean userPwdLoginBean);
    }

    interface UserPresenter extends IBasePresenter<UserView> {

        void getUserLogin(String username, String password, String app, String token, String format, int appid, String protocol, String sign);

    }

    interface UserModel extends BaseModel {

        Disposable getUserLogin(String username, String password, String app, String token, String format, int appid, String protocol, String sign, UserLoginCallBack userLoginCallBack);

    }

    interface UserLoginCallBack{
        void successLogin(UserPwdLoginBean userPwdLoginBean);

        void errorLogin(Exception e);

    }
}
