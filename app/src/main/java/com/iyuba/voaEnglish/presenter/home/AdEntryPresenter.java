package com.iyuba.voaEnglish.presenter.home;


import com.iyuba.voaEnglish.model.bean.AdEntryBean;
import com.iyuba.voaEnglish.model.home.AdEntryModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.AdEntryContract;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AdEntryPresenter extends BasePresenter<AdEntryContract.AdEntryView, AdEntryContract.AdEntryModel> implements AdEntryContract.AdEntryPresenter {


    @Override
    public void getAdEntryAll(int appId, int flag, int uid) {
        Disposable disposable = model.getAdEntryAll(appId, flag, uid, new AdEntryContract.AdCallBack() {
            @Override
            public void adSuccess(List<AdEntryBean> adEntryBean) {
                view.getAdEntryAll(adEntryBean);
            }

            @Override
            public void adError(Exception e) {
                if(e instanceof UnknownHostException){
                    view.toast("请求时间超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected AdEntryContract.AdEntryModel initModel() {
        return new AdEntryModel();
    }
}
