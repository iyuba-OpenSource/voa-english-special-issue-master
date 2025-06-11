package com.iyuba.voaEnglish.presenter.ad;


import com.iyuba.voaEnglish.model.ad.AdIncentiveModel;
import com.iyuba.voaEnglish.model.bean.IncentiveVipBean;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.ad.AdIncentiveContract;

import io.reactivex.disposables.Disposable;

public class AdIncentivePresenter extends BasePresenter<AdIncentiveContract.AdIncentiveView, AdIncentiveContract.AdIncentiveModel>
        implements AdIncentiveContract.AdIncentivePresenter {
    @Override
    protected AdIncentiveContract.AdIncentiveModel initModel() {
        return new AdIncentiveModel();
    }

    @Override
    public void openIncentiveVip(String url, String uid, String appid, String timeStr, String sign) {

        Disposable disposable = model.openIncentiveVip(url, uid, appid, timeStr, sign, new AdIncentiveContract.Callback() {
            @Override
            public void success(IncentiveVipBean incentiveVipBean) {

                if (incentiveVipBean.getResult() == 200) {

                    view.openIncentiveVip(incentiveVipBean);
                } else {

                    view.toast(incentiveVipBean.getMessage());
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
        addSubscribe(disposable);
    }
}
