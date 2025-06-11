package com.iyuba.voaEnglish.view.home;


import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.AdEntryBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface AdEntryContract {

    interface AdEntryView extends LoadingView {

        void getAdEntryAll(List<AdEntryBean> adEntryBean);
    }

    interface AdEntryPresenter extends IBasePresenter<AdEntryView> {

        void getAdEntryAll(int appId, int flag, int uid);
    }

    interface AdEntryModel extends BaseModel {

        Disposable getAdEntryAll(int appId, int flag, int uid,AdCallBack adCallBack);
    }

    interface AdCallBack{

        void adSuccess(List<AdEntryBean> adEntryBean);

        void adError(Exception e);
    }
}
