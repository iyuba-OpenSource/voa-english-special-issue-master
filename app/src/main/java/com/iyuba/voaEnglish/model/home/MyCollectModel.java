package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.view.home.MyCollectContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyCollectModel implements MyCollectContract.MyCollectModel {
    @Override
    public Disposable getMyCollect(int userid, String topic, int appid, int sentenceFlg, String format, String sign, MyCollectContract.MyCollectCallBack myCollectCallBack) {
        return Network
                .getRequest()
                .getMyCollect("https://apps.iyuba.cn/dataapi/jsp/getCollect.jsp",userid,topic,appid,sentenceFlg,format,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyCollectBean>() {
                    @Override
                    public void accept(MyCollectBean myCollectBean) throws Exception {
                        myCollectCallBack.MyCollectSuccess(myCollectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        myCollectCallBack.MyCollectError((Exception) throwable);
                    }
                });
    }
}
