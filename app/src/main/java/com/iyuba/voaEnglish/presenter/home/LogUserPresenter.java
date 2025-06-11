package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.LogLoginBean;
import com.iyuba.voaEnglish.model.home.LogUserModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.home.LogUserContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class LogUserPresenter extends BasePresenter<LogUserContract.LogUserView, LogUserContract.LogUserModel> implements LogUserContract.LogUserPresenter {


    @Override
    public void logUser(int protocol, String username, String password, String format, String sign) {
        Disposable disposable = model.logUser(protocol, username, password, format, sign, new LogUserContract.LogCallBack() {
            @Override
            public void successLog(LogLoginBean logLoginBean) {
                view.logUser(logLoginBean);
            }

            @Override
            public void errorLog(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });

        addSubscribe(disposable);

    }


    @Override
    protected LogUserContract.LogUserModel initModel() {
        return new LogUserModel();
    }
}
