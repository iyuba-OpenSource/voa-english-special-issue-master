package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.GetWordsBean;
import com.iyuba.voaEnglish.view.home.GetWordsContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GetWordsModel implements GetWordsContract.GetWordsModel {
    @Override
    public Disposable getWordsMes(String q, String format, GetWordsContract.GetWordsCallBack getWordsCallBack) {
        return Network
                .getRequest()
                .getWordsMes(q,format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetWordsBean>() {
                    @Override
                    public void accept(GetWordsBean getWordsBean) throws Exception {
                        getWordsCallBack.getWordsSuccess(getWordsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getWordsCallBack.getWordsError((Exception) throwable);
                    }
                });
    }
}
