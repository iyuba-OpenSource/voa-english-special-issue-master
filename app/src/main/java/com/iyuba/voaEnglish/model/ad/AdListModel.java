
package com.iyuba.voaEnglish.model.ad;


import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.ad.AdSubmitBean;
import com.iyuba.voaEnglish.view.ad.AdContract;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AdListModel implements AdContract.AdModel {

    @Override
    public Disposable addAdInfo(String url, String date_time, String appid, String device, String deviceid,
                                String uid, String packageStr, String os, String ads, AdContract.Callback callback) {

        return Network
                .getRequest()
                .addAdInfo(url, date_time, appid, device, deviceid, uid, packageStr, os, ads)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<AdSubmitBean>() {
                    @Override
                    public void accept(AdSubmitBean adSubmitBean) throws Exception {

                        callback.success(adSubmitBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        callback.error((Exception) throwable);
                    }
                });
    }
}
