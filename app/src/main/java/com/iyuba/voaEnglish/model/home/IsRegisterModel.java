package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.IsRegisterBean;
import com.iyuba.voaEnglish.view.home.IsRegisterContact;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class IsRegisterModel implements IsRegisterContact.IsRegisterModel {
    @Override
    public Disposable isRegister(String format, String platform, int protocol, String username, IsRegisterContact.IsRegisterCallBack isRegisterCallBack) {
        return Network
                .getRequest()
                .isRegister(format,platform,protocol,username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IsRegisterBean>() {
                    @Override
                    public void accept(IsRegisterBean isRegisterBean) throws Exception {
                        isRegisterCallBack.IsRegisterSuccess(isRegisterBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isRegisterCallBack.IsRegisterError((Exception) throwable);
                    }
                });
    }
}
