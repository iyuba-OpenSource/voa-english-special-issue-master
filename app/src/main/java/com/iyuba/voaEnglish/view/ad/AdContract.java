package com.iyuba.voaEnglish.view.ad;



import com.iyuba.voaEnglish.db.AdLog;
import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.ad.AdSubmitBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface AdContract {

    interface AdView extends LoadingView {

    }

    interface AdPresenter extends IBasePresenter<AdView> {

        void addAdInfo(String url, String date_time, String appid, String device,
                       String deviceid, String uid, String packageStr, String os,
                       String ads, List<AdLog> adLogList);
    }

    interface AdModel extends BaseModel {

        Disposable addAdInfo(String url, String date_time, String appid, String device,
                             String deviceid, String uid, String packageStr, String os,
                             String ads, Callback callback);
    }

    interface Callback {

        void success(AdSubmitBean adSubmitBean);

        void error(Exception e);
    }
}
