package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.ClockMoneyBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface ClockMoneyContract {

    interface ClockMoneyView extends LoadingView {

        void clockAddMoney(ClockMoneyBean clockMoneyBean);
    }

    interface ClockMoneyPresenter extends IBasePresenter<ClockMoneyView> {

        void clockAddMoney(int srid, int mobile, String flag, int uid, int appid);
    }

    interface ClockMoneyModel extends BaseModel{

        Disposable clockAddMoney(int srid, int mobile, String flag, int uid, int appid,ClockAddMoneyCallBack clockAddMoneyCallBack);
    }

    interface ClockAddMoneyCallBack{

        void clockAddMoneySuccess(ClockMoneyBean clockMoneyBean);

        void clockAddMoneyError(Exception e);
    }
}
