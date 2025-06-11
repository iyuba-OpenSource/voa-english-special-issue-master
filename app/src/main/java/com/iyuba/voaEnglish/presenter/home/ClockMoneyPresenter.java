package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.ClockMoneyBean;
import com.iyuba.voaEnglish.model.home.ClockMoneyModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.ClockMoneyContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class ClockMoneyPresenter extends BasePresenter<ClockMoneyContract.ClockMoneyView, ClockMoneyContract.ClockMoneyModel> implements ClockMoneyContract.ClockMoneyPresenter {


    @Override
    public void clockAddMoney(int srid, int mobile, String flag, int uid, int appid) {
        Disposable disposable = model.clockAddMoney(srid, mobile, flag, uid, appid, new ClockMoneyContract.ClockAddMoneyCallBack() {
            @Override
            public void clockAddMoneySuccess(ClockMoneyBean clockMoneyBean) {
                view.clockAddMoney(clockMoneyBean);
            }

            @Override
            public void clockAddMoneyError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected ClockMoneyContract.ClockMoneyModel initModel() {
        return new ClockMoneyModel();
    }
}
