package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.CollectBean;
import com.iyuba.voaEnglish.view.home.CollectContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CollectModel implements CollectContract.CollectModel {
    @Override
    public Disposable getCollect(String groupName, int sentenceFlg, int appId, int userId, String type, int voaId, int sentenceId, String topic, String format, CollectContract.CollectCallBack collectCallBack) {
        return Network
                .getRequest()
                .getCollect("https://apps.iyuba.cn/iyuba/updateCollect.jsp",groupName,sentenceFlg,appId,userId,type,voaId,sentenceId,topic,format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectBean>() {
                    @Override
                    public void accept(CollectBean collectBean) throws Exception {
                        collectCallBack.successCollect(collectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        collectCallBack.errorCollect((Exception) throwable);
                    }
                });
    }
}
