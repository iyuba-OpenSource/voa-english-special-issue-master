package com.iyuba.voaEnglish.model.home;



import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.AdEntryBean;
import com.iyuba.voaEnglish.view.home.AdEntryContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AdEntryModel implements AdEntryContract.AdEntryModel {
    @Override
    public Disposable getAdEntryAll(int appId, int flag, int uid, AdEntryContract.AdCallBack adCallBack) {
        return Network
                .getRequestForDev()
                .getAdEntryAll("http://dev.iyuba.cn/getAdEntryAll.jsp",appId,flag,uid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AdEntryBean>>() {
                    @Override
                    public void accept(List<AdEntryBean> adEntryBeans) throws Exception {
                        adCallBack.adSuccess(adEntryBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        adCallBack.adError((Exception) throwable);
                    }
                });
    }
}
