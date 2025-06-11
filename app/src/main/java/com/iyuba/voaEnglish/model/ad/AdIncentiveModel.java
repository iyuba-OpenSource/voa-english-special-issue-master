package com.iyuba.voaEnglish.model.ad;


import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.IncentiveVipBean;
import com.iyuba.voaEnglish.view.ad.AdIncentiveContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AdIncentiveModel implements AdIncentiveContract.AdIncentiveModel {


    @Override
    public Disposable openIncentiveVip(String url, String uid, String appid, String timeStr, String sign, AdIncentiveContract.Callback callback) {

        return Network
                .getRequest()
                .openIncentiveVip(url, uid, appid, timeStr, sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IncentiveVipBean>() {
                    @Override
                    public void accept(IncentiveVipBean incentiveVipBean) throws Exception {

                        callback.success(incentiveVipBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        callback.error((Exception) throwable);
                    }
                });
    }
}
