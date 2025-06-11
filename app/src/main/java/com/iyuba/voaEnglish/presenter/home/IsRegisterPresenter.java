package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.IsRegisterBean;
import com.iyuba.voaEnglish.model.home.IsRegisterModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.IsRegisterContact;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class IsRegisterPresenter extends BasePresenter<IsRegisterContact.IsRegisterView, IsRegisterContact.IsRegisterModel> implements IsRegisterContact.IsRegisterPresenter {


    @Override
    public void isRegister(String format, String platform, int protocol, String username) {

        Disposable disposable = model.isRegister(format, platform, protocol, username, new IsRegisterContact.IsRegisterCallBack() {
            @Override
            public void IsRegisterSuccess(IsRegisterBean isRegisterBean) {
                view.isRegister(isRegisterBean);
            }

            @Override
            public void IsRegisterError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);

    }

    @Override
    protected IsRegisterContact.IsRegisterModel initModel() {
        return new IsRegisterModel();
    }
}
