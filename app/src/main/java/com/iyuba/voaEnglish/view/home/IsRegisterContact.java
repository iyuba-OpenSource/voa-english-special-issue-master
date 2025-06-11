package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.IsRegisterBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface IsRegisterContact {

    interface IsRegisterView extends LoadingView {
        void isRegister(IsRegisterBean isRegisterBean);
    }


    interface IsRegisterPresenter extends IBasePresenter<IsRegisterView> {
        void isRegister(String format, String platform, int protocol, String username);
    }

    interface IsRegisterModel extends BaseModel {
        Disposable isRegister(String format, String platform, int protocol, String username,IsRegisterCallBack isRegisterCallBack);
    }

    interface IsRegisterCallBack{
        void IsRegisterSuccess(IsRegisterBean isRegisterBean);

        void IsRegisterError(Exception e);
    }
}
