package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.MyWordBean;
import com.iyuba.voaEnglish.view.home.MyWordContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyWordModel implements MyWordContract.MyWordModel {
    @Override
    public Disposable getMyWord(int u, int pageNumber, int pageCounts, String format, MyWordContract.MyWordCallBack myWordCallBack) {
        return Network
                .getRequest()
                .getMyWord(u,pageNumber,pageCounts,format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyWordBean>() {
                    @Override
                    public void accept(MyWordBean myWordBean) throws Exception {
                        myWordCallBack.myWordSuccess(myWordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        myWordCallBack.myWordError((Exception) throwable);
                    }
                });
    }
}
