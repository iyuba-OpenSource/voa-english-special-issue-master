package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.model.home.UidLoginModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.UidLoginContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class UidLoginPresenter extends BasePresenter<UidLoginContract.UidLoginView, UidLoginContract.UidLoginModel> implements UidLoginContract.UidLoginPresenter {


    @Override
    public void uidLogin(String platform, String format, int procotol, int id, int myid, int appid, String sign) {

        Disposable disposable = model.uidLogin(platform, format, procotol, id, myid, appid, sign, new UidLoginContract.UidLoginCallBack() {
            @Override
            public void uidSuccessLogin(UidBean uidBean) {

                view.uidLogin(uidBean);

            }

            @Override
            public void uidErrorLogin(Exception e) {

                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);

    }

    @Override
    protected UidLoginContract.UidLoginModel initModel() {
        return new UidLoginModel();
    }
}
