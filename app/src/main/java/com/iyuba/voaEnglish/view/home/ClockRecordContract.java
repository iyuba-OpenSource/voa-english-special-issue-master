package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.ClockRecordBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface ClockRecordContract {

    interface ClockRecordView extends LoadingView {

        void getClockRecord(ClockRecordBean clockRecordBean);
    }

    interface ClockRecordPresenter extends IBasePresenter<ClockRecordView> {

        void getClockRecord(int fid, int uid, String month);
    }

    interface ClockRecordModel extends BaseModel {

        Disposable getClockRecord(int fid, int uid, String month, ClockRecordCallBack clockRecordCallBack);
    }

    interface ClockRecordCallBack{

        void clockRecordSuccess(ClockRecordBean clockRecordBean);

        void clockRecordError(Exception e);
    }
}
