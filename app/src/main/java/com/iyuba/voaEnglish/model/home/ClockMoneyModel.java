package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.ClockMoneyBean;
import com.iyuba.voaEnglish.view.home.ClockMoneyContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ClockMoneyModel implements ClockMoneyContract.ClockMoneyModel {
    @Override
    public Disposable clockAddMoney(int srid, int mobile, String flag, int uid, int appid, ClockMoneyContract.ClockAddMoneyCallBack clockAddMoneyCallBack) {
        return Network
                .getRequest()
                .clockAddMoney("http://api.iyuba.cn/credits/updateScore.jsp",srid,mobile,flag,uid,appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ClockMoneyBean>() {
                    @Override
                    public void accept(ClockMoneyBean clockMoneyBean) throws Exception {
                        clockAddMoneyCallBack.clockAddMoneySuccess(clockMoneyBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        clockAddMoneyCallBack.clockAddMoneyError((Exception) throwable);
                    }
                });
    }
}
