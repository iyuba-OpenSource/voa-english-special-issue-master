package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.LogLoginBean;
import com.iyuba.voaEnglish.view.home.LogUserContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LogUserModel implements LogUserContract.LogUserModel {
    @Override
    public Disposable logUser(int protocol, String username, String password, String format, String sign, LogUserContract.LogCallBack logCallBack) {
        return Network
                .getRequest()
                .logUser("http://api.iyuba.com.cn/v2/api.iyuba",protocol,username,password,format,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LogLoginBean>() {
                    @Override
                    public void accept(LogLoginBean logLoginBean) throws Exception {
                        logCallBack.successLog(logLoginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        logCallBack.errorLog((Exception) throwable);
                    }
                });
    }
}
