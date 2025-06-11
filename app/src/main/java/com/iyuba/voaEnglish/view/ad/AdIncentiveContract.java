package com.iyuba.voaEnglish.view.ad;


import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.IncentiveVipBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface AdIncentiveContract {

    interface AdIncentiveView extends LoadingView {

        void openIncentiveVip(IncentiveVipBean incentiveVipBean);
    }

    interface AdIncentivePresenter extends IBasePresenter<AdIncentiveView> {

        void openIncentiveVip(String url, String uid, String appid
                , String timeStr, String sign);
    }

    interface AdIncentiveModel extends BaseModel {

        Disposable openIncentiveVip(String url, String uid, String appid
                , String timeStr, String sign, Callback callback);
    }

    interface Callback {

        void success(IncentiveVipBean incentiveVipBean);

        void error(Exception e);
    }
}
