package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.LogLoginBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface LogUserContract {

    interface LogUserView extends LoadingView {
        void logUser(LogLoginBean logLoginBean);
    }

    interface LogUserPresenter extends IBasePresenter<LogUserView> {
        void logUser(int protocol, String username, String password, String format, String sign);
    }

    interface LogUserModel extends BaseModel {
        Disposable logUser(int protocol, String username, String password, String format, String sign, LogCallBack logCallBack);
    }


    interface LogCallBack {

        void successLog(LogLoginBean logLoginBean);


        void errorLog(Exception e);

    }
}
