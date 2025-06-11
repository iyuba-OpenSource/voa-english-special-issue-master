package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.view.home.UidLoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UidLoginModel implements UidLoginContract.UidLoginModel {
    @Override
    public Disposable uidLogin(String platform, String format, int procotol, int id, int myid, int appid, String sign, UidLoginContract.UidLoginCallBack uidLoginCallBack) {
        return Network
                .getRequest()
                .uidLogin(platform, format, procotol , id, myid, appid ,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UidBean>() {
                    @Override
                    public void accept(UidBean uidBean) throws Exception {
                        uidLoginCallBack.uidSuccessLogin(uidBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        uidLoginCallBack.uidErrorLogin((Exception) throwable);
                    }
                });
    }
}
